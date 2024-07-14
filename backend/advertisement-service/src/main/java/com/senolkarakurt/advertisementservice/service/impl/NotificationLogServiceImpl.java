package com.senolkarakurt.advertisementservice.service.impl;

import com.senolkarakurt.advertisementservice.producer.NotificationSystemLogProducer;
import com.senolkarakurt.advertisementservice.producer.dto.NotificationSystemLogDto;
import com.senolkarakurt.advertisementservice.service.SystemLogService;
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
