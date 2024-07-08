package com.senolkarakurt.purchaseservice.converter;

import com.senolkarakurt.dto.response.InvoiceResponseDto;
import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.purchaseservice.model.Invoice;
import com.senolkarakurt.purchaseservice.producer.dto.NotificationInvoiceDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvoiceConverter {

    public static InvoiceResponseDto toInvoiceResponseDtoByInvoice(Invoice invoice) {
        InvoiceResponseDto invoiceResponseDto = new InvoiceResponseDto();
        invoiceResponseDto.setId(invoice.getId());
        invoiceResponseDto.setRecordStatus(invoice.getRecordStatus());
        invoiceResponseDto.setTotalPrice(invoice.getTotalPrice());
        invoiceResponseDto.setCreateDateTime(invoice.getCreateDateTime());
        invoiceResponseDto.setInvoiceNo(invoice.getInvoiceNo());
        return invoiceResponseDto;
    }

    public static NotificationInvoiceDto toNotificationDtoByNotificationTypeAndInvoice(NotificationType notificationType, InvoiceResponseDto invoiceResponseDto){
        return NotificationInvoiceDto.builder()
                .notificationType(notificationType)
                .invoiceResponseDto(invoiceResponseDto)
                .build();
    }
}
