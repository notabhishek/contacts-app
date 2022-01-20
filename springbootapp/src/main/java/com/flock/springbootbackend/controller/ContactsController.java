package com.flock.springbootbackend.controller;

import com.flock.springbootbackend.dto.ContactBulkReq;
import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.model.User;
import com.flock.springbootbackend.repository.UserRepo;
import com.flock.springbootbackend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@CrossOrigin
public class ContactsController {
    @Autowired
    private ContactService ContactService;


    @PostMapping("/add")
    public String add(@RequestBody Contact contact) {
        ContactService.saveContact(contact);
        return "New Contact is added";
    }

    @PostMapping("/addMultipleContact")
    public String addMultiple(@RequestBody ContactBulkReq contactDto){
        ContactService.saveContacts(contactDto);
        return "All contacts are added";
    }

    @PostMapping("/update")
    public String update(@RequestBody Contact contact) {
        return ContactService.updateContact(contact);
    }

    @GetMapping("/getAll")
    public List<Contact> getAllContacts() {
        return ContactService.getAllContacts();
    }

    @GetMapping("/search")
    public List<Contact> searchPrefix(@Param("prefix") String prefix, @Param("orderby") String orderby, @Param("desc") Boolean desc) {
        System.out.println("prefix :" + prefix + ", orderby: " + orderby);
        return ContactService.searchPrefix(prefix, orderby, desc);
    }

    @PostMapping("/updateScore")
    public String updateScore(@RequestBody Contact contact) {
        return ContactService.updateScore(contact.getCid());
    }

    @PostMapping("/deleteContact")
    public String deleteContact(@RequestBody Contact contact) {
        return ContactService.deleteContact(contact.getCid());
    }

    @PostMapping("/deleteContactList")
    public String deleteContactList(@RequestBody ContactBulkReq contactBulkReq ){
        return ContactService.deleteContacts(contactBulkReq);
    }
}
