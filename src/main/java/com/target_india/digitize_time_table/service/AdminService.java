package com.target_india.digitize_time_table.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target_india.digitize_time_table.config.DbSettings;
import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import com.target_india.digitize_time_table.model.Admin;
import com.target_india.digitize_time_table.model.ClassInfo;
import com.target_india.digitize_time_table.model.Student;
import com.target_india.digitize_time_table.repository.AdminDao;
import com.target_india.digitize_time_table.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.*;


@Service
public class AdminService {

    private final AdminDao adminDao;

    @Autowired
    public AdminService(AdminDao adminDao){
        this.adminDao=adminDao;
    }

    public List<Admin> getAllAdmins() {
        try (ResultSet resultSet = adminDao.findAllAdmins();){
            List<Admin> admins = new ArrayList<Admin>();
            while (resultSet.next()) {
                int id = resultSet.getInt("admin_id");
                String name = resultSet.getString("admin_name");
                String contact = resultSet.getString("admin_contact");
                Admin admin = new Admin(id, name, contact);
                admins.add(admin);
            }
            if (admins.isEmpty()) {
                throw new ResourceNotFoundException("No data found");
            }

            return admins;
        }
        catch(Exception exception){
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }

    public Admin getAdminById(int id) {
        try {
            ResultSet resultSet = adminDao.findAdminById(id);
            Admin admin = new Admin();
            if (resultSet.next()) {
                admin.setAdminId(resultSet.getInt(1));
                admin.setAdminName(resultSet.getString(2));
                admin.setAdminContact(resultSet.getString(3));
            } else {
                throw new ResourceNotFoundException("No admin found with id: "+id);
            }

            return admin;
        }
        catch(Exception exception){
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
}