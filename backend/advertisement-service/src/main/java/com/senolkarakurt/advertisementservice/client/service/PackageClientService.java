package com.senolkarakurt.advertisementservice.client.service;

import com.senolkarakurt.advertisementservice.client.PackageClient;
import com.senolkarakurt.advertisementservice.exception.ExceptionMessagesResource;
import com.senolkarakurt.advertisementservice.model.CustomerPackage;
import com.senolkarakurt.advertisementservice.model.Package;
import com.senolkarakurt.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PackageClientService {

    private final ExceptionMessagesResource exceptionMessagesResource;
    private final PackageClient packageClient;

    public Package getPackageById(Long id) {
        Package aPackage = packageClient.getPackageById(id);
        if (aPackage == null) {
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPackageNotFound(), id));
            throw new CommonException(exceptionMessagesResource.getPackageNotFound());
        }
        return aPackage;
    }

    public CustomerPackage getCustomerPackageById(Long id) {
        CustomerPackage customerPackage = packageClient.getCustomerPackageById(id);
        if (customerPackage == null) {
            log.error("%s : {} %s".formatted(exceptionMessagesResource.getPackageNotFound(), id));
            throw new CommonException(exceptionMessagesResource.getPackageNotFound());
        }
        return customerPackage;
    }

    public void changeCustomerPackageAdvertisementCount(Long id, Integer advertisementCount){
        packageClient.changeCustomerPackageAdvertisementCount(id, advertisementCount);
    }
}
