package com.senolkarakurt.advertisementservice.converter;

import com.senolkarakurt.advertisementservice.model.Package;
import com.senolkarakurt.dto.response.CustomerPackageResponseDto;
import com.senolkarakurt.dto.response.PackageResponseDto;
import com.senolkarakurt.advertisementservice.model.CustomerPackage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PackageConverter {

    public static CustomerPackageResponseDto toCustomerPackageResponseDtoByCustomerPackage(CustomerPackage customerPackage){
        CustomerPackageResponseDto customerPackageResponseDto = new CustomerPackageResponseDto();
        customerPackageResponseDto.setId(customerPackage.getId());
        customerPackageResponseDto.setRecordStatus(customerPackage.getRecordStatus());
        customerPackageResponseDto.setStartDateTime(customerPackage.getStartDateTime());
        customerPackageResponseDto.setFinishDateTime(customerPackage.getFinishDateTime());
        customerPackageResponseDto.setAdvertisementCount(customerPackage.getAdvertisementCount());
        return customerPackageResponseDto;
    }

    public static PackageResponseDto toPackageResponseDtoByPackage(Package aPackage){
        PackageResponseDto packageResponseDto = new PackageResponseDto();
        packageResponseDto.setId(aPackage.getId());
        packageResponseDto.setPackageType(aPackage.getPackageType());
        packageResponseDto.setPrice(aPackage.getPrice());
        packageResponseDto.setDescription(aPackage.getDescription());
        return packageResponseDto;
    }
}
