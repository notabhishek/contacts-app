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
    private ContactService ContactService;

    @PostMapping("/add")
    public String add(@RequestBody Contact contact) {
        ContactService.saveContact(contact);
        return "New Contact is added";
    }

    @GetMapping("/getAll")
    public List<Contact> getAllContacts() {
        return ContactService.getAllContacts();
    }

//    @GetMapping("/startswith")
//    public List<Contact> getContactsStartsWith(@Param("name") String name) {
//        System.out.println("namePrfix :" + name);
//        return ContactService.startsWithName(name);
//    }
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
