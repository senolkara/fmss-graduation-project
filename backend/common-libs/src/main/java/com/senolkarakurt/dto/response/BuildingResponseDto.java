package com.senolkarakurt.dto.response;

import com.senolkarakurt.enums.BuildingType;
import com.senolkarakurt.enums.CommercialStatus;
import com.senolkarakurt.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingResponseDto implements Serializable {
    private Long id;
    private CommercialStatus commercialStatus;
    private RecordStatus recordStatus;
    private BuildingType buildingType;
    private String name;
    private String description;
    private Integer squareMeters;
    private Integer roomCount;
    private Integer saloonCount;
    private Integer floorCount;
    private BigDecimal price;
    private BuildingAddressResponseDto buildingAddressResponseDto;
    private HouseResponseDto houseResponseDto;
}
