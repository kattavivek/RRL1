package com.springbootbackend.Service;



import com.springbootbackend.model.*;


import com.springbootbackend.repository.*;

import java.util.List;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userService {
	
	


    @Autowired
    private  userRepo userRepository;
    

    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}
	public void updateStatus(long userId, String newStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        if ("pending".equalsIgnoreCase(newStatus)) {
            // Set the status to "pending"
            user.setStatus("pending");
        } else {
            user.setStatus(newStatus);
        }
        userRepository.save(user);
    }
   

   
    public void confirmUser(long userId) {
        updateStatus(userId, "Confirmed");
    }

   
    public void cancelUser(long userId) {
        updateStatus(userId, "Cancelled");
    }
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

  
    public List<User> getConfirmedReservations() {
        return userRepository.findByStatusIgnoreCase("Confirmed");
    }
    
}
