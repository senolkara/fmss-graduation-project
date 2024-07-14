package com.senolkarakurt.customerservice.client.service;

import com.senolkarakurt.customerservice.client.UserClient;
import com.senolkarakurt.customerservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.customerservice.model.Address;
import com.senolkarakurt.customerservice.model.User;
import com.senolkarakurt.customerservice.service.SystemLogService;
import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@Slf4j
public class UserClientService {

    private final UserClient userClient;
    private final ExceptionMessagesResource exceptionMessagesResource;
    private final SystemLogService systemLogService;

    public UserClientService(UserClient userClient,
                             ExceptionMessagesResource exceptionMessagesResource,
                             @Qualifier("textFileLogService") SystemLogService systemLogService) {
        this.userClient = userClient;
        this.exceptionMessagesResource = exceptionMessagesResource;
        this.systemLogService = systemLogService;
    }

    public User getUserById(Long id) {
        User user = userClient.getUserById(id);
        if (user.getId() == null) {
            log.error("%s : {}".formatted(exceptionMessagesResource.getCustomerNotFound()));
            saveSystemLog("%s : {}".formatted(exceptionMessagesResource.getCustomerNotFound()));
            throw new CommonException(exceptionMessagesResource.getCustomerNotFound());
        }
        return user;
    }

    public Set<Address> getAddressesByUserId(Long userId){
        return userClient.getAddressesByUserId(userId);
    }

    private void saveSystemLog(String content){
        SystemLogSaveRequestDto systemLogSaveRequestDto = SystemLogSaveRequestDto.builder()
                .recordDateTime(LocalDateTime.now())
                .content(content)
                .build();
        systemLogService.save(systemLogSaveRequestDto);
    }
}
