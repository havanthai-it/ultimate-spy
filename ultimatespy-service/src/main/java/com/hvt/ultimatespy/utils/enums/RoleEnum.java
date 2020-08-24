package com.hvt.ultimatespy.utils.enums;

public enum RoleEnum {
    ADMIN("ADMIN"),
    FREE("FREE"),
    BASIC("BASIC"),
    STANDARD("STANDARD"),
    PREMIUM("PREMIUM");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
