package com.flock.springbootbackend.service;

import com.flock.springbootbackend.exception.InvalidContact;
import com.flock.springbootbackend.model.DeletedContact;
import com.flock.springbootbackend.utils.Constants;
import com.flock.springbootbackend.model.User;
import com.flock.springbootbackend.requestObjects.ContactBulkReq;
import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.requestObjects.SearchContactsReq;
import com.flock.springbootbackend.repository.ContactRepo;
import com.flock.springbootbackend.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.flock.springbootbackend.utils.Validation.*;

@Service
public class ContactService {

    @Autowired
    private ContactRepo contactRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BinService binService;

    public void validateContact(Contact contact) {
        if(!validName(contact.getName()))
            throw new InvalidContact("Invalid Contact Name");
        if(!validEmail(contact.getEmail()))
            throw new InvalidContact("Invalid Contact Email");
        if(!validPhone(contact.getPhone()))
            throw new InvalidContact("Invalid Contact Phone");
    }

    public Contact saveContact(Contact contact) {
        User user = userService.getCurrentUser();
        contact.setUid(user.getUid());
        contact.setCid(user.getMaxcid() + 1);

        validateContact(contact);

        userService.incrementMaxCid(user.getUid());
        return contactRepository.save(contact);
    }
    public String saveContacts(List<Contact> contacts) {
        User user = userService.getCurrentUser();
        int uid = user.getUid(), maxcid = user.getMaxcid();

        for(Contact contact : contacts) {
            validateContact(contact);
            contact.setUid(uid);
            contact.setCid(++maxcid);
        }

        contactRepository.saveAll(contacts);
        userService.updateMaxCid(uid, maxcid);
        return Constants.ContactMsgConstants.ALL_CONTACTS_SAVED;
    }

    public String updateContact(Contact c) {
        int uid = userService.getCurrentUser().getUid();
        validateContact(c);
        try {
            contactRepository.updateContact(uid, c.getCid(), c.getName(), c.getEmail(), c.getPhone(), c.getAddress());
            return Constants.ContactMsgConstants.CONTACT_UPDATED;
        } catch (Exception e) {
            throw new InvalidContact("Update contact failed!" + e.getMessage());
        }
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
        try {
            int uid = userService.getCurrentUser().getUid();
            contactRepository.updateScore(uid, cid);
            return Constants.ContactMsgConstants.SCORE_UPDATED;
        } catch (Exception e) {
            throw new InvalidContact("Update score failed! " + e.getMessage());
        }
    }

    public String deleteContact(int cid) {
        User user = userService.getCurrentUser();
        Contact c = null;
        try {
            c = contactRepository.getContactDetails(user.getUid(), cid);
        } catch (Exception e) {
            throw new InvalidContact("Invalid contact! " + e.getMessage());
        }
        try {
            DeletedContact deletedContact = new DeletedContact(c.getUid(), c.getCid(),
                    c.getName(), c.getEmail(), c.getPhone(), c.getFav(), c.getAddress(), c.getScore());

            System.out.println(deletedContact);
            binService.saveDeletedContact(deletedContact);
            contactRepository.deleteContact(user.getUid(), cid);

            return Constants.ContactMsgConstants.CONTACT_DELETED;
        } catch (Exception e) {
            throw new InvalidContact("Delete Failed! " + e.getMessage());
        }
    }

    public String deleteContacts(ContactBulkReq contactBulkReq) {
        try {
            int uid = userService.getCurrentUser().getUid();
            List<Contact> contacts = contactRepository.findAll(uid, contactBulkReq.getContactCid());
            List<DeletedContact> deletedContacts = new ArrayList<>();
            for(Contact contact : contacts)
                deletedContacts.add(Util.contactToDeletedContact(contact));
            binService.saveAll(deletedContacts);
            contactRepository.deleteContacts(uid, contactBulkReq.getContactCid());
            return Constants.ContactMsgConstants.CONTACTS_DELETED;
        } catch (Exception e) {
            throw new InvalidContact("Delete Contacts failed! " + e.getMessage());
        }
    }

    public String updateFav(int cid, boolean fav) {
        try {
            int uid = userService.getCurrentUser().getUid();
            contactRepository.updateFav(uid, cid, fav);
            return Constants.ContactMsgConstants.FAV_UPDATED;
        } catch (Exception e) {
            throw new InvalidContact("Update favourite failed! " + e.getMessage());
        }
    }

    public List<Contact> getFavourites() {
        int uid = userService.getCurrentUser().getUid();
        return contactRepository.getFavourites(uid);
    }

    public Contact getContactDetails(int cid) {
        try {
            int uid = userService.getCurrentUser().getUid();
            return contactRepository.getContactDetails(uid, cid);
        } catch (Exception e) {
            throw new InvalidContact("Could not get details for contact! " + e.getMessage());
        }
    }
}
