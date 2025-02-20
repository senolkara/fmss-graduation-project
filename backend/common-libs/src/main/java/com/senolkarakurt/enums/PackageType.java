package com.senolkarakurt.enums;

import lombok.Getter;

@Getter
public enum PackageType {

    STANDARD(0),
    PREMIUM(1),
    GOLD(2),
    PLATINUM(3);

    private final Integer value;

    PackageType(Integer value) {
        this.value = value;
    }

    public static PackageType fromValue(Integer value) {

        for (PackageType packageType : PackageType.values()) {
            if (packageType.value.equals(value)) {
                return packageType;
            }
        }
        return null;
    }
}
