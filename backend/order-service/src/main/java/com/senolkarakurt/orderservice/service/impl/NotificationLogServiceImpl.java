package com.senolkarakurt.orderservice.service.impl;

import com.senolkarakurt.orderservice.client.service.UserClientService;
import com.senolkarakurt.orderservice.producer.NotificationSystemLogProducer;
import com.senolkarakurt.orderservice.producer.dto.NotificationSystemLogDto;
import com.senolkarakurt.orderservice.service.SystemLogService;
import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("notificationLogService")
@RequiredArgsConstructor
public class NotificationLogServiceImpl implements SystemLogService {

    private final UserClientService userClientService;
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
