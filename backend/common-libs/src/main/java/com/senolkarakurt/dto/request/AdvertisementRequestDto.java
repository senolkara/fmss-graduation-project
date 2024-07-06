package com.senolkarakurt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementRequestDto {
    private Long id;
    private BuildingRequestDto buildingRequestDto;
    private CustomerPackageRequestDto customerPackageRequestDto;
}
