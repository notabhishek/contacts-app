package com.flock.springbootbackend.service;

import com.flock.springbootbackend.Utils;
import com.flock.springbootbackend.model.User;
import com.flock.springbootbackend.repository.UserRepo;
import com.flock.springbootbackend.requestObjects.UserReq;
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

    public UserReq getCurrentUserReq() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByEmail(principal.toString()).get();
        UserReq userReq = new UserReq(user.getName(), user.getEmail(), user.getPhone(), user.getAddress());
        return userReq;
    }

    public String updateUser(UserReq user) {
        User curUser = getCurrentUser();

        curUser.setName(user.getName());
        curUser.setPhone(user.getPhone());
        curUser.setAddress(user.getAddress());

        userRepo.save(curUser);
        return Utils.UserConstants.USER_DATA_UPDATED;
    }

    public String incrementMaxCid(int uid) {
        userRepo.incrementMaxCid(uid);
        return Utils.INCREMENTED_MAX_CID;
    }
}
