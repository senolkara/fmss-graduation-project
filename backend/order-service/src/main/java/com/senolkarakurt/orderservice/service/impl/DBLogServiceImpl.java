package com.senolkarakurt.orderservice.service.impl;

import com.senolkarakurt.orderservice.model.SystemLog;
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

    @Override
    public void save(SystemLogSaveRequestDto systemLogSaveRequestDto) {
        SystemLog systemLog = SystemLog.builder()
                .recordDateTime(LocalDateTime.now())
                .content(systemLogSaveRequestDto.getContent())
                .build();
        systemLogRepository.save(systemLog);
    }
}
