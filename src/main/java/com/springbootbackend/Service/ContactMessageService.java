package com.springbootbackend.Service;



import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootbackend.model.ContactMessage;
import com.springbootbackend.repository.ContactMessageRepository;



@Service
public class ContactMessageService {
	
	
	@Autowired
    private ContactMessageRepository contactMessageRepository;

    public List<ContactMessage> getAllContactMessages() {
        return contactMessageRepository.findAll();
    }

}
