package com.senolkarakurt.notificationservice.consumer.dto;

import com.senolkarakurt.dto.response.CustomerResponseDto;
import com.senolkarakurt.enums.NotificationType;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class NotificationCustomerDto implements Serializable {
    private NotificationType notificationType;
    private CustomerResponseDto customerResponseDto;
}
