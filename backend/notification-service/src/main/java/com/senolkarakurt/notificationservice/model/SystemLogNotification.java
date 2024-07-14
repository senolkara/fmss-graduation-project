package com.senolkarakurt.notificationservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(value = "system_logs_notification")
public class SystemLogNotification {

    @Id
    private String id;
    private LocalDateTime recordDateTime;
    private String content;
}
