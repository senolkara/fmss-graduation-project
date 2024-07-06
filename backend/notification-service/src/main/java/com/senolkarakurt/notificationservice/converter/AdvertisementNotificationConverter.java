package com.senolkarakurt.notificationservice.converter;

import com.senolkarakurt.dto.request.AdvertisementNotificationRequestDto;
import com.senolkarakurt.dto.response.AdvertisementNotificationResponseDto;
import com.senolkarakurt.notificationservice.consumer.dto.NotificationAdvertisementDto;
import com.senolkarakurt.notificationservice.model.AdvertisementNotification;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdvertisementNotificationConverter {

    public static AdvertisementNotification toAdvertisementNotificationByAdvertisementNotificationRequestDto(AdvertisementNotificationRequestDto advertisementNotificationRequestDto) {
        return AdvertisementNotification.builder()
                .notificationType(advertisementNotificationRequestDto.getNotificationType())
                .statusType(advertisementNotificationRequestDto.getStatusType())
                .advertisementId(advertisementNotificationRequestDto.getAdvertisementId())
                .advertisementNo(advertisementNotificationRequestDto.getAdvertisementNo())
                .name(advertisementNotificationRequestDto.getName())
                .surname(advertisementNotificationRequestDto.getSurname())
                .email(advertisementNotificationRequestDto.getEmail())
                .build();
    }

    public static AdvertisementNotificationResponseDto toAdvertisementNotificationResponseDtoByAdvertisementNotification(AdvertisementNotification advertisementNotification){
        AdvertisementNotificationResponseDto advertisementNotificationResponseDto = new AdvertisementNotificationResponseDto();
        advertisementNotificationResponseDto.setId(advertisementNotification.getId());
        advertisementNotificationResponseDto.setNotificationType(advertisementNotification.getNotificationType());
        advertisementNotificationResponseDto.setStatusType(advertisementNotification.getStatusType());
        advertisementNotificationResponseDto.setAdvertisementId(advertisementNotification.getAdvertisementId());
        advertisementNotificationResponseDto.setAdvertisementNo(advertisementNotification.getAdvertisementNo());
        advertisementNotificationResponseDto.setName(advertisementNotification.getName());
        advertisementNotificationResponseDto.setSurname(advertisementNotification.getSurname());
        advertisementNotificationResponseDto.setEmail(advertisementNotification.getEmail());
        return advertisementNotificationResponseDto;
    }

    public static AdvertisementNotificationRequestDto toAdvertisementNotificationRequestDtoByNotificationAdvertisementDto(NotificationAdvertisementDto notificationAdvertisementDto){
        return AdvertisementNotificationRequestDto.builder()
                .notificationType(notificationAdvertisementDto.getNotificationType())
                .advertisementId(notificationAdvertisementDto.getAdvertisementResponseDto().getId())
                .advertisementNo(notificationAdvertisementDto.getAdvertisementResponseDto().getAdvertisementNo())
                .name(notificationAdvertisementDto.getAdvertisementResponseDto().getCustomerPackageResponseDto().getCustomerResponseDto().getUserResponseDto().getName())
                .surname(notificationAdvertisementDto.getAdvertisementResponseDto().getCustomerPackageResponseDto().getCustomerResponseDto().getUserResponseDto().getSurname())
                .email(notificationAdvertisementDto.getAdvertisementResponseDto().getCustomerPackageResponseDto().getCustomerResponseDto().getUserResponseDto().getEmail())
                .build();
    }

    public static List<AdvertisementNotificationResponseDto> toResponse(List<AdvertisementNotification> advertisementNotificationList) {
        return advertisementNotificationList
                .stream()
                .map(AdvertisementNotificationConverter::toAdvertisementNotificationResponseDtoByAdvertisementNotification)
                .collect(Collectors.toList());
    }
}
