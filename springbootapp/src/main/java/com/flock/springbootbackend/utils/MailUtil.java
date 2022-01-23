package com.flock.springbootbackend.utils;

import com.flock.springbootbackend.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;

import java.util.Locale;

public class MailUtil {

    public SimpleMailMessage constructResetTokenEmail(String token, User user) {
        String url = "Password Reset Token: " + token;
        String message = "Use below token to reset your password";
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(Constants.SUPPORT_EMAIL);
        return email;
    }
}
