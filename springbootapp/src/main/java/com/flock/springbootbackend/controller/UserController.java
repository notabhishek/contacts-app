package com.flock.springbootbackend.controller;

import com.flock.springbootbackend.Utils;
import com.flock.springbootbackend.model.LoginCredentials;
import com.flock.springbootbackend.model.User;
import com.flock.springbootbackend.repository.UserRepo;
import com.flock.springbootbackend.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

import static com.flock.springbootbackend.Utils.AuthContants.JWT_TOKEN;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {


    @Autowired private UserRepo userRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody User user){
        String encodedPass = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPass);

        user = userRepo.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return Collections.singletonMap(JWT_TOKEN, token);
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
            throw new RuntimeException(Utils.AuthContants.INVALID_LOGIN_CREDENTIALS);
        }
    }
}
