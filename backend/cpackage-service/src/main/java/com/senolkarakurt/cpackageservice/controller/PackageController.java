package com.senolkarakurt.cpackageservice.controller;

import com.senolkarakurt.dto.request.CustomerPackageRequestDto;
import com.senolkarakurt.dto.response.CustomerPackageResponseDto;
import com.senolkarakurt.dto.response.GenericResponse;
import com.senolkarakurt.cpackageservice.model.CPackage;
import com.senolkarakurt.cpackageservice.model.CustomerPackage;
import com.senolkarakurt.cpackageservice.service.PackageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/packages")
@CrossOrigin(origins = "http://localhost:3000")
public class PackageController {

    private final PackageService packageService;

    @PostMapping({"","/","/save"})
    public void save(@RequestBody CustomerPackageRequestDto customerPackageRequestDto) {
        packageService.save(customerPackageRequestDto);
    }

    /**
     * Müşterinin tüm paketlerini listele
     */
    @GetMapping("/customerId/{customerId}")
    public GenericResponse<List<CustomerPackageResponseDto>> getAllByCustomerId(@PathVariable("customerId") Long customerId) {
        return GenericResponse.success(packageService.getAllByCustomerId(customerId));
    }

    @GetMapping("/id/{id}")
    public CPackage getPackageById(@PathVariable("id") Long id) {
        return packageService.getPackageById(id);
    }

    @GetMapping("/allPackages")
    public GenericResponse<List<CPackage>> getAllPackages() {
        return GenericResponse.success(packageService.getAllPackages());
    }

    @GetMapping("/customerPackageId/{id}")
    public CustomerPackage getCustomerPackageById(@PathVariable("id") Long id) {
        return packageService.getCustomerPackageById(id);
    }

    @PutMapping("/changeCustomerPackageAdvertisementCount/{id}")
    public void changeCustomerPackageAdvertisementCount(@PathVariable("id") Long id, @RequestBody Integer advertisementCount){
        packageService.changeCustomerPackageAdvertisementCount(id, advertisementCount);
    }
}
