package com.springbootbackend.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.springbootbackend.Service.AdminService;
import com.springbootbackend.model.Admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Admin", description = "Operations related to admin management")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
        
    }

    @PostMapping("/login")
    @Operation(
        summary = "Admin login",
        description = "Authenticate an admin user with email and password. Returns a success message if credentials are valid."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login successful."),
        @ApiResponse(responseCode = "400", description = "Admin not found."),
        @ApiResponse(responseCode = "401", description = "Invalid credentials."),
        @ApiResponse(responseCode = "404", description = "Not found."),
        @ApiResponse(responseCode = "403", description = "Forbidden."),
        @ApiResponse(responseCode = "405", description = "Method not allowed.")
    })
    public ResponseEntity<String> login(@RequestBody Admin adminUser) {
        String email = adminUser.getEmail();
        String password = adminUser.getPassword();

        // Retrieve user details from the database
        Admin admin = adminService.findByEmail(email);
        if (Objects.isNull(admin) ) {
            return ResponseEntity.badRequest().body("Admin not found.");
        }

        // Validate admin email and password
        if (!admin.getPassword().equals(password) || !admin.getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Successful authentication
        return ResponseEntity.ok("Login successful.");
    }

    @PostMapping("/register")
    @Operation(
        summary = "Register a new admin user",
        description = "Register a new admin user with email and password. Validates email format and password complexity."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Admin user registered successfully."),
        @ApiResponse(responseCode = "400", description = "Email and password are required or invalid format/password complexity."),
        @ApiResponse(responseCode = "401", description = "Unauthorized."),
        @ApiResponse(responseCode = "404", description = "Not found."),
        @ApiResponse(responseCode = "403", description = "Forbidden."),
        @ApiResponse(responseCode = "405", description = "Method not allowed.")
    })
    public ResponseEntity<String> register(@RequestBody Admin adminUser) {
        String email = adminUser.getEmail();
        String password = adminUser.getPassword();

        // Perform server-side validation
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email and password are required.");
        }
        
     // Validate email format using regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            return ResponseEntity.badRequest().body("Invalid email format.");
        }

        // Validate password complexity using regex
//        String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
//        if (!password.matches(passwordRegex)) {
//            return ResponseEntity.badRequest().body("Password must meet complexity requirements.");
//        }
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/]).{8,}$";
        if (!password.matches(passwordRegex)) {
            return ResponseEntity.badRequest().body(
                    "Password must be at least 8 characters long and include at least one uppercase letter, " +
                            "one lowercase letter, one number, and one special character.");
        }


        List<Admin> admins = adminService.getAllAdmins();
        for( Admin existingAdmin : admins) {
            if (existingAdmin.getEmail().equals(email)) {
                return ResponseEntity.badRequest().body("Email already exists.");
            }
        }
        
        // Create a new Admin object and set the properties
        Admin newAdmin = new Admin();
        newAdmin.setEmail(email);
        newAdmin.setPassword(password);

        // Save the new admin user to the database
        adminService.saveAdmin(newAdmin);

        return ResponseEntity.ok("Admin user registered successfully.");
    }


    @Operation(
        summary = "Retrieve all admin details",
        description = "Get all admin details with id, email id, and password."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Created and added Successfully", content = { @Content(schema = @Schema(implementation = Admin.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", description = "Bad Request", content = { @Content(schema = @Schema(implementation = Admin.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = { @Content(schema = @Schema(implementation = Admin.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = { @Content(schema = @Schema(implementation = Admin.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "405", description = "Not found", content = { @Content(schema = @Schema(implementation = Admin.class), mediaType = "application/json") })
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();

        if (admins.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(admins);
    }

    /*@DeleteMapping("/delete/{email}")
    @Operation(
            summary = "Delete an admin user",
            description = "Deletes an admin user by their ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Admin deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Admin not found."),
            @ApiResponse(responseCode = "401", description = "Unauthorized."),
            //@ApiResponse(responseCode = "403", description = "Forbidden.")
    })
    public ResponseEntity<String> deleteAdmin(@PathVariable String email) {
        Admin admin = adminService.findByEmail(email);
        if (admin == null || admin.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found.");
        }
        boolean isDeleted = adminService.deleteAdminByEmail(email);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found.");
        }
        return ResponseEntity.ok("Admin deleted successfully.");
    }*/
}
