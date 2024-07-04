package com.senolkarakurt.notificationservice.service;

import com.senolkarakurt.dto.request.CustomerNotificationRequestDto;
import com.senolkarakurt.dto.response.CustomerNotificationResponseDto;
import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.enums.StatusType;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.notificationservice.model.CustomerNotification;
import com.senolkarakurt.notificationservice.repository.CustomerNotificationRepository;
import com.senolkarakurt.notificationservice.service.impl.*;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CustomerNotificationServiceTest {

    @InjectMocks
    private CustomerNotificationServiceImpl customerNotificationService;

    @Mock
    private CustomerNotificationRepository customerNotificationRepository;

    @Test
    public void testGetCustomerNotificationsList(){
        CustomerNotification customerNotification1 = prepareCustomerNotification1();
        CustomerNotification customerNotification2 = prepareCustomerNotification2();
        when(customerNotificationRepository.findAll()).thenReturn(Arrays.asList(customerNotification1, customerNotification2));
        List<CustomerNotificationResponseDto> customerNotificationResponseDtoList = customerNotificationService.getAll();
        assertEquals(customerNotificationResponseDtoList.size(), 2);
        assertEquals(customerNotificationResponseDtoList.getFirst().getNotificationType(), NotificationType.EMAIL);
    }

    @Test
    public void testGetById(){
        CustomerNotification customerNotification1 = prepareCustomerNotification1();
        when(customerNotificationRepository.findById("6679dcd311ee0f06020e1f7f")).thenReturn(Optional.of(customerNotification1));
        CustomerNotificationResponseDto customerNotificationResponseDto = customerNotificationService.getById("6679dcd311ee0f06020e1f7f");
        assertNotEquals(customerNotificationResponseDto, null);
        assertEquals(customerNotificationResponseDto.getNotificationType(), NotificationType.EMAIL);
    }

    @Test
    public void testGetInvalidById() {
        when(customerNotificationRepository.findById("6679dcd311ee0f06020e1f7f")).thenThrow(new CommonException("Bildirim bulunamad?"));
        Exception exception = assertThrows(CommonException.class, () -> {
            customerNotificationService.getById("6679dcd311ee0f06020e1f7f");
        });
        assertTrue(exception.getMessage().contains("Bildirim bulunamad?"));
    }

    @Test
    public void testCreateCustomerNotification(){
        when(customerNotificationRepository.save(Mockito.any(CustomerNotification.class))).thenReturn(Instancio.create(CustomerNotification.class));
        customerNotificationService.save(prepareCustomerNotificationRequestDto());
        verify(customerNotificationRepository, times(1)).save(Mockito.any(CustomerNotification.class));
    }

    private CustomerNotificationRequestDto prepareCustomerNotificationRequestDto() {
        return Instancio.of(CustomerNotificationRequestDto.class)
                .set(field("notificationType"), NotificationType.EMAIL)
                .set(field("name"), "senol")
                .set(field("surname"), "karakurt")
                .set(field("email"), "snlkrkrt@hotmail.com")
                .create();
    }

    private CustomerNotification prepareCustomerNotification1(){
        CustomerNotification customerNotification = new CustomerNotification();
        customerNotification.setId("66789fc3ec7e4c0449231231");
        customerNotification.setNotificationType(NotificationType.EMAIL);
        customerNotification.setStatusType(StatusType.SUCCESS);
        customerNotification.setName("senol");
        customerNotification.setSurname("karakurt");
        customerNotification.setEmail("snlkrkrt@hotmail.com");
        return customerNotification;
    }

    private CustomerNotification prepareCustomerNotification2(){
        CustomerNotification customerNotification = new CustomerNotification();
        customerNotification.setId("6678a317ec7e4c0449231232");
        customerNotification.setNotificationType(NotificationType.EMAIL);
        customerNotification.setStatusType(StatusType.SUCCESS);
        customerNotification.setName("senol");
        customerNotification.setSurname("karakurt");
        customerNotification.setEmail("skarakurt@hotmail.com");
        return customerNotification;
    }
}
