package com.flock.springbootbackend.service;

import com.flock.springbootbackend.dto.ContactBulkReq;
import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.model.User;
import com.flock.springbootbackend.repository.ContactRepository;
import com.flock.springbootbackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserService userService;

    @Override
    public Contact saveContact(Contact contact) {
        int uid = userService.getCurrentUser().getUid();
        contact.setUid(uid);
        return contactRepository.save(contact);
    }

    @Override
    public String saveContacts(ContactBulkReq contactBulkReq){
        contactRepository.saveAll(contactBulkReq.getContactList());
        return "All contacts saved";
    }

    @Override
    public String updateContact(Contact c) {
        int uid = userService.getCurrentUser().getUid();
        contactRepository.updateContact(uid, c.getCid(), c.getName(), c.getEmail(), c.getPhone(), c.getAddress());
        return "Contact updated!";
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public List<Contact> searchPrefix(String prefix, String orderby, Boolean desc) {
        int uid = userService.getCurrentUser().getUid();
        if(orderby.equals("score")) {
            if(desc) {
                return contactRepository.searchPrefixOrderByScoreDESC(uid, prefix);
            } else {
                return contactRepository.searchPrefixOrderByScoreASC(uid, prefix);
            }
        } else {
            if (desc) {
                return contactRepository.searchPrefixOrderByNameDESC(uid, prefix);
            } else {
                return contactRepository.searchPrefixOrderByNameASC(uid, prefix);
            }
        }
    }

    @Override
    public String updateScore(int cid) {
        Boolean validCid = true;
        if(!validCid) return "invalid contact id";
        int uid = userService.getCurrentUser().getUid();
        contactRepository.updateScore(uid, cid);
        return "Score updated!";
    }

    @Override
    public String deleteContact(int cid) {
        Boolean validCid = true;
        if(!validCid) {
            return "Invalid Contact Id";
        }
        int uid = userService.getCurrentUser().getUid();
        System.out.println(String.valueOf(cid) + " delete for user " + userService.getCurrentUser());
        contactRepository.deleteContact(uid, cid);
        return "Contact deleted";
    }

    @Override
    public String deleteContacts(ContactBulkReq contactBulkReq){
        int uid = userService.getCurrentUser().getUid();
        contactRepository.deleteContacts(uid, contactBulkReq.getContactCid());
        return "Contacts deleted";
    }
}
