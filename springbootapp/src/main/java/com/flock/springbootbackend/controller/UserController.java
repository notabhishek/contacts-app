package com.flock.springbootbackend.controller;

import com.flock.springbootbackend.requestObjects.UserReq;
import com.flock.springbootbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public UserReq getUser() {
        return userService.getCurrentUserReq();
    }

    @PostMapping("/update")
    public UserReq updateUser(@RequestBody UserReq user) {
        return userService.updateUser(user);
    }
}
