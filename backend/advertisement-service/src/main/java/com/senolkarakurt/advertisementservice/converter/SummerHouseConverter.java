package com.senolkarakurt.advertisementservice.converter;

import com.senolkarakurt.advertisementservice.model.SummerHouse;
import com.senolkarakurt.dto.response.SummerHouseResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SummerHouseConverter {

    public static SummerHouseResponseDto toSummerHouseResponseDtoBySummerHouse(SummerHouse summerHouse){
        SummerHouseResponseDto summerHouseResponseDto = new SummerHouseResponseDto();
        summerHouseResponseDto.setThereAFireplace(summerHouse.isThereAFireplace());
        return summerHouseResponseDto;
    }
}
