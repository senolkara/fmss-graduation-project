package com.senolkarakurt.orderservice.client.service;

import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import com.senolkarakurt.orderservice.client.PackageClient;
import com.senolkarakurt.orderservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.orderservice.model.CPackage;
import com.senolkarakurt.orderservice.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PackageClientService {

    private final ExceptionMessagesResource exceptionMessagesResource;
    private final PackageClient packageClient;
    private final SystemLogService systemLogService;

    public PackageClientService(ExceptionMessagesResource exceptionMessagesResource,
                                PackageClient packageClient,
                                @Qualifier("textFileLogService") SystemLogService systemLogService) {
        this.exceptionMessagesResource = exceptionMessagesResource;
        this.packageClient = packageClient;
        this.systemLogService = systemLogService;
    }

    public CPackage getPackageById(Long id) {
        CPackage aCPackage = packageClient.getPackageById(id);
        if (aCPackage == null) {
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPackageNotFound(), id));
            SystemLogSaveRequestDto systemLogSaveRequestDto = SystemLogSaveRequestDto.builder()
                    .userId(null)
                    .recordDateTime(LocalDateTime.now())
                    .content("%s : {} %s".formatted(exceptionMessagesResource.getPackageNotFound(), id))
                    .build();
            systemLogService.save(systemLogSaveRequestDto);
            throw new CommonException(exceptionMessagesResource.getPackageNotFound());
        }
        return aCPackage;
    }

}
