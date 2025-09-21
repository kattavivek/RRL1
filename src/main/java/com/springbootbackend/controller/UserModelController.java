package com.springbootbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootbackend.model.UserModel;
import com.springbootbackend.repository.*;
import com.springbootbackend.exception.*;

import javax.persistence.EntityNotFoundException;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserModel", description = "Operations related to user model management")
@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserModelController {
	
	@Autowired
	private UserRepository userRepository;
	
	//get all user rest api
	@GetMapping("/users")
	public List<UserModel> getAllUsers(){
		return userRepository.findAll();
	}
	
	//create user rest api
	@PostMapping("/users")
	public UserModel createUser(@RequestBody UserModel user) {
		return userRepository.save(user);
	}

	@PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserModel loginRequest) {

        UserModel user = userRepository.findByEmailId(loginRequest.getEmailId());

        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        return ResponseEntity.ok("Login successful");
    }
	
	@PutMapping("/users/{id}")
    public ResponseEntity<UserModel> updateUserProfile(@PathVariable Long id, @RequestBody UserModel user) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + id));
        
        existingUser.setId(user.getId());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhone(user.getPhone());
        existingUser.setEmailId(user.getEmailId());
        existingUser.setPassword(user.getPassword());
        

        UserModel updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    }
	
	@PutMapping("/users/{id}/changepassword")
    public ResponseEntity<UserModel> changeUserPassword(@PathVariable Long id, @RequestBody UserModel user) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + id));

       

        existingUser.setPassword(user.getPassword());
        UserModel updatedUser =userRepository.save(existingUser);

        return ResponseEntity.ok(updatedUser);
    }
	
	@GetMapping("/users/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return ResponseEntity.ok(user);
    }
	

	
}
