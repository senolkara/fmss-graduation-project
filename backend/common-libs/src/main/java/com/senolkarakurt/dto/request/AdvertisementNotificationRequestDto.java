package com.senolkarakurt.dto.request;

import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.enums.StatusType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertisementNotificationRequestDto {
    private NotificationType notificationType;
    private StatusType statusType;
    private Long advertisementId;
    private String advertisementNo;
    private String name;
    private String surname;
    private String email;
}
