package com.senolkarakurt.notificationservice.service;

import com.senolkarakurt.dto.request.CustomerNotificationRequestDto;
import com.senolkarakurt.dto.response.CustomerNotificationResponseDto;

import java.util.List;

public interface CustomerNotificationService {
    void save(CustomerNotificationRequestDto customerNotificationRequestDto);
    List<CustomerNotificationResponseDto> getAll();
    CustomerNotificationResponseDto getById(String id);
    void resendFailed();
}
