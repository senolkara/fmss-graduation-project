package com.senolkarakurt.notificationservice.consumer;

import com.senolkarakurt.enums.AdvertisementStatus;
import com.senolkarakurt.enums.StatusType;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.notificationservice.client.service.PackageClientService;
import com.senolkarakurt.notificationservice.component.NotificationComponent;
import com.senolkarakurt.notificationservice.consumer.dto.NotificationAdvertisementDto;
import com.senolkarakurt.notificationservice.consumer.dto.NotificationCustomerPackageRequestDto;
import com.senolkarakurt.notificationservice.exception.ExceptionMessagesResource;
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
public class NotificationCustomerPackageRequestConsumer {

    private final PackageClientService packageClientService;

    @RabbitListener(bindings =
        @QueueBinding(
                value = @Queue(value = "${notification.customer.package.queue}", durable = "true"),
                exchange = @Exchange(value = "${notification.customer.package.exchange}", type = ExchangeTypes.FANOUT, durable = "true")
        )
    )
    public void sendNotificationCustomerPackageRequest(NotificationCustomerPackageRequestDto notificationCustomerPackageRequestDto) {
        packageClientService.save(notificationCustomerPackageRequestDto.getCustomerPackageRequestDto());
    }

}
