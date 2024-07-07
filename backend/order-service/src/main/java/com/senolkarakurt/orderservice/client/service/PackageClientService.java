package com.senolkarakurt.orderservice.client.service;

import com.senolkarakurt.orderservice.client.PackageClient;
import com.senolkarakurt.orderservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.orderservice.model.CPackage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PackageClientService {

    private final ExceptionMessagesResource exceptionMessagesResource;
    private final PackageClient packageClient;

    public CPackage getPackageById(Long id) {
        CPackage aCPackage = packageClient.getPackageById(id);
        if (aCPackage == null) {
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPackageNotFound(), id));
            throw new CommonException(exceptionMessagesResource.getPackageNotFound());
        }
        return aCPackage;
    }

}
