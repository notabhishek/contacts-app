package com.flock.springbootbackend.utils;

public interface Validation {
    public static boolean validName(String name) {
        if(name.trim().equals("")) return false;
        return true;
    }
    public static boolean validPhone(String phone) {
        return true;
    }
    public static boolean validEmail(String email) {
        return true;
    }
}
