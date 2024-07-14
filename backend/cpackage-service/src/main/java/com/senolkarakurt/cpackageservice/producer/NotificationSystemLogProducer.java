package com.senolkarakurt.cpackageservice.producer;

import com.senolkarakurt.cpackageservice.config.RabbitSystemLogConfig;
import com.senolkarakurt.cpackageservice.producer.dto.NotificationSystemLogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class NotificationSystemLogProducer {

    private final AmqpTemplate rabbitInvoiceTemplate;
    private final RabbitSystemLogConfig rabbitSystemLogConfig;

    public void sendNotificationSystemLog(NotificationSystemLogDto notificationSystemLogDto) {
        log.info(String.format("message sent -> %s", notificationSystemLogDto.toString()));
        rabbitInvoiceTemplate.convertAndSend(rabbitSystemLogConfig.getSystemLogExchange(), "", notificationSystemLogDto);
        log.info("notification sent. system log exchange:{}", rabbitSystemLogConfig.getSystemLogExchange());
    }

}
