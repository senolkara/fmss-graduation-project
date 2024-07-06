package com.senolkarakurt.advertisementservice.client.service;

import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.advertisementservice.client.CustomerClient;
import com.senolkarakurt.advertisementservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.advertisementservice.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerClientService {

    private final ExceptionMessagesResource exceptionMessagesResource;
    private final CustomerClient customerClient;

    public Customer getCustomerById(Long id) {
        Customer customer = customerClient.getCustomerById(id);
        if (customer == null) {
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getCustomerNotFound(), id));
            throw new CommonException(exceptionMessagesResource.getCustomerNotFound());
        }
        return customer;
    }

}
