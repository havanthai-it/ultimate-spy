package com.hvt.ultimatespy.utils;

public class FuncUtils {

    public static String randomString(int length, boolean hasUpper, boolean hasLower, boolean hasNumber) {
        StringBuilder pool = new StringBuilder();
        if (hasLower) pool.append("abcdefghijklmnopqrstuvxyz");
        if (hasUpper) pool.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        if (hasNumber) pool.append("0123456789");

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = (int)(pool.toString().length() * Math.random());
            sb.append(pool.toString().charAt(index));
        }

        return sb.toString();
    }

}
