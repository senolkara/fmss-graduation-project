package com.senolkarakurt.notificationservice.converter;

import com.senolkarakurt.dto.request.CustomerNotificationRequestDto;
import com.senolkarakurt.dto.response.CustomerNotificationResponseDto;
import com.senolkarakurt.dto.response.CustomerResponseDto;
import com.senolkarakurt.dto.response.UserResponseDto;
import com.senolkarakurt.notificationservice.consumer.dto.NotificationCustomerDto;
import com.senolkarakurt.notificationservice.model.CustomerNotification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerNotificationConverter {
    public static CustomerNotification toCustomerNotificationByCustomerNotificationRequestDto(CustomerNotificationRequestDto customerNotificationRequestDto) {
        return CustomerNotification.builder()
                .notificationType(customerNotificationRequestDto.getNotificationType())
                .statusType(customerNotificationRequestDto.getStatusType())
                .name(customerNotificationRequestDto.getName())
                .surname(customerNotificationRequestDto.getSurname())
                .email(customerNotificationRequestDto.getEmail())
                .build();
    }

    public static CustomerNotificationRequestDto toCustomerNotificationRequestDtoByNotificationCustomerDto(NotificationCustomerDto notificationCustomerDto){
        return CustomerNotificationRequestDto.builder()
                .notificationType(notificationCustomerDto.getNotificationType())
                .name(notificationCustomerDto.getCustomerResponseDto().getUserResponseDto().getName())
                .surname(notificationCustomerDto.getCustomerResponseDto().getUserResponseDto().getSurname())
                .email(notificationCustomerDto.getCustomerResponseDto().getUserResponseDto().getEmail())
                .build();
    }

    public static CustomerNotificationResponseDto toCustomerNotificationResponseDtoByCustomerNotification(CustomerNotification customerNotification){
        CustomerNotificationResponseDto customerNotificationResponseDto = new CustomerNotificationResponseDto();
        customerNotificationResponseDto.setId(customerNotification.getId());
        customerNotificationResponseDto.setNotificationType(customerNotification.getNotificationType());
        customerNotificationResponseDto.setStatusType(customerNotification.getStatusType());
        customerNotificationResponseDto.setName(customerNotification.getName());
        customerNotificationResponseDto.setSurname(customerNotification.getSurname());
        customerNotificationResponseDto.setEmail(customerNotification.getEmail());
        return customerNotificationResponseDto;
    }

    public static NotificationCustomerDto toNotificationCustomerDtoByCustomerNotification(CustomerNotification customerNotification){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(customerNotification.getName());
        userResponseDto.setSurname(customerNotification.getSurname());
        userResponseDto.setEmail(customerNotification.getEmail());
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setUserResponseDto(userResponseDto);
        return NotificationCustomerDto.builder()
                .notificationType(customerNotification.getNotificationType())
                .customerResponseDto(customerResponseDto)
                .build();
    }

    public static List<CustomerNotificationResponseDto> toResponse(List<CustomerNotification> customerNotificationList) {
        return customerNotificationList
                .stream()
                .map(CustomerNotificationConverter::toCustomerNotificationResponseDtoByCustomerNotification)
                .collect(Collectors.toList());
    }
}
