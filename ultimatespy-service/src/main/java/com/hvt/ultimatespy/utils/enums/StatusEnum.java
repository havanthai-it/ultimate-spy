package com.hvt.ultimatespy.utils.enums;

public enum StatusEnum {
    CREATED("created"),
    ACTIVE("active"),
    INACTIVE("inactive"),
    SETTLED("settled"),
    UNSETTLED("unsettled");


    private String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
