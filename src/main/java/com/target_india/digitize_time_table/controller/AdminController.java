package com.target_india.digitize_time_table.controller;
import com.target_india.digitize_time_table.model.Admin;
import com.target_india.digitize_time_table.model.Instructor;
import com.target_india.digitize_time_table.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admins")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    private final AdminService adminService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins(){
        return new ResponseEntity<>(adminService.getAllAdmins(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable int id){
        return new ResponseEntity<>(adminService.getAdminById(id),HttpStatus.OK);
    }




}
