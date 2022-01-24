package com.flock.springbootbackend.utils;

import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.model.DeletedContact;

public class Util {
    public static DeletedContact contactToDeletedContact(Contact c) {
        return new DeletedContact(
                c.getUid(),
                c.getCid(),
                c.getName(),
                c.getEmail(),
                c.getPhone(),
                c.getFav(),
                c.getAddress(),
                c.getScore()
        );
    }

    public static Contact deletedContactToContact(DeletedContact d) {
        return new Contact(
                d.getUid(),
                d.getCid(),
                d.getName(),
                d.getEmail(),
                d.getPhone(),
                d.isFav(),
                d.getAddress(),
                d.getScore()
                );
    }
}
