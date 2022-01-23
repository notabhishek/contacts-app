package com.flock.springbootbackend.controller;

import com.flock.springbootbackend.requestObjects.PassResetTokenReq;
import com.flock.springbootbackend.requestObjects.PasswordResetReq;
import com.flock.springbootbackend.requestObjects.LoginCredentials;
import com.flock.springbootbackend.model.User;
import com.flock.springbootbackend.security.JWTUtil;
import com.flock.springbootbackend.service.UserService;
import com.flock.springbootbackend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.flock.springbootbackend.utils.Constants.AuthContants.INVALID_LOGIN_CREDENTIALS;
import static com.flock.springbootbackend.utils.Constants.AuthContants.JWT_TOKEN;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {


//    @Autowired private UserRepo userRepo;
    @Autowired private UserService userService;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody User user) {
        try {

            String encodedPass = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPass);
            user = userService.save(user);
            String token = jwtUtil.generateToken(user.getEmail());
            return Collections.singletonMap(JWT_TOKEN, token);

        } catch (DataIntegrityViolationException e) {
            return Collections.singletonMap("Error", Constants.AuthContants.USER_ALREADY_EXISTS);
        }
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getEmail());

            return Collections.singletonMap(JWT_TOKEN, token);
        }catch (AuthenticationException authExc){
            return Collections.singletonMap("Error", INVALID_LOGIN_CREDENTIALS);
//            throw new RuntimeException(Utils.AuthContants.INVALID_LOGIN_CREDENTIALS);
        }
    }

    @PostMapping("/genResetToken")
    public Map<String, Object> genResetToken(@RequestBody PassResetTokenReq passResetTokenReq) {
        String userEmail = passResetTokenReq.getEmail();
        System.out.println("/n/n/n/n/n/n/n" + userEmail);
        User user = userService.findByEmail(userEmail);
        if (user == null) {
            return Collections.singletonMap("Error", "No user exists with email: " + userEmail);
        }
        String token =  userService.genResetToken(userEmail, user);
        if(token == "")
            return Collections.singletonMap("Error", "Could not generate password reset token");
        return Collections.singletonMap("Success", "Password Reset token sent to " + user.getEmail() + "\n token: " + token);
    }

    @PostMapping("/resetPassword")
    public Map<String, Object> resetPassword(@RequestBody PasswordResetReq passwordResetReq) {
        return userService.resetPassword(passwordResetReq);
    }
}
