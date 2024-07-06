package com.senolkarakurt.notificationservice.service.impl;

import com.senolkarakurt.dto.request.AdvertisementNotificationRequestDto;
import com.senolkarakurt.dto.response.AdvertisementNotificationResponseDto;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.notificationservice.converter.AdvertisementNotificationConverter;
import com.senolkarakurt.notificationservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.notificationservice.model.AdvertisementNotification;
import com.senolkarakurt.notificationservice.repository.AdvertisementNotificationRepository;
import com.senolkarakurt.notificationservice.service.AdvertisementNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertisementNotificationServiceImpl implements AdvertisementNotificationService {

    private final AdvertisementNotificationRepository advertisementNotificationRepository;
    private final ExceptionMessagesResource exceptionMessagesResource;

    @Override
    public void save(AdvertisementNotificationRequestDto advertisementNotificationRequestDto) {
        AdvertisementNotification advertisementNotification = AdvertisementNotificationConverter.toAdvertisementNotificationByAdvertisementNotificationRequestDto(advertisementNotificationRequestDto);
        advertisementNotificationRepository.save(advertisementNotification);
    }

    @Override
    public List<AdvertisementNotificationResponseDto> getAll() {
        return AdvertisementNotificationConverter.toResponse(advertisementNotificationRepository.findAll());
    }

    @Override
    public AdvertisementNotificationResponseDto getById(String id) {
        Optional<AdvertisementNotification> advertisementNotificationOptional = advertisementNotificationRepository.findById(id);
        if (advertisementNotificationOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getNotFoundNotification(), id));
            throw new CommonException(exceptionMessagesResource.getNotFoundNotification());
        }
        return AdvertisementNotificationConverter.toAdvertisementNotificationResponseDtoByAdvertisementNotification(advertisementNotificationOptional.get());
    }
}
