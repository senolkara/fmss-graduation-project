package com.senolkarakurt.dto.request;

import com.senolkarakurt.enums.AdvertisementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementRequestDto {
    private Long id;
    private AdvertisementType advertisementType;
    private BigDecimal price;
    private BuildingRequestDto buildingRequestDto;
    private CustomerPackageRequestDto customerPackageRequestDto;
}
