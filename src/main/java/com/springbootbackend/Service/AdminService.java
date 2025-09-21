package com.springbootbackend.Service;

import java.util.List;

import com.springbootbackend.model.Admin;



public interface AdminService {
    Admin findByEmail(String email);
  
    List<Admin> getAllAdmins();
    void saveAdmin(Admin admin);
    boolean deleteAdminByEmail(String email);
}
