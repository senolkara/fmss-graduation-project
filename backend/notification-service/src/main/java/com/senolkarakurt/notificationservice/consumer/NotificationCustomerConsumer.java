package com.senolkarakurt.notificationservice.consumer;

import com.senolkarakurt.dto.request.CustomerNotificationRequestDto;
import com.senolkarakurt.enums.StatusType;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.notificationservice.component.NotificationComponent;
import com.senolkarakurt.notificationservice.consumer.dto.NotificationCustomerDto;
import com.senolkarakurt.notificationservice.converter.CustomerNotificationConverter;
import com.senolkarakurt.notificationservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.notificationservice.service.CustomerNotificationService;
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
public class NotificationCustomerConsumer {

    private final CustomerNotificationService customerNotificationService;
    private final NotificationComponent notificationComponent;
    private final ExceptionMessagesResource exceptionMessagesResource;

    @RabbitListener(bindings =
        @QueueBinding(
                value = @Queue(value = "${notification.customer.queue}", durable = "true"),
                exchange = @Exchange(value = "${notification.customer.exchange}", type = ExchangeTypes.FANOUT, durable = "true")
        )
    )
    public void sendNotificationCustomer(NotificationCustomerDto notificationCustomerDto) {
        StatusType statusType = StatusType.SUCCESS;
        try{
            String message = "notification customer : " + notificationCustomerDto.toString();
            notificationComponent.sendMessage(message, notificationCustomerDto.getNotificationType());
        } catch (CommonException commonException){
            statusType = StatusType.FAILED;
            log.error("%s : {}".formatted(exceptionMessagesResource.getNotSendNotification()));
        }
        saveCustomerNotificationMessage(notificationCustomerDto, statusType);
    }

    public void saveCustomerNotificationMessage(NotificationCustomerDto notificationCustomerDto, StatusType statusType){
        CustomerNotificationRequestDto customerNotificationRequestDto = CustomerNotificationConverter.toCustomerNotificationRequestDtoByNotificationCustomerDto(notificationCustomerDto);
        customerNotificationRequestDto.setStatusType(statusType);
        customerNotificationService.save(customerNotificationRequestDto);
    }
}
