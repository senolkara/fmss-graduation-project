package com.senolkarakurt.purchaseservice.producer;

import com.senolkarakurt.purchaseservice.config.RabbitInvoiceConfig;
import com.senolkarakurt.purchaseservice.producer.dto.NotificationInvoiceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class NotificationInvoiceProducer {

    private final AmqpTemplate rabbitInvoiceTemplate;
    private final RabbitInvoiceConfig rabbitInvoiceConfig;

    public void sendNotificationInvoice(NotificationInvoiceDto notificationInvoiceDto) {
        log.info(String.format("message sent -> %s", notificationInvoiceDto.toString()));
        rabbitInvoiceTemplate.convertAndSend(rabbitInvoiceConfig.getInvoiceExchange(), "", notificationInvoiceDto);
        log.info("notification sent. invoice exchange:{}", rabbitInvoiceConfig.getInvoiceExchange());
    }

}
