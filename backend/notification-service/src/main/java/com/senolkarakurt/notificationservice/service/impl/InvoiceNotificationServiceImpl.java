package com.senolkarakurt.notificationservice.service.impl;

import com.senolkarakurt.dto.request.InvoiceNotificationRequestDto;
import com.senolkarakurt.dto.response.InvoiceNotificationResponseDto;
import com.senolkarakurt.enums.StatusType;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.notificationservice.component.NotificationComponent;
import com.senolkarakurt.notificationservice.consumer.dto.NotificationInvoiceDto;
import com.senolkarakurt.notificationservice.converter.*;
import com.senolkarakurt.notificationservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.notificationservice.model.InvoiceNotification;
import com.senolkarakurt.notificationservice.repository.InvoiceNotificationRepository;
import com.senolkarakurt.notificationservice.service.InvoiceNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceNotificationServiceImpl implements InvoiceNotificationService {

    private final InvoiceNotificationRepository invoiceNotificationRepository;
    private final NotificationComponent notificationComponent;
    private final ExceptionMessagesResource exceptionMessagesResource;
    @Override
    public void save(InvoiceNotificationRequestDto invoiceNotificationRequestDto) {
        InvoiceNotification invoiceNotification = InvoiceNotificationConverter.toInvoiceNotificationByInvoiceNotificationRequestDto(invoiceNotificationRequestDto);
        invoiceNotificationRepository.save(invoiceNotification);
    }

    @Override
    public List<InvoiceNotificationResponseDto> getAll() {
        return InvoiceNotificationConverter.toResponse(invoiceNotificationRepository.findAll());
    }

    @Override
    public InvoiceNotificationResponseDto getById(String id) {
        Optional<InvoiceNotification> invoiceNotificationOptional = invoiceNotificationRepository.findById(id);
        if (invoiceNotificationOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getNotFoundNotification(), id));
            throw new CommonException(exceptionMessagesResource.getNotFoundNotification());
        }
        return InvoiceNotificationConverter.toInvoiceNotificationResponseDtoByInvoiceNotification(invoiceNotificationOptional.get());
    }

    @Override
    public void resendFailed() {
        List<InvoiceNotification> failedInvoiceNotificationList = invoiceNotificationRepository.findAll()
                .stream()
                .filter(invoiceNotification -> invoiceNotification.getStatusType().equals(StatusType.FAILED))
                .toList();
        failedInvoiceNotificationList.forEach(invoiceNotification -> {
            NotificationInvoiceDto notificationInvoiceDto = InvoiceNotificationConverter.toNotificationInvoiceDtoByInvoiceNotification(invoiceNotification);
            StatusType statusType = StatusType.SUCCESS;
            try{
                String message = "notification invoice : " + notificationInvoiceDto.toString();
                notificationComponent.sendMessage(message, notificationInvoiceDto.getNotificationType());
            } catch (CommonException commonException){
                statusType = StatusType.FAILED;
                log.error("%s : {}".formatted(exceptionMessagesResource.getNotSendNotification()));
            }
            invoiceNotification.setStatusType(statusType);
            invoiceNotificationRepository.save(invoiceNotification);
        });
    }
}
