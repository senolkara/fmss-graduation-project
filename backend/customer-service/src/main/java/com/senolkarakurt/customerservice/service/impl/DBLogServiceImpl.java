package com.senolkarakurt.customerservice.service.impl;

import com.senolkarakurt.customerservice.client.service.UserClientService;
import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.customerservice.model.SystemLog;
import com.senolkarakurt.customerservice.model.User;
import com.senolkarakurt.customerservice.repository.SystemLogRepository;
import com.senolkarakurt.customerservice.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
