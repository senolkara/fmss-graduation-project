package com.senolkarakurt.cpackageservice.client.service;

import com.senolkarakurt.cpackageservice.service.SystemLogService;
import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.cpackageservice.client.CustomerClient;
import com.senolkarakurt.cpackageservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.cpackageservice.model.Customer;
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
            saveSystemLog("%s : {} %s".formatted(exceptionMessagesResource.getCustomerNotFound(), id));
            throw new CommonException(exceptionMessagesResource.getCustomerNotFound());
        }
        return customer;
    }

    private void saveSystemLog(String content){
        SystemLogSaveRequestDto systemLogSaveRequestDto = SystemLogSaveRequestDto.builder()
                .recordDateTime(LocalDateTime.now())
                .content(content)
                .build();
        systemLogService.save(systemLogSaveRequestDto);
    }

}
