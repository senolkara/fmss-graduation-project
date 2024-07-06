package com.senolkarakurt.notificationservice.client;

import com.senolkarakurt.enums.AdvertisementStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "advertisement-service", url = "http://localhost:8096/api/v1/advertisements")
public interface AdvertisementClient {

    @PutMapping("/changeAdvertisementStatus/{id}")
    void changeAdvertisementStatus(@PathVariable("id") Long id, AdvertisementStatus advertisementStatus);

}
