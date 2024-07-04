package com.senolkarakurt.notificationservice.model;

import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.enums.StatusType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(value = "invoice_notification")
public class InvoiceNotification {

    @Id
    private String id;
    private NotificationType notificationType;
    private StatusType statusType;
    private BigDecimal totalPrice;
    private String orderCode;
    private String name;
    private String surname;
    private String email;
}
