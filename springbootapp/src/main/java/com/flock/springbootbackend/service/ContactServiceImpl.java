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
    private UserRepo userRepo;

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findByEmail(principal.toString()).get();
    }

    @Override
    public Contact saveContact(Contact contact) {
        int uid = getCurrentUser().getUid();
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
        int uid = getCurrentUser().getUid();
        contactRepository.updateContact(uid, c.getCid(), c.getName(), c.getEmail(), c.getPhone(), c.getAddress());
        return "Contact updated!";
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public List<Contact> searchPrefix(String prefix, String orderby, Boolean desc) {
        int uid = getCurrentUser().getUid();
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
        // check if cid is valid
        if(!validCid) return "invalid contact id";
        int uid = getCurrentUser().getUid();
        contactRepository.updateScore(uid, cid);
        return "Score updated!";
    }

    @Override
    public String deleteContact(int cid) {
        Boolean validCid = true;
        // check if cid is valid
        if(!validCid) {
            return "Invalid Contact Id";
        }
        int uid = getCurrentUser().getUid();
        System.out.println(String.valueOf(cid) + " delete for user " + getCurrentUser());
        contactRepository.deleteContact(uid, cid);
        return "Contact deleted";
    }

    @Override
    public String deleteContacts(ContactBulkReq contactBulkReq){
        int uid = getCurrentUser().getUid();
        contactRepository.deleteContacts(uid, contactBulkReq.getContactCid());
        return "Contacts deleted";
    }

    //    @Override
//    public List<Student> startsWithName(String namePrefix) {
//        return studentRepository.startsWithName(namePrefix);
//    }
//
//    @Override
//    public List<Student> endsWithName(String nameSuffix) {
//        return studentRepository.endsWithName(nameSuffix);
//    }

//    @Override
//    public List<Student> containsName(String name) {
//        return studentRepository.containsName(name);
//    }
//
//    @Override
//    public String deleteStudentId(int id) {
//        studentRepository.deleteStudentId(id);
//        return "Student " + String.valueOf(id) + " deleted!";
//    }
//
//    @Override
//    public String updateStudent(int id, String name, String address) {
//        studentRepository.updateStudent(id, name, address);
//        return "Student " + String.valueOf(id) + " updated!";
//    }
}
