package com.senolkarakurt.dto.request;

import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.enums.StatusType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceNotificationRequestDto {
    private NotificationType notificationType;
    private StatusType statusType;
    private BigDecimal totalPrice;
    private String orderCode;
    private String name;
    private String surname;
    private String email;
}
