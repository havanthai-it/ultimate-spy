package com.hvt.ultimatespy.utils.enums;

public enum ActionEnum {
    SEARCH("search"),
    SAVE_POST("save_post"),
    TRACK_POST("track_post");

    private String value;

    ActionEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
