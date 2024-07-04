package com.senolkarakurt.notificationservice.consumer;

import com.senolkarakurt.dto.request.InvoiceNotificationRequestDto;
import com.senolkarakurt.enums.StatusType;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.notificationservice.component.NotificationComponent;
import com.senolkarakurt.notificationservice.consumer.dto.NotificationInvoiceDto;
import com.senolkarakurt.notificationservice.converter.*;
import com.senolkarakurt.notificationservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.notificationservice.service.InvoiceNotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class NotificationInvoiceConsumer {

    private final InvoiceNotificationService invoiceNotificationService;
    private final NotificationComponent notificationComponent;
    private final ExceptionMessagesResource exceptionMessagesResource;

    @RabbitListener(bindings =
        @QueueBinding(
                value = @Queue(value = "${notification.invoice.queue}", durable = "true"),
                exchange = @Exchange(value = "${notification.invoice.exchange}", type = ExchangeTypes.FANOUT, durable = "true")
        )
    )
    public void sendNotificationInvoice(NotificationInvoiceDto notificationInvoiceDto) {
        StatusType statusType = StatusType.SUCCESS;
        try{
            String message = "notification invoice : " + notificationInvoiceDto.toString();
            notificationComponent.sendMessage(message, notificationInvoiceDto.getNotificationType());
        } catch (CommonException commonException){
            statusType = StatusType.FAILED;
            log.error("%s : {}".formatted(exceptionMessagesResource.getNotSendNotification()));
        }
        saveInvoiceNotificationMessage(notificationInvoiceDto, statusType);
    }

    public void saveInvoiceNotificationMessage(NotificationInvoiceDto notificationInvoiceDto, StatusType statusType){
        InvoiceNotificationRequestDto invoiceNotificationRequestDto = InvoiceNotificationConverter.toInvoiceNotificationRequestDtoByNotificationInvoiceDto(notificationInvoiceDto);
        invoiceNotificationRequestDto.setStatusType(statusType);
        invoiceNotificationService.save(invoiceNotificationRequestDto);
    }
}
