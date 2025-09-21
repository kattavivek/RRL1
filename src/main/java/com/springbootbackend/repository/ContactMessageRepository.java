package com.springbootbackend.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootbackend.model.ContactMessage;





public interface ContactMessageRepository extends JpaRepository<ContactMessage, Integer>{

}

