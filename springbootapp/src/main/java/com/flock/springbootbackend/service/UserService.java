package com.flock.springbootbackend.service;

import com.flock.springbootbackend.model.User;
import com.flock.springbootbackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findByEmail(principal.toString()).get();
    }

    public String updateUser(User user) {
        user.setUid(getCurrentUser().getUid());
        userRepo.save(user);
        return "User data updated!";
    }
}
