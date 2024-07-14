package com.senolkarakurt.cpackageservice.service;

import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;

public interface SystemLogService {
    void save(SystemLogSaveRequestDto systemLogSaveRequestDto);
}
