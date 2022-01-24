package com.flock.springbootbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidUser extends RuntimeException {
    public InvalidUser(String message) {
        super(message);
    }
}
