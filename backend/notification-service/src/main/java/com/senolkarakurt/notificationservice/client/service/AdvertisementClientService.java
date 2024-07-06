package com.senolkarakurt.notificationservice.client.service;

import com.senolkarakurt.enums.AdvertisementStatus;
import com.senolkarakurt.notificationservice.client.AdvertisementClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AdvertisementClientService {

    private final AdvertisementClient advertisementClient;

    public void changeAdvertisementStatus(Long id, AdvertisementStatus advertisementStatus){
        advertisementClient.changeAdvertisementStatus(id, advertisementStatus);
    }

}
