package com.springbootbackend.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.springbootbackend.model.Admin;
import com.springbootbackend.repository.AdminRepository;


@Service
public class AdminServiceImpl implements AdminService {

    
    @Autowired
    private  AdminRepository adminUserRepository;
         

    @Override
    public Admin findByEmail(String email) {
        return adminUserRepository.findByEmail(email);
    }

    @Override
    public void saveAdmin(Admin admin) {
        adminUserRepository.save(admin);
    }
    @Override
    public List<Admin> getAllAdmins() {
        return adminUserRepository.findAll();
    }

    @Override
    public boolean deleteAdminByEmail(String email) {
        Admin admin = adminUserRepository.findByEmail(email);
        if (admin != null) {
            adminUserRepository.delete(admin);
            return true; // Deletion successful
        }
        return false; // Admin not found, deletion failed
    }
}

