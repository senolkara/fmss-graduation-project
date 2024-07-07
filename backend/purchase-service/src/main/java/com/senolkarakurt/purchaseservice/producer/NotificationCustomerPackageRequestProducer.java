package com.senolkarakurt.purchaseservice.producer;

import com.senolkarakurt.purchaseservice.config.RabbitCustomerPackageConfig;
import com.senolkarakurt.purchaseservice.producer.dto.NotificationCustomerPackageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class NotificationCustomerPackageRequestProducer {

    private final AmqpTemplate rabbitCustomerPackageTemplate;
    private final RabbitCustomerPackageConfig rabbitCustomerPackageConfig;

    public void sendNotificationCustomerPackageRequest(NotificationCustomerPackageRequestDto notificationCustomerPackageRequestDto) {
        log.info(String.format("message sent -> %s", notificationCustomerPackageRequestDto.toString()));
        rabbitCustomerPackageTemplate.convertAndSend(rabbitCustomerPackageConfig.getCustomerPackageExchange(), "", notificationCustomerPackageRequestDto);
        log.info("notification sent. customer package exchange:{}", rabbitCustomerPackageConfig.getCustomerPackageExchange());
    }

}
