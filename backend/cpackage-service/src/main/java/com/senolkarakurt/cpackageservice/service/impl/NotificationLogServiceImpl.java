package com.senolkarakurt.cpackageservice.service.impl;

import com.senolkarakurt.cpackageservice.producer.NotificationSystemLogProducer;
import com.senolkarakurt.cpackageservice.producer.dto.NotificationSystemLogDto;
import com.senolkarakurt.cpackageservice.service.SystemLogService;
import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("notificationLogService")
@RequiredArgsConstructor
public class NotificationLogServiceImpl implements SystemLogService {

    private final NotificationSystemLogProducer notificationSystemLogProducer;

    @Override
    public void save(SystemLogSaveRequestDto systemLogSaveRequestDto) {
        NotificationSystemLogDto notificationSystemLogDto = NotificationSystemLogDto.builder()
                .recordDateTime(LocalDateTime.now())
                .content(systemLogSaveRequestDto.getContent())
                .build();
        notificationSystemLogProducer.sendNotificationSystemLog(notificationSystemLogDto);
    }
}
