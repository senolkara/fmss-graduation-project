package com.senolkarakurt.customerservice.client.service;

import com.senolkarakurt.customerservice.client.AuthenticationClient;
import com.senolkarakurt.customerservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.customerservice.model.User;
import com.senolkarakurt.dto.request.UserRequestDto;
import com.senolkarakurt.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationClientService {

    private final ExceptionMessagesResource exceptionMessagesResource;
    private final AuthenticationClient authenticationClient;

    public User register(UserRequestDto userRequestDto) {
        User user = authenticationClient.register(userRequestDto);
        if (user.getId() == null) {
            log.error("%s : {}".formatted(exceptionMessagesResource.getCustomerNotCreated()));
            throw new CommonException(exceptionMessagesResource.getCustomerNotCreated());
        }
        return user;
    }
}
