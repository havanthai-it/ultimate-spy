package com.hvt.ultimatespy.utils.enums;

public enum RoleEnum {
    ADMIN("admin"),
    NORMAL("");

    private String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
