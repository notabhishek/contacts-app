package com.flock.springbootbackend.controller;

import com.flock.springbootbackend.model.DeletedContact;
import com.flock.springbootbackend.service.BinService;
import com.flock.springbootbackend.service.ContactService;
import com.flock.springbootbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bin")
@CrossOrigin
public class BinController {
    @Autowired
    private BinService binService;
    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<DeletedContact> getAll() {
        return binService.getAll();
    }

    @GetMapping("/get")
    public DeletedContact get(@RequestParam(value = "cid" , required = true) int cid){
        return binService.get(userService.getCurrentUser().getUid(), cid);
    }

    @PostMapping("/restore")
    public DeletedContact restore(@RequestBody DeletedContact deletedContact) {
        return binService.restoreContact(userService.getCurrentUser().getUid(), deletedContact.getCid());
    }

    @PostMapping("/delete")
    public void delete(@RequestBody DeletedContact deletedContact) {
        binService.delete(userService.getCurrentUser().getUid(), deletedContact.getCid());
    }
}
