package com.hvt.ultimatespy.utils.enums;

public enum StatusEnum {
    CREATED("CREATED"),
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
