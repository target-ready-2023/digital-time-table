package com.target_india.digitize_time_table.controller;
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
import java.util.Optional;

@RestController
@RequestMapping("admins")
public class AdminController {
    private final AdminService adminService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<String> getAllAdmins(){
        Optional<String> admins = adminService.getAllAdmins();
        if (admins.isEmpty()) {
            logger.error("No admin data found");
            return new ResponseEntity<>("No data found",HttpStatus.NOT_FOUND);
        }
        logger.info("admin data fetched successfully");
        return new ResponseEntity<>(String.valueOf(Optional.of(admins)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getAdminById(@PathVariable int id){
        Optional<String> admin = adminService.getAdminById(id);
        if (admin.isEmpty()){
            logger.error("No data found for admin with ID="+id);
            return new ResponseEntity<>("No data found for admin with ID="+id,HttpStatus.NOT_FOUND);
        }
        logger.info("admin data fetched successfully with id ="+id);
        return new ResponseEntity<>(String.valueOf(Optional.of(admin)),HttpStatus.CREATED);
    }




}
