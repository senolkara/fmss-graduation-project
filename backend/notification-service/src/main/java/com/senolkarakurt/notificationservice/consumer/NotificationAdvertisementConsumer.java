package com.senolkarakurt.notificationservice.consumer;

import com.senolkarakurt.dto.request.AdvertisementNotificationRequestDto;
import com.senolkarakurt.enums.AdvertisementStatus;
import com.senolkarakurt.enums.StatusType;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.notificationservice.client.service.AdvertisementClientService;
import com.senolkarakurt.notificationservice.component.NotificationComponent;
import com.senolkarakurt.notificationservice.consumer.dto.NotificationAdvertisementDto;
import com.senolkarakurt.notificationservice.converter.AdvertisementNotificationConverter;
import com.senolkarakurt.notificationservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.notificationservice.service.AdvertisementNotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class NotificationAdvertisementConsumer {

    private final AdvertisementNotificationService advertisementNotificationService;
    private final NotificationComponent notificationComponent;
    private final ExceptionMessagesResource exceptionMessagesResource;
    private AdvertisementClientService advertisementClientService;

    @RabbitListener(bindings =
        @QueueBinding(
                value = @Queue(value = "${notification.advertisement.queue}", durable = "true"),
                exchange = @Exchange(value = "${notification.advertisement.exchange}", type = ExchangeTypes.FANOUT, durable = "true")
        )
    )
    public void sendNotificationAdvertisement(NotificationAdvertisementDto notificationAdvertisementDto) {
        StatusType statusType = StatusType.SUCCESS;
        try{
            String message = "notification advertisement : " + notificationAdvertisementDto.toString();
            notificationComponent.sendMessage(message, notificationAdvertisementDto.getNotificationType());
        } catch (CommonException commonException){
            statusType = StatusType.FAILED;
            log.error("%s : {}".formatted(exceptionMessagesResource.getNotSendNotification()));
        }
        saveAdvertisementNotificationMessage(notificationAdvertisementDto, statusType);
        advertisementClientService.changeAdvertisementStatus(
                notificationAdvertisementDto.getAdvertisementResponseDto().getId(),
                AdvertisementStatus.ACTIVE
        );
    }

    public void saveAdvertisementNotificationMessage(NotificationAdvertisementDto notificationAdvertisementDto, StatusType statusType){
        AdvertisementNotificationRequestDto advertisementNotificationRequestDto = AdvertisementNotificationConverter.toAdvertisementNotificationRequestDtoByNotificationAdvertisementDto(notificationAdvertisementDto);
        advertisementNotificationRequestDto.setStatusType(statusType);
        advertisementNotificationService.save(advertisementNotificationRequestDto);
    }
}
