package com.senolkarakurt.advertisementservice.client.service;

import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.advertisementservice.client.UserClient;
import com.senolkarakurt.advertisementservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.advertisementservice.model.Address;
import com.senolkarakurt.advertisementservice.model.User;
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

    public User getUserById(Long id) {
        User user = userClient.getUserById(id);
        if (user.getId() == null) {
            log.error("%s : {}".formatted(exceptionMessagesResource.getCustomerNotFound()));
            throw new CommonException(exceptionMessagesResource.getCustomerNotFound());
        }
        return user;
    }

    public Set<Address> getAddressesByUserId(Long userId){
        return userClient.getAddressesByUserId(userId);
    }

}
