package com.hvt.ultimatespy.utils.enums;

public enum ActionEnum {
    CLICK("CLICK"),
    SIGNUP("SIGNUP");

    private String value;

    ActionEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
