package com.senolkarakurt.advertisementservice.converter;

import com.senolkarakurt.advertisementservice.model.House;
import com.senolkarakurt.dto.response.HouseResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HouseConverter {

    public static HouseResponseDto toHouseResponseDtoByHouse(House house){
        HouseResponseDto houseResponseDto = new HouseResponseDto();
        houseResponseDto.setWhichFloor(house.getWhichFloor());
        houseResponseDto.setDetached(houseResponseDto.isDetached());
        return houseResponseDto;
    }
}
