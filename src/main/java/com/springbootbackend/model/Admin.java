package com.springbootbackend.model;

import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "admin_users")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Admin's Id", example = "123")
    private Long id;

    @Schema(description = "Admin's email", example = "abc@gmail.com")
    private String email;
    
    @Schema(description = "Admin's password", example = "Abc@1234")
    private String password;
    
	public Admin(Long id, String email, String password) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public Admin() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

   
}
