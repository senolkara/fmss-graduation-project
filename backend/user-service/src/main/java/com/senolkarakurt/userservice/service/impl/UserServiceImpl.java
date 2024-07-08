package com.senolkarakurt.userservice.service.impl;

import com.senolkarakurt.dto.request.UserRequestDto;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.userservice.converter.AddressConverter;
import com.senolkarakurt.userservice.converter.UserConverter;
import com.senolkarakurt.userservice.dto.request.LoginRequestDto;
import com.senolkarakurt.userservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.userservice.model.Address;
import com.senolkarakurt.userservice.model.User;
import com.senolkarakurt.userservice.repository.AddressRepository;
import com.senolkarakurt.userservice.repository.UserRepository;
import com.senolkarakurt.userservice.service.UserService;
import com.senolkarakurt.util.GenerateRandomUnique;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ExceptionMessagesResource exceptionMessagesResource;

    @Override
    public User save(UserRequestDto userRequestDto) {
        Optional<User> userOptional = userRepository.findByEmail(userRequestDto.getEmail());
        if (userOptional.isPresent()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getEmailAlreadyExist(), userRequestDto.getEmail()));
            throw new CommonException(exceptionMessagesResource.getEmailAlreadyExist());
        }
        Optional<User> userPhoneNumberOptional = userRepository.findByPhoneNumber(userRequestDto.getPhoneNumber());
        if (userPhoneNumberOptional.isPresent()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPhoneNumberAlreadyExist(), userRequestDto.getEmail()));
            throw new CommonException(exceptionMessagesResource.getPhoneNumberAlreadyExist());
        }
        User user = UserConverter.toUserByUserRequestDto(userRequestDto);
        userRepository.save(user);
        Set<Address> addressSet = AddressConverter.toSetAddressBySetAddressRequestDto(userRequestDto.getAddressRequestDtoSet(), user);
        addressRepository.saveAll(addressSet);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getUserNotFoundWithId());
        }
        if (!userOptional.get().getRecordStatus().equals(RecordStatus.ACTIVE)){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getThisUserIsInActive(), userOptional.get().getEmail()));
            throw new CommonException(exceptionMessagesResource.getThisUserIsInActive());
        }
        return userOptional.orElse(null);
    }

    @Override
    public Set<Address> getAddressesByUserId(Long userId) {
        return new HashSet<>(addressRepository.findAddressesByUserIdAndRecordStatus(userId, RecordStatus.ACTIVE));
    }

    @Override
    public void login(LoginRequestDto loginRequestDto) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequestDto.getEmail());
        if (userOptional.isEmpty()){
            log.error("%s : {}".formatted(exceptionMessagesResource.getLoginFailed()));
            throw new CommonException(exceptionMessagesResource.getLoginFailed());
        }
        User user = userOptional.get();
        if (!user.getRecordStatus().equals(RecordStatus.ACTIVE)){
            log.error("%s : {}".formatted(exceptionMessagesResource.getLoginFailed()));
            throw new CommonException(exceptionMessagesResource.getLoginFailed());
        }
        String hashingPass = GenerateRandomUnique.createRandomHash(loginRequestDto.getPassword());
        String hashedPass = user.getPassword();
        if (!hashingPass.equals(hashedPass)){
            log.error("%s : {}".formatted(exceptionMessagesResource.getLoginFailed()));
            throw new CommonException(exceptionMessagesResource.getLoginFailed());
        }
    }

}
