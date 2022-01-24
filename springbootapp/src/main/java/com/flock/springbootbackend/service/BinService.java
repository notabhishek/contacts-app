package com.flock.springbootbackend.service;

import com.flock.springbootbackend.exception.BinError;
import com.flock.springbootbackend.exception.InvalidContact;
import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.model.DeletedContact;
import com.flock.springbootbackend.repository.BinRepo;
import com.flock.springbootbackend.repository.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BinService {
    @Autowired
    BinRepo binRepo;

    @Autowired
    ContactRepo contactRepo;

    @Autowired
    private UserService userService;

    public DeletedContact saveDeletedContact(DeletedContact delContact) {
        return binRepo.save(delContact);
    }

    public DeletedContact restoreContact(int uid, int cid) {
        DeletedContact dc = null;
        try {
            dc = binRepo.getDeletedContact(uid, cid);
        } catch (Exception e) {
            throw new BinError("getDeletedContact(uid, cid) failed! " + e.getMessage());
        }
//        Contact contact = new Contact();
//        contact.setUid(dc.getUid());
//        contact.setCid(dc.getCid());
//        contact.setName(dc.getName());
//        contact.setEmail(dc.getEmail());
//        contact.setPhone(dc.getPhone());
//        contact.setFav(dc.isFav());
//        contact.setAddress(dc.getAddress());
//        contact.setScore(dc.getScore());
        Contact contact = dc.getAsContact();
        System.out.println(contact);
        System.out.println("here");
        System.out.println(contactRepo.findAll());
        contactRepo.save(contact);
        System.out.println("idhar chal gya");
        binRepo.deleteFromBin(uid, cid);
        return dc;
    }

    public List<DeletedContact> getAll() {
        return binRepo.getAll(userService.getCurrentUser().getUid());
    }

    public DeletedContact get(int uid, int cid) {
        return binRepo.get(uid, cid);
    }

    public void delete(int uid, int cid) {
        binRepo.deleteFromBin(uid, cid);
    }
}
