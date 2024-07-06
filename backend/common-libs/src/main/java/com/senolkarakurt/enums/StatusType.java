package com.senolkarakurt.enums;

import lombok.Getter;

@Getter
public enum StatusType {

    SUCCESS(1),
    FAILED(2);

    private final Integer value;

    StatusType(Integer value) {
        this.value = value;
    }

    public static StatusType fromValue(Integer value) {

        for (StatusType statusType : StatusType.values()) {
            if (statusType.value.equals(value)) {
                return statusType;
            }
        }
        return null;
    }
}
