package com.senolkarakurt.enums;

import lombok.Getter;

@Getter
public enum AdvertisementType {

    TRANSFERABLE(0),
    PURCHASEABLE(1),
    RENTABLE(2);

    private final Integer value;

    AdvertisementType(Integer value) {
        this.value = value;
    }

    public static AdvertisementType fromValue(Integer value) {

        for (AdvertisementType advertisementType : AdvertisementType.values()) {
            if (advertisementType.value.equals(value)) {
                return advertisementType;
            }
        }
        return null;
    }
}
