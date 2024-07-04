package com.senolkarakurt.notificationservice.service;

import com.senolkarakurt.dto.request.InvoiceNotificationRequestDto;
import com.senolkarakurt.dto.response.InvoiceNotificationResponseDto;
import com.senolkarakurt.enums.NotificationType;
import com.senolkarakurt.enums.StatusType;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.notificationservice.model.InvoiceNotification;
import com.senolkarakurt.notificationservice.repository.InvoiceNotificationRepository;
import com.senolkarakurt.notificationservice.service.impl.*;
import com.senolkarakurt.util.GenerateRandomUnique;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class InvoiceNotificationServiceTest {

    @InjectMocks
    private InvoiceNotificationServiceImpl invoiceNotificationService;

    @Mock
    private InvoiceNotificationRepository invoiceNotificationRepository;

    @Test
    public void testGetInvoiceNotificationsList(){
        InvoiceNotification invoiceNotification1 = prepareInvoiceNotification1();
        InvoiceNotification invoiceNotification2 = prepareInvoiceNotification2();
        when(invoiceNotificationRepository.findAll()).thenReturn(Arrays.asList(invoiceNotification1, invoiceNotification2));
        List<InvoiceNotificationResponseDto> invoiceNotificationResponseDtoList = invoiceNotificationService.getAll();
        assertEquals(invoiceNotificationResponseDtoList.size(), 2);
        assertEquals(invoiceNotificationResponseDtoList.getFirst().getNotificationType(), NotificationType.EMAIL);
    }

    @Test
    public void testGetById(){
        InvoiceNotification invoiceNotification1 = prepareInvoiceNotification1();
        when(invoiceNotificationRepository.findById("667b357950f3f02115fdd8b6")).thenReturn(Optional.of(invoiceNotification1));
        InvoiceNotificationResponseDto invoiceNotificationResponseDto = invoiceNotificationService.getById("667b357950f3f02115fdd8b6");
        assertNotEquals(invoiceNotificationResponseDto, null);
        assertEquals(invoiceNotificationResponseDto.getNotificationType(), NotificationType.EMAIL);
    }

    @Test
    public void testGetInvalidById() {
        when(invoiceNotificationRepository.findById("667b357950f3f02115fdd8b6")).thenThrow(new CommonException("Bildirim bulunamad?"));
        Exception exception = assertThrows(CommonException.class, () -> {
            invoiceNotificationService.getById("667b357950f3f02115fdd8b6");
        });
        assertTrue(exception.getMessage().contains("Bildirim bulunamad?"));
    }

    @Test
    public void testCreateInvoiceNotification(){
        when(invoiceNotificationRepository.save(Mockito.any(InvoiceNotification.class))).thenReturn(Instancio.create(InvoiceNotification.class));
        invoiceNotificationService.save(prepareInvoiceNotificationRequestDto());
        verify(invoiceNotificationRepository, times(1)).save(Mockito.any(InvoiceNotification.class));
    }

    private InvoiceNotificationRequestDto prepareInvoiceNotificationRequestDto() {
        return Instancio.of(InvoiceNotificationRequestDto.class)
                .set(field("notificationType"), NotificationType.EMAIL)
                .set(field("orderCode"), GenerateRandomUnique.createRandomOrderCode())
                .set(field("totalPrice"), BigDecimal.TEN)
                .set(field("name"), "senol")
                .set(field("surname"), "karakurt")
                .set(field("email"), "snlkrkrt@hotmail.com")
                .create();
    }

    private InvoiceNotification prepareInvoiceNotification1() {
        InvoiceNotification invoiceNotification = new InvoiceNotification();
        invoiceNotification.setId("667b357950f3f02115fdd8b6");
        invoiceNotification.setNotificationType(NotificationType.EMAIL);
        invoiceNotification.setStatusType(StatusType.SUCCESS);
        invoiceNotification.setOrderCode(GenerateRandomUnique.createRandomOrderCode());
        invoiceNotification.setTotalPrice(BigDecimal.TEN);
        invoiceNotification.setName("senol");
        invoiceNotification.setSurname("karakurt");
        invoiceNotification.setEmail("snlkrkrt@hotmail.com");
        return invoiceNotification;
    }

    private InvoiceNotification prepareInvoiceNotification2() {
        InvoiceNotification invoiceNotification = new InvoiceNotification();
        invoiceNotification.setId("667b43ec50f3f02115fdd8b8");
        invoiceNotification.setNotificationType(NotificationType.EMAIL);
        invoiceNotification.setStatusType(StatusType.SUCCESS);
        invoiceNotification.setOrderCode(GenerateRandomUnique.createRandomOrderCode());
        invoiceNotification.setTotalPrice(BigDecimal.TEN);
        invoiceNotification.setName("senol");
        invoiceNotification.setSurname("karakurt");
        invoiceNotification.setEmail("skarakurt@hotmail.com");
        return invoiceNotification;
    }

}
