package com.flock.springbootbackend.service;

import com.flock.springbootbackend.exception.InvalidContact;
import com.flock.springbootbackend.exception.InvalidUser;
import com.flock.springbootbackend.exception.TokenError;
import com.flock.springbootbackend.exception.UserNotFound;
import com.flock.springbootbackend.model.Contact;
import com.flock.springbootbackend.requestObjects.PasswordResetReq;
import com.flock.springbootbackend.model.PasswordResetToken;
import com.flock.springbootbackend.repository.PasswordTokenRepo;
import com.flock.springbootbackend.utils.Constants;
import com.flock.springbootbackend.model.User;
import com.flock.springbootbackend.repository.UserRepo;
import com.flock.springbootbackend.requestObjects.UserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.flock.springbootbackend.utils.Validation.*;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordTokenRepo passwordTokenRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;

    public void validateUser(User user) {
        if(!validName(user.getName()))
            throw new InvalidUser("Invalid User Name");
        if(!validEmail(user.getEmail()))
            throw new InvalidUser("Invalid User Email");
        if(!validPhone(user.getPhone()))
            throw new InvalidUser("Invalid User Phone");
    }

    public User saveUser(User user) {
        validateUser(user);
        if(findByEmail(user.getEmail()) == null)
            return userRepo.save(user);
        else throw new InvalidUser("User with email: " + user.getEmail() + " already exists, use /login!");
    }

    public User updateUser(User user) {
        validateUser(user);
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

    public void genResetToken(String userEmail, User user) {
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        MailService mailUtil = new MailService();

        mailService.sendResetEmail(userEmail, token);
    }

    public Map<String, Object> resetPassword(PasswordResetReq passwordResetReq) {

        User user = findByEmail(passwordResetReq.getEmail());
        if(user == null)
            throw new UserNotFound(passwordResetReq.getEmail());
        if (!passwordTokenRepo.existsById(user.getUid()))
            throw new TokenError("No token for email: " + passwordResetReq.getEmail());

        PasswordResetToken token = passwordTokenRepo.getById(user.getUid());

        if (token.hasExpired()) {
            passwordTokenRepo.deleteById(user.getUid());
            throw new TokenError("Token expired, generate a new one!");
        }

        if (!token.getToken().equals(passwordResetReq.getResetToken()))
            throw new TokenError("Invalid Token!");

        String encodedPass = passwordEncoder.encode(passwordResetReq.getPassword());
        user.setPassword(encodedPass);
        user = updateUser(user);
        passwordTokenRepo.deleteById(user.getUid());
        return Collections.singletonMap("Success", "Password reset, relogin!");
    }
}
