package com.senolkarakurt.orderservice.client.service;

import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.orderservice.client.UserClient;
import com.senolkarakurt.orderservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.orderservice.model.Address;
import com.senolkarakurt.orderservice.model.User;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.orderservice.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@Slf4j
public class UserClientService {

    private final ExceptionMessagesResource exceptionMessagesResource;
    private final UserClient userClient;
    private final SystemLogService systemLogService;

    public UserClientService(ExceptionMessagesResource exceptionMessagesResource,
                             UserClient userClient,
                             @Qualifier("textFileLogService") SystemLogService systemLogService) {
        this.exceptionMessagesResource = exceptionMessagesResource;
        this.userClient = userClient;
        this.systemLogService = systemLogService;
    }

    public User getUserById(Long id) {
        User user = userClient.getUserById(id);
        if (user.getId() == null) {
            log.error("%s : {}".formatted(exceptionMessagesResource.getCustomerNotFound()));
            SystemLogSaveRequestDto systemLogSaveRequestDto = SystemLogSaveRequestDto.builder()
                    .userId(null)
                    .recordDateTime(LocalDateTime.now())
                    .content("%s : {}".formatted(exceptionMessagesResource.getCustomerNotFound()))
                    .build();
            systemLogService.save(systemLogSaveRequestDto);
            throw new CommonException(exceptionMessagesResource.getCustomerNotFound());
        }
        return user;
    }

    public Set<Address> getAddressesByUserId(Long userId){
        return userClient.getAddressesByUserId(userId);
    }

}
