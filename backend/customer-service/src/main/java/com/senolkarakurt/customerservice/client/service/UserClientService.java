package com.senolkarakurt.customerservice.client.service;

import com.senolkarakurt.customerservice.client.UserClient;
import com.senolkarakurt.customerservice.model.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserClientService {

    private final UserClient userClient;

    public Set<Address> getAddressesByUserId(Long userId){
        return userClient.getAddressesByUserId(userId);
    }

}
