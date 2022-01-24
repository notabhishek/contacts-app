package com.flock.springbootbackend.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
@IdClass(UidCidKey.class)
public class Contact {

    @Id
    private int uid;
    @Id
    @JsonView(View.ContactSummary.class)
    private int cid;

    @JsonView(View.ContactSummary.class)
    private String name;
    @JsonView(View.ContactSummary.class)
    private String email;
    @JsonView(View.ContactSummary.class)
    private String phone;
    @JsonView(View.ContactSummary.class)
    private boolean fav;

    private String address;
    private int score;

    public Contact() {
        this.fav = false;
    }

    public Contact(int uid, int cid, String name, String email, String phone, String address) {
        this.cid = cid;
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.score = 0;
        this.fav = false;
    }

    public Contact(int uid, int cid, String name, String email, String phone, boolean fav, String address, int score) {
        this.uid = uid;
        this.cid = cid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.fav = fav;
        this.address = address;
        this.score = score;
    }

    public Contact(int cid) {
        this.cid = cid;
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

    public boolean getFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "uid=" + uid +
                ", cid=" + cid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", fav=" + fav +
                ", address='" + address + '\'' +
                ", score=" + score +
                '}';
    }
}
