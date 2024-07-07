package com.senolkarakurt.purchaseservice.converter;

import com.senolkarakurt.dto.response.PackageResponseDto;
import com.senolkarakurt.purchaseservice.model.CPackage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PackageConverter {

    public static PackageResponseDto toPackageResponseDtoByPackage(CPackage aCPackage){
        PackageResponseDto packageResponseDto = new PackageResponseDto();
        packageResponseDto.setId(aCPackage.getId());
        packageResponseDto.setPackageType(aCPackage.getPackageType());
        packageResponseDto.setPrice(aCPackage.getPrice());
        packageResponseDto.setDescription(aCPackage.getDescription());
        return packageResponseDto;
    }
}
