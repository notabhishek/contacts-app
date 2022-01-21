package com.flock.springbootbackend.service;

import com.flock.springbootbackend.dto.ContactBulkReq;
import com.flock.springbootbackend.model.Contact;

import java.util.List;

public interface ContactService {
    public Contact saveContact(Contact contact);
    public String updateContact(Contact contact);

    public List<Contact> getAllContacts();
    public List<Contact> searchPrefix(String prefix, String orderby, Boolean desc);

    public String updateScore(int cid);
    public String deleteContact(int cid);

    public String saveContacts(ContactBulkReq contactBulkReq);
    public String deleteContacts(ContactBulkReq contactBulkReq);
}
