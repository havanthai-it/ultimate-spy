package com.hvt.ultimatespy.utils.enums;

public enum RoleEnum {
    ADMIN("ADMIN"),
    NORMAL(""),
    BASIC("BASIC"),
    PREMIUM("PREMIUM");

    private String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
