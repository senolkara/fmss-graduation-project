package com.senolkarakurt.orderservice.client.service;

import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.enums.AccountType;
import com.senolkarakurt.orderservice.client.CustomerClient;
import com.senolkarakurt.orderservice.dto.request.CustomerUpdateRequestDto;
import com.senolkarakurt.orderservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.orderservice.model.Customer;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.orderservice.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CustomerClientService {

    private final ExceptionMessagesResource exceptionMessagesResource;
    private final CustomerClient customerClient;
    private final SystemLogService systemLogService;

    public CustomerClientService(ExceptionMessagesResource exceptionMessagesResource,
                                 CustomerClient customerClient,
                                 @Qualifier("textFileLogService") SystemLogService systemLogService) {
        this.exceptionMessagesResource = exceptionMessagesResource;
        this.customerClient = customerClient;
        this.systemLogService = systemLogService;
    }

    public Customer getCustomerById(Long id) {
        Customer customer = customerClient.getCustomerById(id);
        if (customer == null) {
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getCustomerNotFound(), id));
            SystemLogSaveRequestDto systemLogSaveRequestDto = SystemLogSaveRequestDto.builder()
                    .userId(null)
                    .recordDateTime(LocalDateTime.now())
                    .content("%s : {} %s".formatted(exceptionMessagesResource.getCustomerNotFound(), id))
                    .build();
            systemLogService.save(systemLogSaveRequestDto);
            throw new CommonException(exceptionMessagesResource.getCustomerNotFound());
        }
        return customer;
    }

    public void changeAccountTypeAndScore(Long id, AccountType accountType, Integer score){
        CustomerUpdateRequestDto customerUpdateRequestDto = CustomerUpdateRequestDto.builder()
                .accountType(accountType)
                .score(score)
                .build();
        customerClient.changeAccountTypeAndScore(id, customerUpdateRequestDto);
    }

}
