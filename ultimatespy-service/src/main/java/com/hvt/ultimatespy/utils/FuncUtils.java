package com.hvt.ultimatespy.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    public static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}
