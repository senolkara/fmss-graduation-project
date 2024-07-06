package com.senolkarakurt.advertisementservice.producer;

import com.senolkarakurt.advertisementservice.config.RabbitAdvertisementConfig;
import com.senolkarakurt.advertisementservice.producer.dto.NotificationAdvertisementDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class NotificationAdvertisementProducer {

    private final AmqpTemplate rabbitTemplate;
    private final RabbitAdvertisementConfig rabbitAdvertisementConfig;

    public void sendNotificationAdvertisement(NotificationAdvertisementDto notificationAdvertisementDto) {
        log.info(String.format("message sent -> %s", notificationAdvertisementDto.toString()));
        rabbitTemplate.convertAndSend(rabbitAdvertisementConfig.getAdvertisementExchange(), "", notificationAdvertisementDto);
        log.info("notification sent. advertisement exchange:{}", rabbitAdvertisementConfig.getAdvertisementExchange());
    }

}
