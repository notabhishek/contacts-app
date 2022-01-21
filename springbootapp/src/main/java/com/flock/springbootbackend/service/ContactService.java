package com.flock.springbootbackend.service;

import com.flock.springbootbackend.Utils;
import com.flock.springbootbackend.dto.ContactBulkReq;
import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.model.SearchContactsReq;
import com.flock.springbootbackend.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserService userService;

    public Contact saveContact(Contact contact) {
        int uid = userService.getCurrentUser().getUid();
        contact.setUid(uid);
        return contactRepository.save(contact);
    }

    public String saveContacts(ContactBulkReq contactBulkReq){
        contactRepository.saveAll(contactBulkReq.getContactList());
        return Utils.ContactMsgConstants.ALL_CONTACTS_SAVED;
    }

    public String updateContact(Contact c) {
        int uid = userService.getCurrentUser().getUid();
        contactRepository.updateContact(uid, c.getCid(), c.getName(), c.getEmail(), c.getPhone(), c.getAddress());
        return Utils.ContactMsgConstants.CONTACT_UPDATED;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public List<Contact> searchPrefix(SearchContactsReq scr) {
        int uid = userService.getCurrentUser().getUid();
        if(scr.getOrderby().equals(Utils.CommonConstants.SCORE)) {
            if(scr.getDesc()) {
                return contactRepository.searchPrefixOrderByScoreDESC(uid, scr.getPrefix());
            } else {
                return contactRepository.searchPrefixOrderByScoreASC(uid, scr.getPrefix());
            }
        } else {
            if (scr.getDesc()) {
                return contactRepository.searchPrefixOrderByNameDESC(uid, scr.getPrefix());
            } else {
                return contactRepository.searchPrefixOrderByNameASC(uid, scr.getPrefix());
            }
        }
    }

    public String updateScore(int cid) {
        Boolean validCid = true;
        if(!validCid) return Utils.ContactMsgConstants.INVALID_CONTACT_ID;
        int uid = userService.getCurrentUser().getUid();
        contactRepository.updateScore(uid, cid);
        return Utils.ContactMsgConstants.SCORE_UPDATED;
    }

    public String deleteContact(int cid) {
        Boolean validCid = true;
        if(!validCid) {
            return Utils.ContactMsgConstants.INVALID_CONTACT_ID;
        }
        int uid = userService.getCurrentUser().getUid();
        contactRepository.deleteContact(uid, cid);
        return Utils.ContactMsgConstants.CONTACT_DELETED;
    }

    public String deleteContacts(ContactBulkReq contactBulkReq){
        int uid = userService.getCurrentUser().getUid();
        contactRepository.deleteContacts(uid, contactBulkReq.getContactCid());
        return Utils.ContactMsgConstants.CONTACTS_DELETED;
    }
}
