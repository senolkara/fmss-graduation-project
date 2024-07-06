package com.senolkarakurt.notificationservice.client;

import com.senolkarakurt.dto.request.CustomerPackageRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "package-service", url = "http://localhost:8095/api/v1/packages")
public interface PackageClient {

    @PostMapping("/save")
    void save(CustomerPackageRequestDto customerPackageRequestDto);

}
