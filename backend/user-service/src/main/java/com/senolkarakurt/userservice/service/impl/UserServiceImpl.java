package com.senolkarakurt.userservice.service.impl;

import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.userservice.dto.request.AddressUpdateRequestDto;
import com.senolkarakurt.userservice.dto.request.UserUpdateRequestDto;
import com.senolkarakurt.userservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.userservice.model.Address;
import com.senolkarakurt.userservice.model.User;
import com.senolkarakurt.userservice.repository.AddressRepository;
import com.senolkarakurt.userservice.repository.UserRepository;
import com.senolkarakurt.userservice.service.SystemLogService;
import com.senolkarakurt.userservice.service.UserService;
import com.senolkarakurt.userservice.token.Token;
import com.senolkarakurt.userservice.token.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final TokenRepository tokenRepository;
    private final ExceptionMessagesResource exceptionMessagesResource;
    private final SystemLogService systemLogService;

    public UserServiceImpl(UserRepository userRepository,
                           AddressRepository addressRepository,
                           TokenRepository tokenRepository,
                           ExceptionMessagesResource exceptionMessagesResource,
                           @Qualifier("dbLogService") SystemLogService systemLogService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.tokenRepository = tokenRepository;
        this.exceptionMessagesResource = exceptionMessagesResource;
        this.systemLogService = systemLogService;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithId(), id));
            saveSystemLog("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getUserNotFoundWithId());
        }
        if (!userOptional.get().getRecordStatus().equals(RecordStatus.ACTIVE)){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getThisUserIsInActive(), userOptional.get().getEmail()));
            saveSystemLog("%s : {} %s".formatted(exceptionMessagesResource.getThisUserIsInActive(), userOptional.get().getEmail()));
            throw new CommonException(exceptionMessagesResource.getThisUserIsInActive());
        }
        return userOptional.orElse(null);
    }

    @Override
    public Set<Address> getAddressesByUserId(Long userId) {
        return new HashSet<>(addressRepository.findByUserIdAndRecordStatus(userId, RecordStatus.ACTIVE));
    }

    @Override
    public List<Address> getAddressListByUserId(Long userId) {
        return addressRepository.findByUserIdAndRecordStatus(userId, RecordStatus.ACTIVE);
    }

    @Override
    public String getValidUserToken() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithThisEmail(), email));
            saveSystemLog("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithThisEmail(), email));
            throw new CommonException(exceptionMessagesResource.getUserNotFoundWithThisEmail());
        }
        User user = userOptional.get();
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return null;
        }
        return validUserTokens.getFirst().getToken();
    }

    @Override
    public void update(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        User user = getUserById(id);
        if (user == null){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithId(), id));
            saveSystemLog("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getUserNotFoundWithId());
        }
        user.setName(userUpdateRequestDto.getName());
        user.setSurname(userUpdateRequestDto.getSurname());
        user.setEmail(userUpdateRequestDto.getEmail());
        user.setPhoneNumber(userUpdateRequestDto.getPhoneNumber());
        user.setBirthDate(userUpdateRequestDto.getBirthDate());
        userRepository.save(user);
        updateAddress(id, userUpdateRequestDto.getAddressUpdateRequestDtoList());
    }

    @Override
    public void updateAddress(Long userId, List<AddressUpdateRequestDto> addressUpdateRequestDtoList) {
        User user = getUserById(userId);
        if (user == null){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithId(), userId));
            saveSystemLog("%s : {} %s".formatted(exceptionMessagesResource.getUserNotFoundWithId(), userId));
            throw new CommonException(exceptionMessagesResource.getUserNotFoundWithId());
        }
        if (CollectionUtils.isEmpty(addressRepository.findByUserId(userId))){
            addressUpdateRequestDtoList.forEach(addressUpdateRequestDto -> {
                Address address = new Address();
                address.setRecordStatus(RecordStatus.ACTIVE);
                address.setTitle(addressUpdateRequestDto.getTitle());
                address.setProvince(addressUpdateRequestDto.getProvince());
                address.setDistrict(addressUpdateRequestDto.getDistrict());
                address.setNeighbourhood(addressUpdateRequestDto.getNeighbourhood());
                address.setStreet(addressUpdateRequestDto.getStreet());
                address.setDescription(addressUpdateRequestDto.getDescription());
                address.setUserId(user.getId());
                addressRepository.save(address);
            });
        }
        else {
            addressUpdateRequestDtoList.forEach(addressUpdateRequestDto -> {
                Optional<Address> addressOptional = addressRepository.findById(addressUpdateRequestDto.getId());
                if (addressOptional.isPresent()){
                    Address address = addressOptional.get();
                    address.setRecordStatus(addressUpdateRequestDto.getRecordStatus() != null ? addressUpdateRequestDto.getRecordStatus() : RecordStatus.ACTIVE);
                    address.setTitle(addressUpdateRequestDto.getTitle());
                    address.setProvince(addressUpdateRequestDto.getProvince());
                    address.setDistrict(addressUpdateRequestDto.getDistrict());
                    address.setNeighbourhood(addressUpdateRequestDto.getNeighbourhood());
                    address.setStreet(addressUpdateRequestDto.getStreet());
                    address.setDescription(addressUpdateRequestDto.getDescription());
                    address.setUserId(user.getId());
                    addressRepository.save(address);
                }
            });
        }
    }

    private void saveSystemLog(String content){
        SystemLogSaveRequestDto systemLogSaveRequestDto = SystemLogSaveRequestDto.builder()
                .recordDateTime(LocalDateTime.now())
                .content(content)
                .build();
        systemLogService.save(systemLogSaveRequestDto);
    }

}
