package com.senolkarakurt.customerservice.service.impl;

import com.senolkarakurt.customerservice.client.service.UserClientService;
import com.senolkarakurt.customerservice.model.User;
import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.customerservice.producer.NotificationSystemLogProducer;
import com.senolkarakurt.customerservice.producer.dto.NotificationSystemLogDto;
import com.senolkarakurt.customerservice.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service("notificationLogService")
@RequiredArgsConstructor
public class NotificationLogServiceImpl implements SystemLogService {

    private final UserClientService userClientService;
    private final NotificationSystemLogProducer notificationSystemLogProducer;

    @Override
    public void save(SystemLogSaveRequestDto systemLogSaveRequestDto) {
        User user = userClientService.getUserById(systemLogSaveRequestDto.getUserId());
        Long userId = user != null ? user.getId() : null;
        NotificationSystemLogDto notificationSystemLogDto = NotificationSystemLogDto.builder()
                .userId(userId)
                .recordDateTime(LocalDateTime.now())
                .content(systemLogSaveRequestDto.getContent())
                .build();
        notificationSystemLogProducer.sendNotificationSystemLog(notificationSystemLogDto);
    }
}
