package com.flock.springbootbackend.requestObjects;

public class PassResetTokenReq {
    private String email;

    public PassResetTokenReq() {
    }

    public PassResetTokenReq(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PassResetTokenReq{" +
                "email='" + email + '\'' +
                '}';
    }
}
