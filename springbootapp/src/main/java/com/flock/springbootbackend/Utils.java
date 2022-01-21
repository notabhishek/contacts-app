package com.flock.springbootbackend;

public interface Utils {

    interface UrlConstants {

        String AUTH_URL = "/auth/**";
        String CONTACTS_URL = "/contacts/**";
    }
    interface CommonConstants {

        String CONTACTSAPP = "contactsapp";
        String EMAIL = "email";
        String SCORE = "score";
    }

    interface AuthContants {

        String JWT_TOKEN = "jwt-token";
        String INVALID_LOGIN_CREDENTIALS = "Invalid Login Credentials";
        String USER_DETAILS = "User Details";
        String JWT_SECRET = "${jwt-secret}";
        String USER_NOT_FOUND_WITH_EMAIL = "User not found with email: ";
        String ROLE_USER = "ROLE_USER";
        String UNAUTHORIZED = "Unauthorized";
        String AUTHORIZATION = "Authorization";
        String BEARER_ = "Bearer ";
        String INVALID_JWT_TOKEN_IN_BEARER_HEADER = "Invalid JWT Token in Bearer Header";
        String INVALID_JWT_TOKEN = "Invalid JWT Token";
    }

    interface UserConstants {

        String USER = "USER";
        String USER_DATA_UPDATED = "User data updated!";
    }

    interface ContactMsgConstants {

        String NEW_CONTACT_IS_ADDED = "New Contact is added";
        String ALL_CONTACTS_SAVED = "All contacts saved";
        String CONTACT_UPDATED = "Contact updated!";
        String INVALID_CONTACT_ID = "invalid contact id";
        String SCORE_UPDATED = "Score updated!";
        String CONTACT_DELETED = "Contact deleted";
        String CONTACTS_DELETED = "Contacts deleted";
    }

    interface ContactQueryConstants {

        String DELETE_CONTACTS = "DELETE FROM Contact c WHERE c.uid = :uid AND c.cid in :ids";
        String SELECT_CONTACTS_NAME_ASC = "Select c FROM Contact c WHERE c.uid = :uid AND (c.name LIKE :prefix% or c.email LIKE :prefix%) ORDER BY c.name ASC";
        String SELECT_CONTACTS_NAME_DESC = "Select c FROM Contact c WHERE c.uid = :uid AND (c.name LIKE :prefix% or c.email LIKE :prefix%) ORDER BY c.name DESC";
        String SELECT_CONTACTS_SCORE_ASC = "Select c FROM Contact c WHERE c.uid = :uid AND (c.name LIKE :prefix% or c.email LIKE :prefix%) ORDER BY c.score ASC";
        String SELECT_CONTACTS_SCORE_DESC = "Select c FROM Contact c WHERE c.uid = :uid AND (c.name LIKE :prefix% or c.email LIKE :prefix%) ORDER BY c.score DESC";
        String UPDATE_SCORE = "UPDATE Contact c SET c.score = c.score + 1 WHERE c.uid = :uid AND c.cid = :cid";
        String UPDATE_CONTACT = "UPDATE Contact c SET c.name=:name, c.email=:email, c.phone=:phone, c.address=:address WHERE c.uid = :uid AND c.cid = :cid";
        String DELETE_CONTACT = "DELETE FROM Contact c WHERE c.uid = :uid AND c.cid = :cid";
    }
}