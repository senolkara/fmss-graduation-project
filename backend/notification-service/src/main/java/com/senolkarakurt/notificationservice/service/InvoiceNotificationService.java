package com.senolkarakurt.notificationservice.service;

import com.senolkarakurt.dto.request.InvoiceNotificationRequestDto;
import com.senolkarakurt.dto.response.InvoiceNotificationResponseDto;

import java.util.List;

public interface InvoiceNotificationService {
    void save(InvoiceNotificationRequestDto invoiceNotificationRequestDto);
    List<InvoiceNotificationResponseDto> getAll();
    InvoiceNotificationResponseDto getById(String id);
    void resendFailed();
}
