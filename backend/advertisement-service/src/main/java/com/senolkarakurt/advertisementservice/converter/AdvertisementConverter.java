package com.senolkarakurt.advertisementservice.converter;

import com.senolkarakurt.advertisementservice.model.Advertisement;
import com.senolkarakurt.advertisementservice.producer.dto.NotificationAdvertisementDto;
import com.senolkarakurt.dto.request.AdvertisementRequestDto;
import com.senolkarakurt.dto.response.AdvertisementResponseDto;
import com.senolkarakurt.enums.AdvertisementStatus;
import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.util.GenerateRandomUnique;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdvertisementConverter {

    public static Advertisement toAdvertisementByAdvertisementRequestDto(AdvertisementRequestDto advertisementRequestDto){
        return Advertisement.builder()
                .advertisementStatus(AdvertisementStatus.IN_REVIEW)
                .advertisementNo(GenerateRandomUnique.createRandomAdvertisementNo())
                .startDateTime(LocalDateTime.now())
                .finishDateTime(LocalDateTime.now().plusDays(30))
                .buildingId(advertisementRequestDto.getBuildingRequestDto().getId())
                .customerPackageId(advertisementRequestDto.getCustomerPackageRequestDto().getId())
                .build();
    }

    public static AdvertisementResponseDto toAdvertisementResponseDtoByAdvertisement(Advertisement advertisement){
        AdvertisementResponseDto advertisementResponseDto = new AdvertisementResponseDto();
        advertisementResponseDto.setId(advertisement.getId());
        advertisementResponseDto.setAdvertisementNo(advertisement.getAdvertisementNo());
        advertisementResponseDto.setStartDateTime(advertisement.getStartDateTime());
        advertisementResponseDto.setFinishDateTime(advertisement.getFinishDateTime());
        return advertisementResponseDto;
    }

    public static NotificationAdvertisementDto toNotificationAdvertisementDtoByNotificationTypeAndAdvertisement(NotificationType notificationType, Advertisement advertisement){
        NotificationAdvertisementDto notificationAdvertisementDto = new NotificationAdvertisementDto();
        notificationAdvertisementDto.setNotificationType(notificationType);
        notificationAdvertisementDto.setAdvertisementResponseDto(toAdvertisementResponseDtoByAdvertisement(advertisement));
        return notificationAdvertisementDto;
    }

    public static List<AdvertisementResponseDto> toResponse(List<Advertisement> advertisementList) {
        return advertisementList
                .stream()
                .map(AdvertisementConverter::toAdvertisementResponseDtoByAdvertisement)
                .collect(Collectors.toList());
    }
}
