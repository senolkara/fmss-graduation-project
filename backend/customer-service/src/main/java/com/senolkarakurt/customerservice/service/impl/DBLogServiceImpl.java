package com.senolkarakurt.customerservice.service.impl;

import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.customerservice.model.SystemLog;
import com.senolkarakurt.customerservice.repository.SystemLogRepository;
import com.senolkarakurt.customerservice.service.SystemLogService;
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
