package com.senolkarakurt.orderservice.converter;

import com.senolkarakurt.dto.response.PackageResponseDto;
import com.senolkarakurt.orderservice.model.Package;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PackageConverter {

    public static PackageResponseDto toPackageResponseDtoByPackage(Package aPackage){
        PackageResponseDto packageResponseDto = new PackageResponseDto();
        packageResponseDto.setId(aPackage.getId());
        packageResponseDto.setPackageType(aPackage.getPackageType());
        packageResponseDto.setPrice(aPackage.getPrice());
        packageResponseDto.setDescription(aPackage.getDescription());
        return packageResponseDto;
    }
}
