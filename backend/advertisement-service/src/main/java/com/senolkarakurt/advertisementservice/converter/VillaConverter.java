package com.senolkarakurt.advertisementservice.converter;

import com.senolkarakurt.advertisementservice.model.Villa;
import com.senolkarakurt.dto.response.VillaResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillaConverter {

    public static VillaResponseDto toVillaResponseDtoByVilla(Villa villa){
        VillaResponseDto villaResponseDto = new VillaResponseDto();
        villaResponseDto.setThereAPool(villa.isThereAPool());
        return villaResponseDto;
    }
}
