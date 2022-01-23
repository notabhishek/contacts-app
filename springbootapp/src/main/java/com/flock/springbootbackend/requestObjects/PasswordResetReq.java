package com.flock.springbootbackend.requestObjects;

public class PasswordResetReq {
    private String email;
    private String resetToken;
    private String password;

    public PasswordResetReq() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PasswordResetReq{" +
                "email='" + email + '\'' +
                ", resetToken='" + resetToken + '\'' +
                ", newPassword='" + password + '\'' +
                '}';
    }
}
