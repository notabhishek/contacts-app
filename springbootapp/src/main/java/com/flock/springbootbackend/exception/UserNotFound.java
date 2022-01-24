package com.flock.springbootbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserNotFound extends InvalidUser {

    public UserNotFound(String email) {
        super("No user found with email: " + email);
    }
}
