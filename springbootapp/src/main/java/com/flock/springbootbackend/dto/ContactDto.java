package com.flock.springbootbackend.dto;

import com.flock.springbootbackend.model.Contact;

import java.util.List;

public class ContactDto {
    private List<Contact> contactList;

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }
}
