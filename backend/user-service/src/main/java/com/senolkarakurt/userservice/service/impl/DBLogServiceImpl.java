package com.senolkarakurt.userservice.service.impl;

import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.userservice.model.SystemLog;
import com.senolkarakurt.userservice.model.User;
import com.senolkarakurt.userservice.repository.SystemLogRepository;
import com.senolkarakurt.userservice.repository.UserRepository;
import com.senolkarakurt.userservice.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service("dbLogService")
@RequiredArgsConstructor
public class DBLogServiceImpl implements SystemLogService {

    private final SystemLogRepository systemLogRepository;
    private final UserRepository userRepository;

    @Override
    public void save(SystemLogSaveRequestDto systemLogSaveRequestDto) {
        User user = null;
        Optional<User> userOptional = userRepository.findById(systemLogSaveRequestDto.getUserId());
        if (userOptional.isPresent()){
            user = userOptional.get();
        }
        Long userId = user != null ? user.getId() : null;
        SystemLog systemLog = SystemLog.builder()
                .userId(userId)
                .recordDateTime(LocalDateTime.now())
                .content(systemLogSaveRequestDto.getContent())
                .build();
        systemLogRepository.save(systemLog);
    }
}
