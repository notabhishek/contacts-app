package com.flock.springbootbackend.controller;

import com.flock.springbootbackend.utils.Constants;
import com.fasterxml.jackson.annotation.JsonView;
//import com.flock.springbootbackend.Utils;
import com.flock.springbootbackend.model.View;
import com.flock.springbootbackend.requestObjects.ContactBulkReq;
import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.requestObjects.SearchContactsReq;
import com.flock.springbootbackend.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
@CrossOrigin
public class ContactsController {
    @Autowired
    private ContactService contactService;

    @PostMapping("/add")
    @JsonView(View.ContactSummary.class)
    public Map<String, Object> add(@RequestBody Contact contact) {
        contact.setName(contact.getName().trim());
        System.out.println(contact.getName());
        if(contact.getName().equals(""))
            return Collections.singletonMap("Error", "Name cannot be empty");
        contact = contactService.saveContact(contact);
        System.out.println(contact);
        return Collections.singletonMap("Success", contact);
//        return Collections.singletonMap("Success", Constants.ContactMsgConstants.NEW_CONTACT_IS_ADDED);
    }

    @PostMapping("/addMultipleContact")
    public String addMultiple(@RequestBody ContactBulkReq contactDto){
        return contactService.saveContacts(contactDto.getContactList());
    }

    @PostMapping("/update")
    public String update(@RequestBody Contact contact) {
        return contactService.updateContact(contact);
    }

    @GetMapping("/getAll")
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/details")
    public Contact getContactDetails(@RequestParam(value = "cid" , required = true) int cid){
        System.out.println(contactService.getContactDetails(cid).getCid());
        return contactService.getContactDetails(cid);
    }

    @PostMapping("/search")
    @JsonView(View.ContactSummary.class)
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

