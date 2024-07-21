package com.senolkarakurt.customerservice.service.impl;

import com.senolkarakurt.customerservice.client.service.AuthenticationClientService;
import com.senolkarakurt.customerservice.client.service.UserClientService;
import com.senolkarakurt.customerservice.converter.CustomerConverter;
import com.senolkarakurt.customerservice.converter.UserConverter;
import com.senolkarakurt.customerservice.dto.request.CustomerSaveRequestDto;
import com.senolkarakurt.customerservice.dto.request.CustomerUpdateRequestDto;
import com.senolkarakurt.customerservice.dto.response.RegistrationResponseDto;
import com.senolkarakurt.customerservice.model.User;
import com.senolkarakurt.customerservice.producer.NotificationCustomerProducer;
import com.senolkarakurt.customerservice.producer.dto.NotificationCustomerDto;
import com.senolkarakurt.customerservice.service.SystemLogService;
import com.senolkarakurt.dto.request.RegistrationRequestDto;
import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.customerservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.customerservice.model.Customer;
import com.senolkarakurt.customerservice.repository.CustomerRepository;
import com.senolkarakurt.customerservice.service.CustomerService;
import com.senolkarakurt.dto.response.UserResponseDto;
import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserClientService userClientService;
    private final AuthenticationClientService authenticationClientService;
    private final ExceptionMessagesResource exceptionMessagesResource;
    private final NotificationCustomerProducer notificationCustomerProducer;
    private final SystemLogService systemLogService;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               UserClientService userClientService,
                               AuthenticationClientService authenticationClientService,
                               ExceptionMessagesResource exceptionMessagesResource,
                               NotificationCustomerProducer notificationCustomerProducer,
                               @Qualifier("dbLogService") SystemLogService systemLogService) {
        this.customerRepository = customerRepository;
        this.userClientService = userClientService;
        this.authenticationClientService = authenticationClientService;
        this.exceptionMessagesResource = exceptionMessagesResource;
        this.notificationCustomerProducer = notificationCustomerProducer;
        this.systemLogService = systemLogService;
    }

    @Override
    public RegistrationResponseDto save(RegistrationRequestDto registrationRequestDto) {
        RegistrationResponseDto registrationResponseDto = authenticationClientService.register(registrationRequestDto);
        CustomerSaveRequestDto customerSaveRequestDto = CustomerSaveRequestDto.builder()
                .userId(registrationResponseDto.getUser().getId())
                .build();
        Customer customer = CustomerConverter.toCustomerByCustomerSaveRequestDto(customerSaveRequestDto);
        customerRepository.save(customer);

        /**
         * Customer kayıt olduktan sonra email at
         */
        NotificationCustomerDto notificationCustomerDto = CustomerConverter.toNotificationCustomerDtoByNotificationTypeAndCustomer(NotificationType.EMAIL, customer);
        UserResponseDto userResponseDto = UserConverter.toUserResponseDtoByUser(registrationResponseDto.getUser());
        notificationCustomerDto.getCustomerResponseDto().setUserResponseDto(userResponseDto);
        notificationCustomerProducer.sendNotificationCustomer(notificationCustomerDto);
        return registrationResponseDto;
    }

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getCustomerNotFoundWithId(), id));
            saveSystemLog("%s : {} %s".formatted(exceptionMessagesResource.getCustomerNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getCustomerNotFoundWithId());
        }
        return customerOptional.orElse(null);
    }

    @Override
    public void changeAccountTypeAndScore(Long id, CustomerUpdateRequestDto customerUpdateRequestDto) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getCustomerNotFoundWithId(), id));
            saveSystemLog("%s : {} %s".formatted(exceptionMessagesResource.getCustomerNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getCustomerNotFoundWithId());
        }
        Customer customer = customerOptional.get();
        customer.setAccountType(customerUpdateRequestDto.getAccountType());
        customer.setScore(customerUpdateRequestDto.getScore());
        customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerByUserId(Long userId) {
        User user = userClientService.getUserById(userId);
        List<Customer> customerList = customerRepository.findByUserId(user.getId());
        if (customerList.isEmpty()){
            log.error("%s : {}".formatted(exceptionMessagesResource.getCustomerNotFound()));
            saveSystemLog("%s : {}".formatted(exceptionMessagesResource.getCustomerNotFound()));
            throw new CommonException(exceptionMessagesResource.getCustomerNotFound());
        }
        return customerList.getFirst();
    }

    private void saveSystemLog(String content) {
        SystemLogSaveRequestDto systemLogSaveRequestDto = SystemLogSaveRequestDto.builder()
                .recordDateTime(LocalDateTime.now())
                .content(content)
                .build();
        systemLogService.save(systemLogSaveRequestDto);
    }
}
