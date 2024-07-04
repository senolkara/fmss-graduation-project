package com.senolkarakurt.notificationservice.model;

import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.enums.StatusType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(value = "customer_notification")
public class CustomerNotification {

    @Id
    private String id;
    private NotificationType notificationType;
    private StatusType statusType;
    private String name;
    private String surname;
    private String email;

}
