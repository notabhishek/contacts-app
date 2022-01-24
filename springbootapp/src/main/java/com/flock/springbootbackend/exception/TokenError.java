package com.flock.springbootbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TokenError extends RuntimeException {
    public TokenError(String message) {
        super(message);
    }
}
