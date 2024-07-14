package com.senolkarakurt.customerservice.client.service;

import com.senolkarakurt.customerservice.client.AuthenticationClient;
import com.senolkarakurt.customerservice.dto.response.RegistrationResponseDto;
import com.senolkarakurt.customerservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.customerservice.model.User;
import com.senolkarakurt.customerservice.service.SystemLogService;
import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.dto.request.UserRequestDto;
import com.senolkarakurt.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AuthenticationClientService {

    private final ExceptionMessagesResource exceptionMessagesResource;
    private final AuthenticationClient authenticationClient;
    private final SystemLogService systemLogService;

    public AuthenticationClientService(ExceptionMessagesResource exceptionMessagesResource,
                                       AuthenticationClient authenticationClient,
                                       @Qualifier("textFileLogService") SystemLogService systemLogService) {
        this.exceptionMessagesResource = exceptionMessagesResource;
        this.authenticationClient = authenticationClient;
        this.systemLogService = systemLogService;
    }

    public RegistrationResponseDto register(UserRequestDto userRequestDto) {
        RegistrationResponseDto registrationResponseDto = authenticationClient.register(userRequestDto);
        if (registrationResponseDto.getUser().getId() == null) {
            log.error("%s : {}".formatted(exceptionMessagesResource.getCustomerNotCreated()));
            saveSystemLog("%s : {}".formatted(exceptionMessagesResource.getCustomerNotCreated()));
            throw new CommonException(exceptionMessagesResource.getCustomerNotCreated());
        }
        return registrationResponseDto;
    }

    private void saveSystemLog(String content){
        SystemLogSaveRequestDto systemLogSaveRequestDto = SystemLogSaveRequestDto.builder()
                .recordDateTime(LocalDateTime.now())
                .content(content)
                .build();
        systemLogService.save(systemLogSaveRequestDto);
    }
}
