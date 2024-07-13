package com.senolkarakurt.notificationservice.service;

import com.senolkarakurt.notificationservice.consumer.dto.NotificationSystemLogDto;

public interface SystemLogNotificationService {
    void save(NotificationSystemLogDto notificationSystemLogDto);
}
