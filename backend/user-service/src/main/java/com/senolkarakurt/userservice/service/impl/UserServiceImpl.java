package com.senolkarakurt.userservice.service.impl;

import com.senolkarakurt.dto.request.UserRequestDto;
import com.senolkarakurt.dto.response.UserResponseDto;
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
import java.util.List;
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
        //kaydetme işleminden sonra login işlemi sonra yapılacak
        return user;
    }

    @Override
    public List<UserResponseDto> getAll() {
        return UserConverter.toResponse(userRepository.findAll());
    }

    @Override
    public UserResponseDto getByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithThisEmail(), email));
            throw new CommonException(exceptionMessagesResource.getUserNotFoundWithThisEmail());
        }
        User user = userOptional.get();
        if (!user.isActive()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getThisUserIsInActive(), user.getEmail()));
            throw new CommonException(exceptionMessagesResource.getThisUserIsInActive());
        }
        return UserConverter.toUserResponseDtoByUser(userOptional.get());
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }

    @Override
    public UserResponseDto getByPhoneNumber(String phoneNumber) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isPresent()){
            return UserConverter.toUserResponseDtoByUser(userOptional.get());
        }
        log.error("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithThisPhoneNumber(), phoneNumber));
        throw new CommonException(exceptionMessagesResource.getUserNotFoundWithThisPhoneNumber());
    }

    @Override
    public UserResponseDto getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            return UserConverter.toUserResponseDtoByUser(userOptional.get());
        }
        log.error("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithId(), id));
        throw new CommonException(exceptionMessagesResource.getUserNotFoundWithId());
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public List<User> getUserListByIds(List<Long> userIdList) {
        return userRepository.findAllById(userIdList);
    }

    @Override
    public Set<Address> getAddressesByUserId(Long userId) {
        return new HashSet<>(addressRepository.getAddressesByUserId(userId));
    }

    @Override
    public void login(LoginRequestDto loginRequestDto) {
        User user = getUserByEmail(loginRequestDto.getEmail());
        if (user == null){
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

    private User getUser(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithThisEmail(), email));
            throw new CommonException(exceptionMessagesResource.getUserNotFoundWithThisEmail());
        }
        User user = userOptional.get();
        if (!user.isActive()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getThisUserIsInActive(), user.getEmail()));
            throw new CommonException(exceptionMessagesResource.getThisUserIsInActive());
        }
        return userOptional.get();
    }

}
