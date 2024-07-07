package com.senolkarakurt.advertisementservice.client;

import com.senolkarakurt.advertisementservice.model.CustomerPackage;
import com.senolkarakurt.advertisementservice.model.CPackage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "package-service", url = "http://localhost:8095/api/v1/packages")
public interface PackageClient {

    @GetMapping("/id/{id}")
    CPackage getPackageById(@PathVariable("id") Long id);

    @GetMapping("/customerPackageId/{id}")
    CustomerPackage getCustomerPackageById(@PathVariable("id") Long id);

    @PutMapping("/changeCustomerPackageAdvertisementCount/{id}")
    void changeCustomerPackageAdvertisementCount(@PathVariable("id") Long id, Integer advertisementCount);

}
