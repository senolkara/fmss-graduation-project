package com.senolkarakurt.notificationservice.controller;

import com.senolkarakurt.dto.response.CustomerNotificationResponseDto;
import com.senolkarakurt.dto.response.GenericResponse;
import com.senolkarakurt.dto.response.InvoiceNotificationResponseDto;
import com.senolkarakurt.notificationservice.service.CustomerNotificationService;
import com.senolkarakurt.notificationservice.service.InvoiceNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final CustomerNotificationService customerNotificationService;
    private final InvoiceNotificationService invoiceNotificationService;

    @GetMapping("/allCustomerNotifications")
    public GenericResponse<List<CustomerNotificationResponseDto>> getAllCustomerNotifications() {
        return GenericResponse.success(customerNotificationService.getAll());
    }

    @GetMapping("/resendFailedCustomerNotifications")
    public void resendFailedCustomerNotifications(){
        customerNotificationService.resendFailed();
    }

    @GetMapping("/allInvoiceNotifications")
    public GenericResponse<List<InvoiceNotificationResponseDto>> getAllInvoiceNotifications() {
        return GenericResponse.success(invoiceNotificationService.getAll());
    }

    @GetMapping("/resendFailedInvoiceNotifications")
    public void resendFailedInvoiceNotifications(){
        invoiceNotificationService.resendFailed();
    }
}
