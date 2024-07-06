package com.senolkarakurt.purchaseservice.producer.dto;

import com.senolkarakurt.dto.response.InvoiceResponseDto;
import com.senolkarakurt.enums.NotificationType;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationInvoiceDto implements Serializable {
    private NotificationType notificationType;
    private InvoiceResponseDto invoiceResponseDto;
}
