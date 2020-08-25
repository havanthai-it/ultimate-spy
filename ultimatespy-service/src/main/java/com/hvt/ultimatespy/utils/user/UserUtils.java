package com.hvt.ultimatespy.utils.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserUtils {
    private static final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String password) {
        return bCryptEncoder.encode(password);
    }

    public static boolean verifyPassword(String rawPassword, String encodedPassword)
    {
        return bCryptEncoder.matches(rawPassword, encodedPassword);
    }
}
