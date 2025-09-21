package com.test.SpringbootFrontend;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeMethod;
import java.util.*;
import com.springbootbackend.controller.UserModelController;
import com.springbootbackend.model.UserModel;
import com.springbootbackend.repository.UserRepository;
import com.springbootbackend.repository.userRepo;
import com.springbootbackend.Service.*;
import com.springbootbackend.model.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginRegister {
	
	@InjectMocks
    private UserModelController userModelController;

    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private userService userService1;

    @Mock
    private userRepo userRepository1;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUserWithValidCredentials() {
        UserModel user = new UserModel();
        user.setEmailId("barkhasoni@gmail.com");
        user.setPassword("1234");

        when(userRepository.findByEmailId("barkhasoni@gmail.com")).thenReturn(user);

        UserModel loginRequest = new UserModel();
        loginRequest.setEmailId("barkhasoni@gmail.com");
        loginRequest.setPassword("1234");

        ResponseEntity<String> responseEntity = userModelController.loginUser(loginRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Login successful", responseEntity.getBody());
    }

    @Test
    void testLoginUserWithInvalidCredentials() {
        when(userRepository.findByEmailId("barkhasoni@gmail.com")).thenReturn(null);

        UserModel loginRequest = new UserModel();
        loginRequest.setEmailId("barkhasoni@gmail.com");
        loginRequest.setPassword("1234");

        ResponseEntity<String> responseEntity = userModelController.loginUser(loginRequest);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Invalid credentials", responseEntity.getBody());
    }    
    
    @Test
    void testCreateUser() {
        UserModel newUser = new UserModel();
        newUser.setFirstName("Aniket");
        newUser.setLastName("Soni");
        newUser.setEmailId("aniket@gmail.com");
        newUser.setPassword("1234");
        newUser.setPhone("1234567890");

        when(userRepository.save(any(UserModel.class))).thenReturn(newUser);

        UserModel createdUser = userModelController.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals("Aniket", createdUser.getFirstName());
        assertEquals("Soni", createdUser.getLastName());
        assertEquals("aniket@gmail.com", createdUser.getEmailId());
        assertEquals("1234567890", createdUser.getPhone());
        assertEquals("1234", createdUser.getPassword());
    }
    
    @Test
    void testUpdateUserProfile() {
        long userId = 1L;
        UserModel existingUser = new UserModel();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(any(UserModel.class))).thenReturn(existingUser);

        UserModel userToUpdate = new UserModel();
        userToUpdate.setId(userId);
        userToUpdate.setFirstName("UpdatedFirstName");

        ResponseEntity<UserModel> responseEntity = userModelController.updateUserProfile(userId, userToUpdate);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(userId, responseEntity.getBody().getId());
        assertEquals("UpdatedFirstName", responseEntity.getBody().getFirstName());
    }

    @Test
    void testChangeUserPassword() {
        long userId = 1L;
        UserModel existingUser = new UserModel();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(any(UserModel.class))).thenReturn(existingUser);

        UserModel userToUpdatePassword = new UserModel();
        userToUpdatePassword.setId(userId);
        userToUpdatePassword.setPassword("newpassword123");

        ResponseEntity<UserModel> responseEntity = userModelController.changeUserPassword(userId, userToUpdatePassword);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(userId, responseEntity.getBody().getId());
        assertEquals("newpassword123", responseEntity.getBody().getPassword());
    }
    
    @Test
    void testGetConfirmedReservations() {
        User user1 = new User();
        user1.setStatus("Confirmed");

        User user2 = new User();
        user2.setStatus("Confirmed");

        List<User> confirmedUsers = new ArrayList<>();
        confirmedUsers.add(user1);
        confirmedUsers.add(user2);

        when(userRepository1.findByStatusIgnoreCase("Confirmed")).thenReturn(confirmedUsers);

        List<User> retrievedConfirmedUsers = userService1.getConfirmedReservations();

        assertNotNull(retrievedConfirmedUsers);
        assertEquals(2, retrievedConfirmedUsers.size());
        assertEquals("Confirmed", retrievedConfirmedUsers.get(0).getStatus());
        assertEquals("Confirmed", retrievedConfirmedUsers.get(1).getStatus());
    }


}
