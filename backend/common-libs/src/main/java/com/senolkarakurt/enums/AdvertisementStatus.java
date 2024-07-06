package com.senolkarakurt.enums;

import lombok.Getter;

@Getter
public enum AdvertisementStatus {

    ACTIVE(1),
    PASSIVE(2),
    IN_REVIEW(3);

    private final Integer value;

    AdvertisementStatus(Integer value) {
        this.value = value;
    }

    public static AdvertisementStatus fromValue(Integer value) {

        for (AdvertisementStatus advertisementStatus : AdvertisementStatus.values()) {
            if (advertisementStatus.value.equals(value)) {
                return advertisementStatus;
            }
        }
        return null;
    }
}
