package com.senolkarakurt.purchaseservice.client.service;

import com.senolkarakurt.exception.CommonException;
import com.senolkarakurt.purchaseservice.client.PackageClient;
import com.senolkarakurt.purchaseservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.purchaseservice.model.CPackage;
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
