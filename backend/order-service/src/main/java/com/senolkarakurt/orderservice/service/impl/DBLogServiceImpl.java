package com.senolkarakurt.orderservice.service.impl;

import com.senolkarakurt.orderservice.client.service.UserClientService;
import com.senolkarakurt.orderservice.model.SystemLog;
import com.senolkarakurt.orderservice.model.User;
import com.senolkarakurt.orderservice.repository.SystemLogRepository;
import com.senolkarakurt.orderservice.service.SystemLogService;
import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("dbLogService")
@RequiredArgsConstructor
public class DBLogServiceImpl implements SystemLogService {

    private final SystemLogRepository systemLogRepository;
    private final UserClientService userClientService;

    @Override
    public void save(SystemLogSaveRequestDto systemLogSaveRequestDto) {
        User user = userClientService.getUserById(systemLogSaveRequestDto.getUserId());
        Long userId = user != null ? user.getId() : null;
        SystemLog systemLog = SystemLog.builder()
                .userId(userId)
                .recordDateTime(LocalDateTime.now())
                .content(systemLogSaveRequestDto.getContent())
                .build();
        systemLogRepository.save(systemLog);
    }
}
