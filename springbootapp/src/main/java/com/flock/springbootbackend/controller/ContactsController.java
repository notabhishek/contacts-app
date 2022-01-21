package com.flock.springbootbackend.controller;

import com.flock.springbootbackend.dto.ContactBulkReq;
import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.model.User;
import com.flock.springbootbackend.service.ContactService;
import com.flock.springbootbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@CrossOrigin
public class ContactsController {
    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String add(@RequestBody Contact contact) {
        contactService.saveContact(contact);
        return "New Contact is added";
    }

    @PostMapping("/addMultipleContact")
    public String addMultiple(@RequestBody ContactBulkReq contactDto){
        contactService.saveContacts(contactDto);
        return "All contacts are added";
    }

    @PostMapping("/update")
    public String update(@RequestBody Contact contact) {
        return contactService.updateContact(contact);
    }

    @GetMapping("/getAll")
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/search")
    public List<Contact> searchPrefix(@Param("prefix") String prefix, @Param("orderby") String orderby, @Param("desc") Boolean desc) {
        System.out.println("prefix :" + prefix + ", orderby: " + orderby);
        return contactService.searchPrefix(prefix, orderby, desc);
    }

    @PostMapping("/updateScore")
    public String updateScore(@RequestBody Contact contact) {
        return contactService.updateScore(contact.getCid());
    }

    @PostMapping("/deleteContact")
    public String deleteContact(@RequestBody Contact contact) {
        return contactService.deleteContact(contact.getCid());
    }

    @PostMapping("/deleteContactList")
    public String deleteContactList(@RequestBody ContactBulkReq contactBulkReq ){
        return contactService.deleteContacts(contactBulkReq);
    }

    @GetMapping("/getUser")
    public User getUser() {
        return userService.getCurrentUser();
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
}
