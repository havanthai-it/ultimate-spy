package com.hvt.ultimatespy.utils.enums;

public enum PlanEnum {
    BASIC_PLAN("basic_plan"),
    PREMIUM_PLAN("premium_plan");

    private String value;

    PlanEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
