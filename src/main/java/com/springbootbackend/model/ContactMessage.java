package com.springbootbackend.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ContactMessage {

	@Id
    @GeneratedValue
    private Long id;
    
    private String name;
    private String email;
    private String message;
    
    
	public ContactMessage() {
		super();
	}


	public ContactMessage(Long id, String name, String email, String message) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.message = message;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return "ContactMessage [id=" + id + ", name=" + name + ", email=" + email + ", message=" + message + "]";
	}   
    
}
