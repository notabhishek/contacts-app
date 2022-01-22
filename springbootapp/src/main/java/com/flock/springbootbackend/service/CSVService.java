package com.flock.springbootbackend.service;

import com.flock.springbootbackend.utils.CSVUtil;
import com.flock.springbootbackend.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CSVService {
    @Autowired
    ContactService contactService;

    public void save(MultipartFile file) {
        try {
            List<Contact> contacts = CSVUtil.csvToContacts(file.getInputStream());
            contactService.saveContacts(contacts);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store CSV data: " + e.getMessage());
        }
    }


}
