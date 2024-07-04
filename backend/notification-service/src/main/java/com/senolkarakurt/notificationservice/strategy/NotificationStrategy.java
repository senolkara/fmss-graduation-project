package com.senolkarakurt.notificationservice.strategy;

import com.senolkarakurt.enums.NotificationType;

public interface NotificationStrategy {
    void sendMessage(String message);
    NotificationType notificationType();
}
