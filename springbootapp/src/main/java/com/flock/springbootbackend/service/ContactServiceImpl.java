package com.flock.springbootbackend.service;

import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
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
