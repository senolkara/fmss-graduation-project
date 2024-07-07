package com.senolkarakurt.advertisementservice.converter;

import com.senolkarakurt.advertisementservice.model.CPackage;
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

    public static PackageResponseDto toPackageResponseDtoByPackage(CPackage aCPackage){
        PackageResponseDto packageResponseDto = new PackageResponseDto();
        packageResponseDto.setId(aCPackage.getId());
        packageResponseDto.setPackageType(aCPackage.getPackageType());
        packageResponseDto.setPrice(aCPackage.getPrice());
        packageResponseDto.setDescription(aCPackage.getDescription());
        return packageResponseDto;
    }
}
