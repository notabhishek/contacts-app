package com.flock.springbootbackend.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;

@Entity
@IdClass(UidCidKey.class)
public class DeletedContact {
    public static final long EXPIRATION = 30L * 24L * 60L * 60L * 1000L;

    @Id
    public int uid;
    @Id
    public int cid;

    private String name;

    private String email;

    private String phone;

    private boolean fav;

    private String address;
    private int score;
    private Date expirydate;

    public DeletedContact() {
        this.expirydate = new Date(System.currentTimeMillis() + EXPIRATION);
    }

    public DeletedContact(int uid, int cid, String name, String email, String phone, boolean fav, String address, int score) {
        this.uid = uid;
        this.cid = cid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.fav = fav;
        this.address = address;
        this.score = score;
        this.expirydate = new Date(System.currentTimeMillis() + EXPIRATION);
    }

    public DeletedContact(int uid, int cid, String name, String email, String phone, boolean fav, String address, int score, Date expirydate) {
        this.uid = uid;
        this.cid = cid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.fav = fav;
        this.address = address;
        this.score = score;
        this.expirydate = expirydate;
    }

    public Contact getAsContact() {
        return new Contact(
                uid,
                cid,
                name,
                email,
                phone,
                fav,
                address,
                score
        );
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }

    @Override
    public String toString() {
        return "DeletedContact{" +
                "uid=" + uid +
                ", cid=" + cid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", fav=" + fav +
                ", address='" + address + '\'' +
                ", score=" + score +
                ", expirydate=" + expirydate +
                '}';
    }
}
