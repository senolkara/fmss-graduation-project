package com.senolkarakurt.enums;

import lombok.Getter;

@Getter
public enum BuildingType {
    HOUSE(1),
    VILLA(2),
    SUMMERHOUSE(3);

    private final Integer value;

    BuildingType(Integer value) {
        this.value = value;
    }

    public static BuildingType fromValue(Integer value) {

        for (BuildingType buildingType : BuildingType.values()) {
            if (buildingType.value.equals(value)) {
                return buildingType;
            }
        }
        return null;
    }
}
