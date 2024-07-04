package com.senolkarakurt.notificationservice.strategy.impl;

import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.notificationservice.strategy.NotificationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmsNotificationStrategy implements NotificationStrategy {
    @Override
    public void sendMessage(String message) {
        log.info("message send to phone" + message);
    }

    @Override
    public NotificationType notificationType() {
        return NotificationType.SMS;
    }
}
