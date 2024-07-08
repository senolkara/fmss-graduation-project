package com.senolkarakurt.dto.response;

import com.senolkarakurt.enums.RecordStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingResponseDto implements Serializable {
    private Long id;
    private RecordStatus recordStatus;
    private String name;
    private String description;
    private Integer squareMeters;
    private Integer roomCount;
    private Integer saloonCount;
    private Integer floorCount;
    private Integer howOldIsIt;
    private BuildingAddressResponseDto buildingAddressResponseDto;
    private HouseResponseDto houseResponseDto;
    private SummerHouseResponseDto summerHouseResponseDto;
    private VillaResponseDto villaResponseDto;
}
