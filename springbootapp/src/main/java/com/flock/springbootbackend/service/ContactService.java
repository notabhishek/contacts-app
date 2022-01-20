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

    public String saveContactDto(ContactBulkReq contactDto);
//    public List<Contact> startsWithName(String namePrefix);
//    public List<Contact> endsWithName(String nameSuffix);
//    public List<Contact> containsName(String name);
//    public String deleteContactId(int cid);
//    public String updateContact(int uid, int cid, String name, String email, String phone, String address);
}
