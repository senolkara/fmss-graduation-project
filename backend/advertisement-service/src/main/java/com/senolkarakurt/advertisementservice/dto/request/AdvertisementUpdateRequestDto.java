package com.senolkarakurt.advertisementservice.dto.request;

import com.senolkarakurt.enums.AdvertisementStatus;
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
public class AdvertisementUpdateRequestDto {
    private AdvertisementStatus advertisementStatus;
    private AdvertisementType advertisementType;
    private BigDecimal price;
}
