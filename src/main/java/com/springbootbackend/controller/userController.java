package com.springbootbackend.controller;



import com.springbootbackend.Service.*;
import com.springbootbackend.model.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "Operations related to user management")
@CrossOrigin
(
	origins="http://localhost:4200"
	
)

@RestController
@RequestMapping("/api/users")
public class userController {
    private final userService userService;

    @Autowired
    public userController(userService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
    	user.setStatus("pending");
        return userService.saveUser(user);
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (EmptyResultDataAccessException ex) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
        
    }
    @PutMapping("/{userId}/status")
    public void updateStatus(@PathVariable long userId, @RequestParam String newStatus) {
        userService.updateStatus(userId, newStatus);
    }

    @PutMapping("/{userId}/confirm")
    public void confirmUser(@PathVariable long userId) {
        userService.confirmUser(userId);
    }

    @PutMapping("/{userId}/deny")
    public void denyUser(@PathVariable long userId) {
        userService.cancelUser(userId);
    }
    
   

    @GetMapping("/confirmed-reservations")
    public List<User> getConfirmedReservations() {
        return userService.getConfirmedReservations();
    }

    
}
