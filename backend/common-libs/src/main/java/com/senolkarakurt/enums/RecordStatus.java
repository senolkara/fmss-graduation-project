package com.senolkarakurt.enums;

import lombok.Getter;

@Getter
public enum RecordStatus {

    ACTIVE(1),
    DELETED(2);

    private final Integer value;

    RecordStatus(Integer value) {
        this.value = value;
    }

    public static RecordStatus fromValue(Integer value) {

        for (RecordStatus recordStatus : RecordStatus.values()) {
            if (recordStatus.value.equals(value)) {
                return recordStatus;
            }
        }
        return null;
    }
}
