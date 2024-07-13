package com.senolkarakurt.customerservice.service.impl;

import com.senolkarakurt.customerservice.client.service.AuthenticationClientService;
import com.senolkarakurt.customerservice.client.service.UserClientService;
import com.senolkarakurt.customerservice.converter.AddressConverter;
import com.senolkarakurt.customerservice.converter.CustomerConverter;
import com.senolkarakurt.customerservice.converter.UserConverter;
import com.senolkarakurt.customerservice.dto.request.CustomerSaveRequestDto;
import com.senolkarakurt.customerservice.dto.request.CustomerUpdateRequestDto;
import com.senolkarakurt.customerservice.model.User;
import com.senolkarakurt.customerservice.producer.NotificationCustomerProducer;
import com.senolkarakurt.customerservice.producer.dto.NotificationCustomerDto;
import com.senolkarakurt.customerservice.service.SystemLogService;
import com.senolkarakurt.dto.request.CustomerRequestDto;
import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.dto.response.AddressResponseDto;
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
    public void save(CustomerRequestDto customerRequestDto) {
        User user = authenticationClientService.register(customerRequestDto.getUserRequestDto());
        CustomerSaveRequestDto customerSaveRequestDto = CustomerSaveRequestDto.builder()
                .userId(user.getId())
                .build();
        Customer customer = CustomerConverter.toCustomerByCustomerSaveRequestDto(customerSaveRequestDto);
        customerRepository.save(customer);

        /**
         * Customer kayıt olduktan sonra email at
         */
        NotificationCustomerDto notificationCustomerDto = CustomerConverter.toNotificationCustomerDtoByNotificationTypeAndCustomer(NotificationType.EMAIL, customer);
        UserResponseDto userResponseDto = UserConverter.toUserResponseDtoByUser(user);
        notificationCustomerDto.getCustomerResponseDto().setUserResponseDto(userResponseDto);
        Set<AddressResponseDto> addressResponseDtoSet = AddressConverter.toSetAddressesResponseDtoBySetAddresses(userClientService.getAddressesByUserId(user.getId()));
        notificationCustomerDto.getCustomerResponseDto().getUserResponseDto().setAddressResponseDtoSet(addressResponseDtoSet);
        notificationCustomerProducer.sendNotificationCustomer(notificationCustomerDto);
    }

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getCustomerNotFoundWithId(), id));
            SystemLogSaveRequestDto systemLogSaveRequestDto = SystemLogSaveRequestDto.builder()
                    .userId(null)
                    .recordDateTime(LocalDateTime.now())
                    .content("%s : {} %s".formatted(exceptionMessagesResource.getCustomerNotFoundWithId(), id))
                    .build();
            systemLogService.save(systemLogSaveRequestDto);
            throw new CommonException(exceptionMessagesResource.getCustomerNotFoundWithId());
        }
        return customerOptional.orElse(null);
    }

    @Override
    public void changeAccountTypeAndScore(Long id, CustomerUpdateRequestDto customerUpdateRequestDto) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getCustomerNotFoundWithId(), id));
            SystemLogSaveRequestDto systemLogSaveRequestDto = SystemLogSaveRequestDto.builder()
                    .userId(null)
                    .recordDateTime(LocalDateTime.now())
                    .content("%s : {} %s".formatted(exceptionMessagesResource.getCustomerNotFoundWithId(), id))
                    .build();
            systemLogService.save(systemLogSaveRequestDto);
            throw new CommonException(exceptionMessagesResource.getCustomerNotFoundWithId());
        }
        Customer customer = customerOptional.get();
        customer.setAccountType(customerUpdateRequestDto.getAccountType());
        customer.setScore(customerUpdateRequestDto.getScore());
        customerRepository.save(customer);
    }
}
