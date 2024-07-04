package com.senolkarakurt.customerservice.client.service;

import com.senolkarakurt.customerservice.client.UserClient;
import com.senolkarakurt.customerservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.customerservice.model.Address;
import com.senolkarakurt.customerservice.model.User;
import com.senolkarakurt.dto.request.UserRequestDto;
import com.senolkarakurt.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserClientService {

    private final ExceptionMessagesResource exceptionMessagesResource;
    private final UserClient userClient;

    public User save(UserRequestDto userRequestDto) {
        User user = userClient.save(userRequestDto);
        if (user.getId() == null) {
            log.error("%s : {}".formatted(exceptionMessagesResource.getCustomerNotCreated()));
            throw new CommonException(exceptionMessagesResource.getCustomerNotCreated());
        }
        return user;
    }

    public User getUserById(Long id) {
        User user = userClient.getUserById(id);
        if (user.getId() == null) {
            log.error("%s : {}".formatted(exceptionMessagesResource.getUserNotFoundWithId()));
            throw new CommonException(exceptionMessagesResource.getUserNotFoundWithId());
        }
        return user;
    }

    public Set<Address> getAddressesByUserId(Long userId){
        return userClient.getAddressesByUserId(userId);
    }

}
