package com.senolkarakurt.notificationservice.client.service;

import com.senolkarakurt.dto.request.CustomerPackageRequestDto;
import com.senolkarakurt.notificationservice.client.PackageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PackageClientService {

    private final PackageClient packageClient;

    public void save(CustomerPackageRequestDto customerPackageRequestDto) {
        packageClient.save(customerPackageRequestDto);
    }

}
