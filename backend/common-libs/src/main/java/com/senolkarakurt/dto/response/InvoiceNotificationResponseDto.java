package com.senolkarakurt.dto.response;

import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceNotificationResponseDto {
    private String id;
    private NotificationType notificationType;
    private StatusType statusType;
    private BigDecimal totalPrice;
    private String orderCode;
    private String name;
    private String surname;
    private String email;
}
