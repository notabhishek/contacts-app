package com.flock.springbootbackend.controller;

import com.flock.springbootbackend.exception.FileException;
import com.flock.springbootbackend.service.CSVService;
import com.flock.springbootbackend.requestObjects.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@Controller
@RequestMapping("/csv")
public class CSVController {
    @Autowired
    CSVService csvService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            csvService.save(file);
            message = "Uploaded file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename();
            throw new FileException("Could not upload the file: " + file.getOriginalFilename());
        }
    }
}
