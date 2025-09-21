package com.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springbootbackend.model.Admin;

import java.util.List;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByEmail(String email);
	@Query("SELECT a FROM Admin a WHERE a.email = :email")
	List<Admin> findAdminsByEmailId(@Param("email") String email);
    
}