package com.senolkarakurt.notificationservice.component;

import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.notificationservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.notificationservice.strategy.NotificationStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Component
@Slf4j
public class NotificationComponent {

    private final ExceptionMessagesResource exceptionMessagesResource;
    private final Map<NotificationType, NotificationStrategy> sendNotificationByType;

    public void sendMessage(String message, NotificationType notificationType) {
        NotificationStrategy notificationStrategy = sendNotificationByType.getOrDefault(notificationType, null);
        if (Objects.isNull(notificationStrategy)) {
            throw new CommonException(exceptionMessagesResource.getNotFoundNotificationType());
        }
        notificationStrategy.sendMessage(message);
    }
}
