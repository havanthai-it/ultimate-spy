package com.hvt.ultimatespy;

public class Test {
    public static void main(String[] args) {
        String s = "0abc324dfdf".charAt(0) + "0abc324dfdf".replaceAll("(.)", "*");
        System.out.println(s);
    }
}
