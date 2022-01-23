package com.flock.springbootbackend.service;

import com.flock.springbootbackend.requestObjects.PasswordResetReq;
import com.flock.springbootbackend.model.PasswordResetToken;
import com.flock.springbootbackend.repository.PasswordTokenRepo;
import com.flock.springbootbackend.utils.Constants;
import com.flock.springbootbackend.model.User;
import com.flock.springbootbackend.repository.UserRepo;
import com.flock.springbootbackend.requestObjects.UserReq;
import com.flock.springbootbackend.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordTokenRepo passwordTokenRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        return userRepo.save(user);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findByEmail(principal.toString()).get();
    }

    public UserReq getCurrentUserReq() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<User> optionalUser = userRepo.findByEmail(principal.toString());
            User user = optionalUser.get();
            UserReq userReq = new UserReq(user.getName(), user.getEmail(), user.getPhone(), user.getAddress());
            return userReq;
        } catch (Exception e) {
            return new UserReq();
        }
    }

    public UserReq updateUser(UserReq user) {
        User curUser = getCurrentUser();

        curUser.setName(user.getName());
        curUser.setPhone(user.getPhone());
        curUser.setAddress(user.getAddress());
        userRepo.save(curUser);

        user.setEmail(curUser.getEmail());
        return user;
    }

    public String incrementMaxCid(int uid) {
        userRepo.incrementMaxCid(uid);
        return Constants.INCREMENTED_MAX_CID;
    }

    public String updateMaxCid(int uid, int cid) {
        userRepo.updateMaxCid(uid, cid);
        return Constants.UserConstants.MAX_CID_UPDATED;
    }

    public void createPasswordResetTokenForUser(User user, String token) {

        PasswordResetToken myToken = new PasswordResetToken(token, user.getUid());
        passwordTokenRepo.save(myToken);
    }

    public String genResetToken(String userEmail, User user) {
        try {
            String token = UUID.randomUUID().toString();
            createPasswordResetTokenForUser(user, token);
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

            MailUtil mailUtil = new MailUtil();
            System.out.println(token);

            String from = "beastonthelease@gmail.com";
            String to = "abhishektiw@flock.com";

            // Connection Refused Error while sending mail
//        mailSender.send(mailUtil.constructResetTokenEmail(token, user));
            return token;
        } catch (Exception e) {
            return "";
        }
    }

    public Map<String, Object> resetPassword(PasswordResetReq passwordResetReq) {
        try {
            User user = findByEmail(passwordResetReq.getEmail());
            if(user == null)
                return Collections.singletonMap("Error", "No user with this email exists");
            if (!passwordTokenRepo.existsById(user.getUid()))
                return Collections.singletonMap("Error", "No token for this user!");

            PasswordResetToken token = passwordTokenRepo.getById(user.getUid());

            if (false && token.hasExpired()) { // Remove bug here, always expired
                try {
                    passwordTokenRepo.deleteById(user.getUid());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return Collections.singletonMap("Error", "Token Expired, generate a new one");
            }

            if (token.getToken().equals(passwordResetReq.getResetToken())) {
                String encodedPass = passwordEncoder.encode(passwordResetReq.getPassword());
                user.setPassword(encodedPass);
                user = save(user);
                passwordTokenRepo.deleteById(user.getUid());
                return Collections.singletonMap("Success", "Password reset, relogin!");
            }
        } catch (Exception e) {
            return Collections.singletonMap("Error", "Invalid token!" + e.getMessage());
        }
        return Collections.singletonMap("Error", "Invalid token!!");
    }


}
