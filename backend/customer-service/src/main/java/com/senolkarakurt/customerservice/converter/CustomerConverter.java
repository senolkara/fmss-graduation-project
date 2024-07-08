package com.senolkarakurt.customerservice.converter;

import com.senolkarakurt.customerservice.dto.request.CustomerSaveRequestDto;
import com.senolkarakurt.customerservice.producer.dto.NotificationCustomerDto;
import com.senolkarakurt.dto.response.CustomerResponseDto;
import com.senolkarakurt.customerservice.model.Customer;
import com.senolkarakurt.enums.AccountType;
import com.senolkarakurt.enums.NotificationType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerConverter {

    public static CustomerResponseDto toCustomerResponseDtoByCustomer(Customer customer){
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setId(customer.getId());
        customerResponseDto.setRecordStatus(customer.getRecordStatus());
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

    public static NotificationCustomerDto toNotificationCustomerDtoByNotificationTypeAndCustomer(NotificationType notificationType, Customer customer){
        NotificationCustomerDto notificationCustomerDto = new NotificationCustomerDto();
        notificationCustomerDto.setNotificationType(notificationType);
        notificationCustomerDto.setCustomerResponseDto(toCustomerResponseDtoByCustomer(customer));
        return notificationCustomerDto;
    }
}
