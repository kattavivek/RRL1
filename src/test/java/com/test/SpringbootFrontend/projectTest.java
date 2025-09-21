package com.test.SpringbootFrontend;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.*;


import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.springbootbackend.controller.*;
import com.springbootbackend.model.*;
import com.springbootbackend.repository.*;
import com.springbootbackend.Service.*;

@TestMethodOrder(OrderAnnotation.class)
public class projectTest {


    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemserviceImpl itemService;
    @InjectMocks
    private UserModelController userModelController;
	
    

    @InjectMocks
    private userController userController;
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private userService userService1;

    @Mock
    private userRepo userRepository1;

    
    @SuppressWarnings("deprecation")
	@BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    //admin test
    @Test
    @Order(1)
    void testLogin() {
        // Mock behavior for AdminService
        Admin mockAdmin = new Admin();
        mockAdmin.setEmail("admin@example.com");
        mockAdmin.setPassword("admin123");
        when(adminService.findByEmail("admin@example.com")).thenReturn(mockAdmin);

        // Perform the test
        Admin adminUser = new Admin();
        adminUser.setEmail("admin@example.com");
        adminUser.setPassword("admin123");
        ResponseEntity<String> response = adminController.login(adminUser);

        // Verify the result
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Login successful.");
    }

    @Test
    @Order(2)
    void testRegister() {
        // Set up mock behavior for void method
        doNothing().when(adminService).saveAdmin(any(Admin.class));

        // Perform the test
        Admin adminUser = new Admin();
        adminUser.setEmail("newadmin@example.com");
        adminUser.setPassword("NewAdmin123@");

        ResponseEntity<String> response = adminController.register(adminUser);

        // Verify the result
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Admin user registered successfully.");

        // Verify that the void method was called
        verify(adminService, times(1)).saveAdmin(any(Admin.class));
    }

    @Test
    @Order(3)
    void testGetAllAdmins() {
        // Set up mock behavior
        List<Admin> mockAdmins = new ArrayList<>();
        mockAdmins.add(new Admin());
        when(adminService.getAllAdmins()).thenReturn(mockAdmins);

        // Perform the test
        ResponseEntity<List<Admin>> response = adminController.getAllAdmins();

        // Verify the result
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
    }
//User test
@Test
@Order(4)
    public void testGetItemById() {
        int itemId = 1;
        ItemModel mockItem = new ItemModel();
        mockItem.setId(itemId);
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(mockItem));

        ItemModel retrievedItem = itemService.getItemById(itemId);

        assertNotNull(retrievedItem);
        assertEquals(itemId, retrievedItem.getId());
    }

    @Test
    @Order(5)
    public void testGetAllItems() {
        // Create some mock data for testing
        ItemModel item1 = new ItemModel();
        ItemModel item2 = new ItemModel();
        when(itemRepository.findAll()).thenReturn(List.of(item1, item2));

        Iterable<ItemModel> items = itemService.getAllItems();

        assertNotNull(items);
        assertEquals(2, ((List<ItemModel>) items).size());
    }
//item update test
    @Test
    @Order(6)
    public void testUpdateItem() {
        int itemId = 1;
        ItemModel existingItem = new ItemModel();
        existingItem.setId(itemId);
        existingItem.setItemname("Item 1");
        
        ItemModel updatedItem = new ItemModel();
        updatedItem.setId(itemId);
        updatedItem.setItemname("Updated Item");
        updatedItem.setItemprice("15.99");
        updatedItem.setItemdesc("Updated description");
        updatedItem.setStatus("enabled");

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(existingItem));
        when(itemRepository.save(existingItem)).thenReturn(updatedItem);

        ItemModel result = itemService.updateItem(itemId, updatedItem);

        assertNotNull(result);
        assertEquals("Updated Item", result.getItemname());
        assertEquals("15.99", result.getItemprice());
        assertEquals("Updated description", result.getItemdesc());
        assertEquals("enabled", result.getStatus());
    }

    @Test
    @Order(7)
    public void testEnableItem() {
        int itemId = 1;
        ItemModel existingItem = new ItemModel();
        existingItem.setId(itemId);
        existingItem.setStatus("disabled");

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(existingItem));
        when(itemRepository.save(existingItem)).thenReturn(existingItem);

        ItemModel result = itemService.enableItem(itemId);

        assertNotNull(result);
        assertEquals("enabled", result.getStatus());
    }

    @Test
    @Order(8)
    public void testDisableItem() {
        int itemId = 1;
        ItemModel existingItem = new ItemModel();
        existingItem.setId(itemId);
        existingItem.setStatus("enabled");

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(existingItem));
        when(itemRepository.save(existingItem)).thenReturn(existingItem);

        ItemModel result = itemService.disableItem(itemId);

        assertNotNull(result);
        assertEquals("disabled", result.getStatus());
    }
    //user login test
    @Test
    @Order(9)
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
//user test
    @Test
    @Order(10)
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
    @Order(11)
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
    // change user password test
    @Test
    @Order(12)
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
    // update user test
    @Test
    @Order(13)

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
 // table tests   
    @Test
    @Order(14)
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("John Doe", "john@example.com", "123456789", "1", "A", "2023-08-13", "12:00 PM", "Test Message"));
        users.add(new User("Jane Smith", "jane@example.com", "987654321", "2", "B", "2023-08-14", "2:00 PM", "Another Message"));

        when(userRepository1.findAll()).thenReturn(users);

        List<User> result = userService1.getAllUsers();

        assertEquals(users.size(), result.size());
        assertEquals(users.get(0).getName(), result.get(0).getName());
        assertEquals(users.get(1).getEmail(), result.get(1).getEmail());
    }

    @Test
    @Order(15)
    public void testUpdateStatus() {
        long userId = 1L;
        String newStatus = "Confirmed";

        User user = new User("John Doe", "john@example.com", "123456789", "1", "A", "2023-08-13", "12:00 PM", "Test Message");
        user.setId(userId);

        when(userRepository1.findById(userId)).thenReturn(Optional.of(user));

        userService1.updateStatus(userId, newStatus);

        verify(userRepository1, times(1)).save(user);

        assertEquals(newStatus, user.getStatus());
    }

    @Test
    @Order(16)
    public void testConfirmUser() {
        long userId = 1L;

        User user = new User("John Doe", "john@example.com", "123456789", "1", "A", "2023-08-13", "12:00 PM", "Test Message");
        user.setId(userId);

        when(userRepository1.findById(userId)).thenReturn(Optional.of(user));

        userService1.confirmUser(userId);

        verify(userRepository1, times(1)).save(user);

        assertEquals("Confirmed", user.getStatus());
    }

    @Test
    @Order(17)
    public void testCancelUser() {
        long userId = 1L;

        User user = new User("John Doe", "john@example.com", "123456789", "1", "A", "2023-08-13", "12:00 PM", "Test Message");
        user.setId(userId);

        when(userRepository1.findById(userId)).thenReturn(Optional.of(user));

        userService1.cancelUser(userId);

        verify(userRepository1, times(1)).save(user);

        assertEquals("Cancelled", user.getStatus());
    }    
    @Test
    @Order(18)
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
    
    @Test
    @Order(19)
    void testAddItem() {
        // Create a mock item to be added
        ItemModel mockItem = new ItemModel();
        mockItem.setItemname("Test Item");
        mockItem.setItemprice("200");
        mockItem.setItemdesc("Test Description");
        mockItem.setStatus("enabled");
        // Set up the mock behavior of the service
     // Set up the mock behavior of the repository
        when(itemRepository.save(mockItem)).thenReturn(mockItem);

        // Call the addItem service method
        ItemModel addedItem = itemService.addItem(mockItem);

        // Assert the returned item
        assertEquals("Test Item", addedItem.getItemname());
        assertEquals("200", addedItem.getItemprice());
        assertEquals("Test Description", addedItem.getItemdesc());
        assertEquals("enabled", addedItem.getStatus());
    }
    
    @Test
    @Order(20)
    void testDeleteItem() {
        int itemId = 1;

        // Perform the test
        itemRepository.deleteById(itemId);

        // Verify that the deleteById method was called once with the specified itemId
        verify(itemRepository, times(1)).deleteById(itemId);
    }
    }


    