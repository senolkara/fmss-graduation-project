package com.senolkarakurt.advertisementservice.producer.dto;

import com.senolkarakurt.dto.response.AdvertisementResponseDto;
import com.senolkarakurt.enums.NotificationType;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationAdvertisementDto implements Serializable {
    private NotificationType notificationType;
    private AdvertisementResponseDto advertisementResponseDto;
}
