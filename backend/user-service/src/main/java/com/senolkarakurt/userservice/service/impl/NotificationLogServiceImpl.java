package com.senolkarakurt.userservice.service.impl;

import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.userservice.model.User;
import com.senolkarakurt.userservice.producer.NotificationSystemLogProducer;
import com.senolkarakurt.userservice.producer.dto.NotificationSystemLogDto;
import com.senolkarakurt.userservice.repository.UserRepository;
import com.senolkarakurt.userservice.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service("notificationLogService")
@RequiredArgsConstructor
public class NotificationLogServiceImpl implements SystemLogService {

    private final UserRepository userRepository;
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
