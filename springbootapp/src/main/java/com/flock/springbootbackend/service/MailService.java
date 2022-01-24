package com.flock.springbootbackend.service;

import com.flock.springbootbackend.exception.MailError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    static final String from = "contactsapp.help@gmail.com";

    @Autowired
    JavaMailSender mailSender;

    SimpleMailMessage createResetTokenMail(String from, String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Password reset token for ContactsApp");
        String body = "Hi, \nUse below token to reset your password. \nToken: " + token;
        message.setText(body);
        return message;
    }

    public void sendResetEmail(String to, String token) {
        SimpleMailMessage message = createResetTokenMail(from, to, token);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new MailError("Couldn't send reset token to " + to);
        }
    }
}
