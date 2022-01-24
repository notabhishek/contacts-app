package com.flock.springbootbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidContact extends RuntimeException {
    public InvalidContact(String message) {
        super(message);
    }
}
