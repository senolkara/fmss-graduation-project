package com.senolkarakurt.customerservice.service;

import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;

public interface SystemLogService {
    void save(SystemLogSaveRequestDto systemLogSaveRequestDto);
}
