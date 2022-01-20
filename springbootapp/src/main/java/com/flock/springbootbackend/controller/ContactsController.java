package com.flock.springbootbackend.controller;

import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.service.ContactService;
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

    @PostMapping("/add")
    public String add(@RequestBody Contact contact) {
        contactService.saveContact(contact);
        return "New Contact is added";
    }

    @PostMapping("/update")
    public String update(@RequestBody Contact contact) {
        return contactService.updateContact(contact);
    }

    @GetMapping("/getAll")
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/search") // find records with matching prefix in name/email
    public List<Contact> searchPrefix(@Param("prefix") String prefix, @Param("orderby") String orderby, @Param("desc") Boolean desc) {
        System.out.println("prefix :" + prefix + ", orderby: " + orderby);
        // check for validity of order by here
        return contactService.searchPrefix(prefix, orderby, desc);
    }

    @PostMapping("/updateScore")
    public String updateScore(@RequestBody Contact contact) {
        return contactService.updateScore(contact.getCid());
    }

    @DeleteMapping("/deleteContact")
    public String deleteContact(@RequestBody Contact contact) {
        return contactService.deleteContact(contact.getCid());
    }
//
//    @GetMapping("/endswith")
//    public List<Contact> getContactsEndsWith(@Param("name") String name) {
//        return ContactService.endsWithName(name);
//    }
//
//    @GetMapping("/contains")
//    public List<Contact> getContactsContains(@Param("name") String name) {
//        return ContactService.containsName(name);
//    }

//    @DeleteMapping("/delete")
//    public String deleteContactId(@Param("id") int id) {
//        return ContactService.deleteContactId(id);
//    }

//    @PostMapping("/update")
//    public String updateContact(@Param("id") int id, @Param("name") String name, @Param("address") String address) {
//        return ContactService.updateContact(id, name, address);
//    }
}
