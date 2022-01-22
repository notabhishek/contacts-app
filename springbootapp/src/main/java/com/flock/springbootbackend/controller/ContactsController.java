package com.flock.springbootbackend.controller;

import com.flock.springbootbackend.utils.Constants;
import com.flock.springbootbackend.requestObjects.ContactBulkReq;
import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.requestObjects.SearchContactsReq;
import com.flock.springbootbackend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return Constants.ContactMsgConstants.NEW_CONTACT_IS_ADDED;
    }

    @PostMapping("/addMultipleContact")
    public String addMultiple(@RequestBody ContactBulkReq contactDto){
        return contactService.saveContacts(contactDto);
    }

    @PostMapping("/update")
    public String update(@RequestBody Contact contact) {
        return contactService.updateContact(contact);
    }

    @GetMapping("/getAll")
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @PostMapping("/search")
    public List<Contact> search(@RequestBody SearchContactsReq searchContactsReq) {
        return contactService.searchPrefix(searchContactsReq);
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

    @PostMapping("/updateFav")
    public String updateFav(@RequestBody Contact contact) {
        return contactService.updateFav(contact.getCid(), contact.getFav());
    }

    @GetMapping("/favourites")
    public List<Contact> getFavourites() {
        return contactService.getFavourites();
    }
}

