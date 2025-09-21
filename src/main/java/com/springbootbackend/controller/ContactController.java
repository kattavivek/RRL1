package com.springbootbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootbackend.Service.ContactMessageService;
import com.springbootbackend.model.ContactMessage;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Contact", description = "Operations related to contact messages")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/cafe")
public class ContactController {
	
    @Autowired
    private ContactMessageService contactMessageService;

    @GetMapping("/messages")
    public ResponseEntity<List<ContactMessage>> getAllContactMessages() {
        List<ContactMessage> messages = contactMessageService.getAllContactMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
