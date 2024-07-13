package com.senolkarakurt.notificationservice.service.impl;

import com.senolkarakurt.notificationservice.consumer.dto.NotificationSystemLogDto;
import com.senolkarakurt.notificationservice.model.SystemLogNotification;
import com.senolkarakurt.notificationservice.repository.SystemLogNotificationRepository;
import com.senolkarakurt.notificationservice.service.SystemLogNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemLogNotificationServiceImpl implements SystemLogNotificationService {

    private final SystemLogNotificationRepository systemLogNotificationRepository;

    @Override
    public void save(NotificationSystemLogDto notificationSystemLogDto) {
        SystemLogNotification systemLogNotification = SystemLogNotification.builder()
                .userId(notificationSystemLogDto.getUserId())
                .recordDateTime(notificationSystemLogDto.getRecordDateTime())
                .content(notificationSystemLogDto.getContent())
                .build();
        systemLogNotificationRepository.save(systemLogNotification);
    }
}
