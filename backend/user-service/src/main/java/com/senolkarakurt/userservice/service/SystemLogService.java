package com.senolkarakurt.userservice.service;

import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;

public interface SystemLogService {
    void save(SystemLogSaveRequestDto systemLogSaveRequestDto);
}
