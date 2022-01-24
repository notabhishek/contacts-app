package com.flock.springbootbackend.exception;

public class MailError extends org.springframework.mail.MailException {
    public MailError(String msg) {
        super(msg);
    }
}
