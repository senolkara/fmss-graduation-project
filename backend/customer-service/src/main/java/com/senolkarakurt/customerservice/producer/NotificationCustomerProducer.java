package com.senolkarakurt.customerservice.producer;

import com.senolkarakurt.customerservice.config.RabbitCustomerConfig;
import com.senolkarakurt.customerservice.producer.dto.NotificationCustomerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class NotificationCustomerProducer {

    private final AmqpTemplate rabbitTemplate;
    private final RabbitCustomerConfig rabbitCustomerConfig;

    public void sendNotificationCustomer(NotificationCustomerDto notificationCustomerDto) {
        log.info(String.format("message sent -> %s", notificationCustomerDto.toString()));
        rabbitTemplate.convertAndSend(rabbitCustomerConfig.getCustomerExchange(), "", notificationCustomerDto);
        log.info("notification sent. customer exchange:{}", rabbitCustomerConfig.getCustomerExchange());
    }

}
