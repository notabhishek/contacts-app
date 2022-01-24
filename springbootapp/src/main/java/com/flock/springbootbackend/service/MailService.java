package com.flock.springbootbackend.service;

import com.flock.springbootbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
        mailSender.send(message);
    }
    public void sendSimpleEmail(String to, String body, String sub) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(sub);
        message.setText(body);

        mailSender.send(message);
        System.out.println("Mail sent...");
    }
}
