package com.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

import com.springbootbackend.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel , Long> {

	UserModel findByEmailId(String emailId);

}
