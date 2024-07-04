package com.senolkarakurt.notificationservice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:messages.properties")
@NoArgsConstructor
public class ExceptionMessagesResource {

    @Value("${NOTIFICATION_TYPE_NOT_FOUND}")
    public String notFoundNotificationType;

    @Value("${NOTIFICATION_NOT_FOUND}")
    public String notFoundNotification;

    @Value("${NOTIFICATION_NOT_CREATED}")
    public String notCreateNotification;

    @Value("${NOTIFICATION_NOT_SEND}")
    public String notSendNotification;

}
