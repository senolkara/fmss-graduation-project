package com.senolkarakurt.orderservice.client.service;

import com.senolkarakurt.orderservice.client.CustomerClient;
import com.senolkarakurt.orderservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.orderservice.model.Customer;
import com.senolkarakurt.exception.CommonException;
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
