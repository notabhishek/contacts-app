package com.flock.springbootbackend.requestObjects;

import com.flock.springbootbackend.model.Contact;

import java.util.List;

public class ContactBulkReq {
    private List<Contact> contactList;
    private List<Integer> contactCid;

    public ContactBulkReq(){}

    public List<Integer> getContactCid() {
        return contactCid;
    }

    public void setContactCid(List<Integer> contactCid) {
        this.contactCid = contactCid;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }
}
