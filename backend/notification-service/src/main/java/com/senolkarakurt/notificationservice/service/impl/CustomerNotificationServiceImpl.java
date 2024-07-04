package com.senolkarakurt.notificationservice.service.impl;

import com.senolkarakurt.dto.request.CustomerNotificationRequestDto;
import com.senolkarakurt.dto.response.CustomerNotificationResponseDto;
import com.senolkarakurt.enums.StatusType;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.notificationservice.component.NotificationComponent;
import com.senolkarakurt.notificationservice.consumer.dto.NotificationCustomerDto;
import com.senolkarakurt.notificationservice.converter.CustomerNotificationConverter;
import com.senolkarakurt.notificationservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.notificationservice.model.CustomerNotification;
import com.senolkarakurt.notificationservice.repository.CustomerNotificationRepository;
import com.senolkarakurt.notificationservice.service.CustomerNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerNotificationServiceImpl implements CustomerNotificationService {

    private final CustomerNotificationRepository customerNotificationRepository;
    private final NotificationComponent notificationComponent;
    private final ExceptionMessagesResource exceptionMessagesResource;

    @Override
    public void save(CustomerNotificationRequestDto customerNotificationRequestDto) {
        CustomerNotification customerNotification = CustomerNotificationConverter.toCustomerNotificationByCustomerNotificationRequestDto(customerNotificationRequestDto);
        customerNotificationRepository.save(customerNotification);
    }

    @Override
    public List<CustomerNotificationResponseDto> getAll() {
        return CustomerNotificationConverter.toResponse(customerNotificationRepository.findAll());
    }

    @Override
    public CustomerNotificationResponseDto getById(String id) {
        Optional<CustomerNotification> customerNotificationOptional = customerNotificationRepository.findById(id);
        if (customerNotificationOptional.isEmpty()){
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getNotFoundNotification(), id));
            throw new CommonException(exceptionMessagesResource.getNotFoundNotification());
        }
        return CustomerNotificationConverter.toCustomerNotificationResponseDtoByCustomerNotification(customerNotificationOptional.get());
    }

    @Override
    public void resendFailed() {
        List<CustomerNotification> failedCustomerNotificationList = customerNotificationRepository.findAll()
                .stream()
                .filter(customerNotification -> customerNotification.getStatusType().equals(StatusType.FAILED))
                .toList();
        failedCustomerNotificationList.forEach(customerNotification -> {
            NotificationCustomerDto notificationCustomerDto = CustomerNotificationConverter.toNotificationCustomerDtoByCustomerNotification(customerNotification);
            StatusType statusType = StatusType.SUCCESS;
            try{
                String message = "notification customer : " + notificationCustomerDto.toString();
                notificationComponent.sendMessage(message, notificationCustomerDto.getNotificationType());
            } catch (CommonException commonException){
                statusType = StatusType.FAILED;
                log.error("%s : {}".formatted(exceptionMessagesResource.getNotSendNotification()));
            }
            customerNotification.setStatusType(statusType);
            customerNotificationRepository.save(customerNotification);
        });
    }
}
