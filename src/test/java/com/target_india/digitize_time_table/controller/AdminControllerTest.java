package com.target_india.digitize_time_table.controller;

import com.target_india.digitize_time_table.model.Admin;
import com.target_india.digitize_time_table.service.AdminService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {AdminControllerTest.class})
public class AdminControllerTest {

    @Mock
    AdminService adminService;

    @InjectMocks
    AdminController adminController;

    @Test
    public void testGetAllAdmins() throws Exception {
        Admin admin1 = new Admin(1, "Amritesh", "1234567980");
        Admin admin2 = new Admin(2, "Anand", "1234567981");
        List<Admin> admins = new ArrayList<Admin>();
        admins.add(admin1);
        admins.add(admin2);

        when(adminService.getAllAdmins()).thenReturn(admins);

        ResponseEntity<?> response = adminController.getAllAdmins();

        List<Admin> responseAdmins = (List<Admin>) response.getBody();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(2,responseAdmins.size());

    }
    @Test
    public void testGetAdminById() {
        int adminId = 1;
        Admin admin = new Admin(adminId, "Amritesh", "1234567980");

        when(adminService.getAdminById(adminId)).thenReturn(admin);

        ResponseEntity<Admin> response = adminController.getAdminById(adminId);

        Admin responseAdmin = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(admin.getAdminId(), responseAdmin.getAdminId());
        assertEquals(admin.getAdminName(), responseAdmin.getAdminName());
        assertEquals(admin.getAdminContact(), responseAdmin.getAdminContact());
    }


}

