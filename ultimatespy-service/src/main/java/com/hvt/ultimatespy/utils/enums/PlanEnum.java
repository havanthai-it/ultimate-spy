package com.hvt.ultimatespy.utils.enums;

public enum PlanEnum {
    FREE("free"),
    BASIC("basic"),
    PREMIUM("premium");

    private String value;

    PlanEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
