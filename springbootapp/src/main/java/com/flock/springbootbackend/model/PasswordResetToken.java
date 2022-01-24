package com.flock.springbootbackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class PasswordResetToken {
    public static final int EXPIRATION = 60 * 60 * 1000;

    @Id
    private int uid;
    private String token;
    private Date expirydate;

    public PasswordResetToken(String token, int uid) {
        this.token = token;
        this.uid = uid;
        this.expirydate = new Date(System.currentTimeMillis() + EXPIRATION);
    }

    public PasswordResetToken() {
        this.expirydate = new Date(System.currentTimeMillis() + EXPIRATION);
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }

    public boolean hasExpired() {
        return this.expirydate.before(new Date(System.currentTimeMillis()));
    }
}
