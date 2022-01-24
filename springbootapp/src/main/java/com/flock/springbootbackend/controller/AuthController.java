package com.flock.springbootbackend.controller;

import com.flock.springbootbackend.exception.InvalidUser;
import com.flock.springbootbackend.exception.UserNotFound;
import com.flock.springbootbackend.requestObjects.PassResetTokenReq;
import com.flock.springbootbackend.requestObjects.PasswordResetReq;
import com.flock.springbootbackend.requestObjects.LoginCredentials;
import com.flock.springbootbackend.model.User;
import com.flock.springbootbackend.security.JWTUtil;
import com.flock.springbootbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

import static com.flock.springbootbackend.utils.Constants.AuthContants.JWT_TOKEN;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired private UserService userService;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody User user) {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        user = userService.saveUser(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return Collections.singletonMap(JWT_TOKEN, token);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body){

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

        try {
            authManager.authenticate(authInputToken);
        } catch (AuthenticationException e) {
            throw new InvalidUser("Invalid Email or Password");
        }

        String token = jwtUtil.generateToken(body.getEmail());
        return Collections.singletonMap(JWT_TOKEN, token);
    }

    @PostMapping("/genResetToken")
    public Map<String, Object> genResetToken(@RequestBody PassResetTokenReq passResetTokenReq) {
        String userEmail = passResetTokenReq.getEmail();
        User user = userService.findByEmail(userEmail);
        if (user == null) {
            throw new UserNotFound(passResetTokenReq.getEmail());
        }

        userService.genResetToken(userEmail, user);
        return Collections.singletonMap("Success", "Password Reset token sent to " + user.getEmail());
    }

    @PostMapping("/resetPassword")
    public Map<String, Object> resetPassword(@RequestBody PasswordResetReq passwordResetReq) {
        return userService.resetPassword(passwordResetReq);
    }
}
