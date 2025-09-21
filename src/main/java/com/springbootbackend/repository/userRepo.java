package com.springbootbackend.repository;

import com.springbootbackend.model.*;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepo extends JpaRepository<User, Long> {

	List<User> findByStatusIgnoreCase(String string);
	
}

