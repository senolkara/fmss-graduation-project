package com.senolkarakurt.customerservice.service.impl;

import com.senolkarakurt.customerservice.client.service.UserClientService;
import com.senolkarakurt.customerservice.converter.AddressConverter;
import com.senolkarakurt.customerservice.converter.CustomerConverter;
import com.senolkarakurt.customerservice.converter.UserConverter;
import com.senolkarakurt.customerservice.dto.request.CustomerSaveRequestDto;
import com.senolkarakurt.customerservice.dto.request.CustomerUpdateRequestDto;
import com.senolkarakurt.customerservice.model.Address;
import com.senolkarakurt.customerservice.model.User;
import com.senolkarakurt.customerservice.producer.NotificationCustomerProducer;
import com.senolkarakurt.customerservice.producer.dto.NotificationCustomerDto;
import com.senolkarakurt.dto.request.CustomerRequestDto;
import com.senolkarakurt.dto.response.AddressResponseDto;
import com.senolkarakurt.dto.response.CustomerResponseDto;
import com.senolkarakurt.customerservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.customerservice.model.Customer;
import com.senolkarakurt.customerservice.repository.CustomerRepository;
import com.senolkarakurt.customerservice.service.CustomerService;
import com.senolkarakurt.dto.response.UserResponseDto;
import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserClientService userClientService;
    private final ExceptionMessagesResource exceptionMessagesResource;
    private final NotificationCustomerProducer notificationCustomerProducer;

    @Override
    public void save(CustomerRequestDto customerRequestDto) {
        User user = userClientService.save(customerRequestDto.getUserRequestDto());
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
    public List<CustomerResponseDto> getAll() {
        List<Customer> customerList = customerRepository.findAll();
        List<User> userList = new ArrayList<>();
        Set<Address> addressSet = new HashSet<>();
        customerList.forEach(customer -> {
            userList.add(userClientService.getUserById(customer.getUserId()));
            addressSet.addAll(userClientService.getAddressesByUserId(customer.getUserId()));
        });
        return CustomerConverter.toResponse(customerList, userList, addressSet);
    }

    @Override
    public CustomerResponseDto getById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getCustomerNotFoundWithId(), id));
            throw new CommonException(exceptionMessagesResource.getCustomerNotFoundWithId());
        }
        User user = userClientService.getUserById(customerOptional.get().getUserId());
        UserResponseDto userResponseDto = UserConverter.toUserResponseDtoByUser(user);
        CustomerResponseDto customerResponseDto = CustomerConverter.toCustomerResponseDtoByCustomer(customerOptional.get());
        customerResponseDto.setUserResponseDto(userResponseDto);
        return customerResponseDto;
    }

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerOptional.orElse(null);
    }

    @Override
    public void changeAccountTypeAndScore(CustomerUpdateRequestDto customerUpdateRequestDto) {
        Customer customer = customerUpdateRequestDto.getCustomer();
        customer.setAccountType(customerUpdateRequestDto.getAccountType());
        customer.setScore(customerUpdateRequestDto.getScore());
    }
}
