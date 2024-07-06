package com.senolkarakurt.notificationservice.service;

import com.senolkarakurt.dto.request.AdvertisementNotificationRequestDto;
import com.senolkarakurt.dto.response.AdvertisementNotificationResponseDto;

import java.util.List;

public interface AdvertisementNotificationService {
    void save(AdvertisementNotificationRequestDto advertisementNotificationRequestDto);
    List<AdvertisementNotificationResponseDto> getAll();
    AdvertisementNotificationResponseDto getById(String id);
}
