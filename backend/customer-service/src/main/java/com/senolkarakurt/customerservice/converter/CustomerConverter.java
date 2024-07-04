package com.senolkarakurt.customerservice.converter;

import com.senolkarakurt.customerservice.dto.request.CustomerSaveRequestDto;
import com.senolkarakurt.customerservice.model.Address;
import com.senolkarakurt.customerservice.model.User;
import com.senolkarakurt.customerservice.producer.dto.NotificationCustomerDto;
import com.senolkarakurt.dto.response.AddressResponseDto;
import com.senolkarakurt.dto.response.CustomerResponseDto;
import com.senolkarakurt.customerservice.model.Customer;
import com.senolkarakurt.dto.response.UserResponseDto;
import com.senolkarakurt.enums.AccountType;
import com.senolkarakurt.enums.NotificationType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerConverter {

    public static CustomerResponseDto toCustomerResponseDtoByCustomer(Customer customer){
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setId(customer.getId());
        customerResponseDto.setAccountType(customer.getAccountType());
        customerResponseDto.setScore(customer.getScore());
        return customerResponseDto;
    }

    public static Customer toCustomerByCustomerSaveRequestDto(CustomerSaveRequestDto customerSaveRequestDto){
        return Customer.builder()
                .userId(customerSaveRequestDto.getUserId())
                .accountType(AccountType.STANDARD)
                .score(0)
                .build();
    }

    public static List<CustomerResponseDto> toResponse(List<Customer> customerList, List<User> userList, Set<Address> addressSet) {
        List<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        customerList.forEach(customer -> {
            CustomerResponseDto customerResponseDto = CustomerConverter.toCustomerResponseDtoByCustomer(customer);
            Optional<User> userOptional = userList
                    .stream()
                    .filter(user -> customer.getUserId().equals(user.getId()))
                    .findFirst();
            if (userOptional.isPresent()){
                UserResponseDto userResponseDto = UserConverter.toUserResponseDtoByUser(userOptional.get());
                Set<Address> addresses = addressSet
                        .stream()
                        .filter(address -> address.getUserId().equals(userOptional.get().getId()))
                        .collect(Collectors.toSet());
                Set<AddressResponseDto> addressResponseDtoSet = AddressConverter.toSetAddressesResponseDtoBySetAddresses(addresses);
                userResponseDto.setAddressResponseDtoSet(addressResponseDtoSet);
                customerResponseDto.setUserResponseDto(userResponseDto);
            }
            customerResponseDtoList.add(customerResponseDto);
        });
        return customerResponseDtoList;
    }

    public static NotificationCustomerDto toNotificationCustomerDtoByNotificationTypeAndCustomer(NotificationType notificationType, Customer customer){
        NotificationCustomerDto notificationCustomerDto = new NotificationCustomerDto();
        notificationCustomerDto.setNotificationType(notificationType);
        notificationCustomerDto.setCustomerResponseDto(toCustomerResponseDtoByCustomer(customer));
        return notificationCustomerDto;
    }
}
