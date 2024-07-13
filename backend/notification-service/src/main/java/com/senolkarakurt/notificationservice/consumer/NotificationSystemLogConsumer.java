package com.senolkarakurt.notificationservice.consumer;

import com.senolkarakurt.notificationservice.consumer.dto.NotificationSystemLogDto;
import com.senolkarakurt.notificationservice.service.SystemLogNotificationService;
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
public class NotificationSystemLogConsumer {

    private final SystemLogNotificationService systemLogNotificationService;

    @RabbitListener(bindings =
        @QueueBinding(
                value = @Queue(value = "${notification.system.log.queue}", durable = "true"),
                exchange = @Exchange(value = "${notification.system.log.exchange}", type = ExchangeTypes.FANOUT, durable = "true")
        )
    )
    public void sendNotificationSystemLog(NotificationSystemLogDto notificationSystemLogDto){
        systemLogNotificationService.save(notificationSystemLogDto);
    }
}
