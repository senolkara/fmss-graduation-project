package com.senolkarakurt.purchaseservice.client;

import com.senolkarakurt.purchaseservice.model.Package;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "package-service", url = "http://localhost:8095/api/v1/packages")
public interface PackageClient {

    @GetMapping("/id/{id}")
    Package getPackageById(@PathVariable("id") Long id);

}
