package com.senolkarakurt.enums;

import lombok.Getter;

@Getter
public enum PackageType {

    PREMIUM(1);

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
