package com.senolkarakurt.enums;

import lombok.Getter;

@Getter
public enum CommercialStatus {

    TRANSFERABLE(0),
    PURCHASEABLE(1),
    RENTABLE(2);

    private final Integer value;

    CommercialStatus(Integer value) {
        this.value = value;
    }

    public static CommercialStatus fromValue(Integer value) {

        for (CommercialStatus commercialStatus : CommercialStatus.values()) {
            if (commercialStatus.value.equals(value)) {
                return commercialStatus;
            }
        }
        return null;
    }
}
