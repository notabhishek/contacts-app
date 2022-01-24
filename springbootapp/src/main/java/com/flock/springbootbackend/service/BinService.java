package com.flock.springbootbackend.service;

import com.flock.springbootbackend.exception.BinError;
import com.flock.springbootbackend.exception.InvalidContact;
import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.model.DeletedContact;
import com.flock.springbootbackend.repository.BinRepo;
import com.flock.springbootbackend.repository.ContactRepo;
import com.flock.springbootbackend.utils.Util;
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
            throw new BinError("Restore Failed as getDeletedContact(uid, cid) failed! " + e.getMessage());
        }
        try {
            Contact contact = Util.deletedContactToContact(dc);
            contactRepo.save(contact);
            binRepo.deleteFromBin(uid, cid);
            return dc;
        } catch (Exception e) {
            throw new BinError("Restore failed! " + e.getMessage());
        }
    }

    public List<DeletedContact> getAll() {
        return binRepo.getAll(userService.getCurrentUser().getUid());
    }

    public DeletedContact get(int uid, int cid) {
        try {
            return binRepo.get(uid, cid);
        } catch (Exception e) {
            throw new BinError("Get Failed! " + e.getMessage());
        }
    }

    public void delete(int uid, int cid) {
        try {
            binRepo.deleteFromBin(uid, cid);
        }catch (Exception e) {
            throw new BinError("Delete failed! " + e.getMessage());
        }
    }

    public void saveAll(List<DeletedContact> deletedContacts) {
        binRepo.saveAll(deletedContacts);
    }
}
