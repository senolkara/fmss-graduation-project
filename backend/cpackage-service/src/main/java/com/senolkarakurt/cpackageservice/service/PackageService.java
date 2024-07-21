package com.senolkarakurt.cpackageservice.service;

import com.senolkarakurt.cpackageservice.model.CPackage;
import com.senolkarakurt.dto.request.CustomerPackageRequestDto;
import com.senolkarakurt.dto.response.CustomerPackageResponseDto;
import com.senolkarakurt.cpackageservice.model.CustomerPackage;

import java.util.List;

public interface PackageService {
    void save(CustomerPackageRequestDto customerPackageRequestDto);
    List<CustomerPackageResponseDto> getAllByCustomerId(Long customerId);
    CPackage getPackageById(Long id);
    CustomerPackage getCustomerPackageById(Long id);
    void changeCustomerPackageAdvertisementCount(Long id, Integer advertisementCount);
    List<CPackage> getAllPackages();
}
