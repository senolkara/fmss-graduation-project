package com.senolkarakurt.notificationservice.converter;

import com.senolkarakurt.dto.request.InvoiceNotificationRequestDto;
import com.senolkarakurt.dto.response.*;
import com.senolkarakurt.notificationservice.consumer.dto.NotificationInvoiceDto;
import com.senolkarakurt.notificationservice.model.InvoiceNotification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvoiceNotificationConverter {
    public static InvoiceNotification toInvoiceNotificationByInvoiceNotificationRequestDto(InvoiceNotificationRequestDto invoiceNotificationRequestDto) {
        return InvoiceNotification.builder()
                .notificationType(invoiceNotificationRequestDto.getNotificationType())
                .statusType(invoiceNotificationRequestDto.getStatusType())
                .totalPrice(invoiceNotificationRequestDto.getTotalPrice())
                .orderCode(invoiceNotificationRequestDto.getOrderCode())
                .name(invoiceNotificationRequestDto.getName())
                .surname(invoiceNotificationRequestDto.getSurname())
                .email(invoiceNotificationRequestDto.getEmail())
                .build();
    }

    public static InvoiceNotificationRequestDto toInvoiceNotificationRequestDtoByNotificationInvoiceDto(NotificationInvoiceDto notificationInvoiceDto){
        return InvoiceNotificationRequestDto.builder()
                .notificationType(notificationInvoiceDto.getNotificationType())
                .totalPrice(notificationInvoiceDto.getInvoiceResponseDto().getTotalPrice())
                .orderCode(notificationInvoiceDto.getInvoiceResponseDto().getOrderResponseDto().getOrderCode())
                .name(notificationInvoiceDto.getInvoiceResponseDto().getOrderResponseDto().getCustomerResponseDto().getUserResponseDto().getName())
                .surname(notificationInvoiceDto.getInvoiceResponseDto().getOrderResponseDto().getCustomerResponseDto().getUserResponseDto().getSurname())
                .email(notificationInvoiceDto.getInvoiceResponseDto().getOrderResponseDto().getCustomerResponseDto().getUserResponseDto().getEmail())
                .build();
    }

    public static InvoiceNotificationResponseDto toInvoiceNotificationResponseDtoByInvoiceNotification(InvoiceNotification invoiceNotification){
        InvoiceNotificationResponseDto invoiceNotificationResponseDto = new InvoiceNotificationResponseDto();
        invoiceNotificationResponseDto.setId(invoiceNotification.getId());
        invoiceNotificationResponseDto.setNotificationType(invoiceNotification.getNotificationType());
        invoiceNotificationResponseDto.setStatusType(invoiceNotification.getStatusType());
        invoiceNotificationResponseDto.setTotalPrice(invoiceNotification.getTotalPrice());
        invoiceNotificationResponseDto.setOrderCode(invoiceNotification.getOrderCode());
        invoiceNotificationResponseDto.setName(invoiceNotification.getName());
        invoiceNotificationResponseDto.setSurname(invoiceNotification.getSurname());
        invoiceNotificationResponseDto.setEmail(invoiceNotification.getEmail());
        return invoiceNotificationResponseDto;
    }

    public static NotificationInvoiceDto toNotificationInvoiceDtoByInvoiceNotification(InvoiceNotification invoiceNotification){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(invoiceNotification.getName());
        userResponseDto.setSurname(invoiceNotification.getSurname());
        userResponseDto.setEmail(invoiceNotification.getEmail());
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.setUserResponseDto(userResponseDto);
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderCode(invoiceNotification.getOrderCode());
        orderResponseDto.setCustomerResponseDto(customerResponseDto);
        InvoiceResponseDto invoiceResponseDto = new InvoiceResponseDto();
        invoiceResponseDto.setTotalPrice(invoiceNotification.getTotalPrice());
        PurchaseResponseDto purchaseResponseDto = PurchaseResponseDto.builder()
                .orderResponseDto(orderResponseDto)
                .build();
        purchaseResponseDto.setOrderResponseDto(orderResponseDto);
        invoiceResponseDto.setPurchaseResponseDto(purchaseResponseDto);
        return NotificationInvoiceDto.builder()
                .notificationType(invoiceNotification.getNotificationType())
                .invoiceResponseDto(invoiceResponseDto)
                .build();
    }

    public static List<InvoiceNotificationResponseDto> toResponse(List<InvoiceNotification> invoiceNotificationList) {
        return invoiceNotificationList
                .stream()
                .map(InvoiceNotificationConverter::toInvoiceNotificationResponseDtoByInvoiceNotification)
                .collect(Collectors.toList());
    }
}
