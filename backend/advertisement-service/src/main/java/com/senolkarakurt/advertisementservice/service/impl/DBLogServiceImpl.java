package com.senolkarakurt.advertisementservice.service.impl;

import com.senolkarakurt.advertisementservice.model.SystemLog;
import com.senolkarakurt.advertisementservice.repository.SystemLogRepository;
import com.senolkarakurt.advertisementservice.service.SystemLogService;
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
