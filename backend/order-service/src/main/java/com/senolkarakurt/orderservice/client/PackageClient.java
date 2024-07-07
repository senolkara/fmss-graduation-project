package com.senolkarakurt.orderservice.client;

import com.senolkarakurt.orderservice.model.CPackage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "package-service", url = "http://localhost:8095/api/v1/packages")
public interface PackageClient {

    @GetMapping("/id/{id}")
    CPackage getPackageById(@PathVariable("id") Long id);

}
