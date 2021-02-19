package com.hvt.ultimatespy.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryptor {
    private static final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();

    public static String encrypt(String s) {
        return bCryptEncoder.encode(s);
    }

    public static boolean verify(String s, String encrypted)
    {
        return bCryptEncoder.matches(s, encrypted);
    }
}
