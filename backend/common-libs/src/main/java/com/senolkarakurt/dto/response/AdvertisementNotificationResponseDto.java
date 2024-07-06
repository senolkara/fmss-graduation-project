package com.senolkarakurt.dto.response;

import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementNotificationResponseDto {
    private String id;
    private NotificationType notificationType;
    private StatusType statusType;
    private Long advertisementId;
    private String advertisementNo;
    private String name;
    private String surname;
    private String email;
}
