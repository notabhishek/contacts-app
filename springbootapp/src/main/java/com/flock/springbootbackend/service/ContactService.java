package com.flock.springbootbackend.service;

import com.flock.springbootbackend.utils.Constants;
import com.flock.springbootbackend.model.User;
import com.flock.springbootbackend.requestObjects.ContactBulkReq;
import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.requestObjects.SearchContactsReq;
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
        User user = userService.getCurrentUser();
        contact.setUid(user.getUid());
        contact.setCid(user.getMaxcid() + 1);

        userService.incrementMaxCid(user.getUid());

        return contactRepository.save(contact);
    }
    public String saveContacts(List<Contact> contacts) {
        User user = userService.getCurrentUser();
        int uid = user.getUid(), maxcid = user.getMaxcid();

        for(Contact contact : contacts) {
            contact.setUid(uid);
            contact.setCid(++maxcid);
        }

        contactRepository.saveAll(contacts);
        userService.updateMaxCid(uid, maxcid);
        return Constants.ContactMsgConstants.ALL_CONTACTS_SAVED;
    }

//    public String saveContacts(ContactBulkReq contactBulkReq){
//
//        User user = userService.getCurrentUser();
//        int uid = user.getUid(), maxcid = user.getMaxcid();
//        for(Contact contact : contactBulkReq.getContactList()) {
//            contact.setUid(uid);
//            ++maxcid;
//            contact.setCid(maxcid);
//            userService.incrementMaxCid(uid);
//            contactRepository.save(contact);
//        }
//        return Constants.ContactMsgConstants.ALL_CONTACTS_SAVED;
//    }


    public String updateContact(Contact c) {
        int uid = userService.getCurrentUser().getUid();
        contactRepository.updateContact(uid, c.getCid(), c.getName(), c.getEmail(), c.getPhone(), c.getAddress());
        return Constants.ContactMsgConstants.CONTACT_UPDATED;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public List<Contact> searchPrefix(SearchContactsReq scr) {
        int uid = userService.getCurrentUser().getUid();
        if(scr.getOrderby().equals(Constants.CommonConstants.SCORE)) {
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
        if(!validCid) return Constants.ContactMsgConstants.INVALID_CONTACT_ID;
        int uid = userService.getCurrentUser().getUid();
        contactRepository.updateScore(uid, cid);
        return Constants.ContactMsgConstants.SCORE_UPDATED;
    }

    public String deleteContact(int cid) {
        Boolean validCid = true;
        if(!validCid) {
            return Constants.ContactMsgConstants.INVALID_CONTACT_ID;
        }
        int uid = userService.getCurrentUser().getUid();
        contactRepository.deleteContact(uid, cid);
        return Constants.ContactMsgConstants.CONTACT_DELETED;
    }

    public String deleteContacts(ContactBulkReq contactBulkReq) {
        int uid = userService.getCurrentUser().getUid();
        contactRepository.deleteContacts(uid, contactBulkReq.getContactCid());
        return Constants.ContactMsgConstants.CONTACTS_DELETED;
    }

    public String updateFav(int cid, boolean fav) {
        int uid = userService.getCurrentUser().getUid();
        contactRepository.updateFav(uid, cid, fav);
        return Constants.ContactMsgConstants.FAV_UPDATED;
    }

    public List<Contact> getFavourites() {
        int uid = userService.getCurrentUser().getUid();
        return contactRepository.getFavourites(uid);
    }
}
