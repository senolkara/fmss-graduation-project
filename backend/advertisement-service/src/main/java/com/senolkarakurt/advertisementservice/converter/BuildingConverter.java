package com.senolkarakurt.advertisementservice.converter;

import com.senolkarakurt.advertisementservice.model.Building;
import com.senolkarakurt.dto.response.BuildingResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BuildingConverter {

    public static BuildingResponseDto toBuildingResponseDtoByBuilding(Building building){
        BuildingResponseDto buildingResponseDto = new BuildingResponseDto();
        buildingResponseDto.setId(building.getId());
        buildingResponseDto.setName(building.getName());
        buildingResponseDto.setDescription(building.getDescription());
        buildingResponseDto.setRecordStatus(building.getRecordStatus());
        buildingResponseDto.setRoomCount(building.getRoomCount());
        buildingResponseDto.setFloorCount(building.getFloorCount());
        buildingResponseDto.setSaloonCount(building.getSaloonCount());
        buildingResponseDto.setSquareMeters(building.getSquareMeters());
        buildingResponseDto.setHowOldIsIt(building.getHowOldIsIt());
        return buildingResponseDto;
    }
}
