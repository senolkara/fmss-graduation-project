package com.senolkarakurt.userservice.service.impl;

import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.userservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.userservice.model.Address;
import com.senolkarakurt.userservice.model.User;
import com.senolkarakurt.userservice.repository.AddressRepository;
import com.senolkarakurt.userservice.repository.UserRepository;
import com.senolkarakurt.userservice.service.SystemLogService;
import com.senolkarakurt.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ExceptionMessagesResource exceptionMessagesResource;
    private final SystemLogService systemLogService;

    public UserServiceImpl(UserRepository userRepository,
                           AddressRepository addressRepository,
                           ExceptionMessagesResource exceptionMessagesResource,
                           @Qualifier("textFileLogService") SystemLogService systemLogService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
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
        return new HashSet<>(addressRepository.findAddressesByUserIdAndRecordStatus(userId, RecordStatus.ACTIVE));
    }

    private void saveSystemLog(String content){
        SystemLogSaveRequestDto systemLogSaveRequestDto = SystemLogSaveRequestDto.builder()
                .recordDateTime(LocalDateTime.now())
                .content(content)
                .build();
        systemLogService.save(systemLogSaveRequestDto);
    }

}
