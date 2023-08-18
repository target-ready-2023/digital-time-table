package com.target_india.digitize_time_table.service;
import com.target_india.digitize_time_table.model.Admin;
import com.target_india.digitize_time_table.repository.AdminDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// ...

class AdminServiceTest {

    @Mock
    private AdminDao adminDaoMock;

    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminService = new AdminService(adminDaoMock);
    }

    @Test
    void testGetAllAdmins_Success() throws Exception {
        ResultSet resultSetMock = mock(ResultSet.class);

        // Mock behavior of adminDao
        when(adminDaoMock.findAllAdmins()).thenReturn(resultSetMock);

        // Mock the behavior of resultSet.next() to simulate data
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("admin_id")).thenReturn(1);
        when(resultSetMock.getString("admin_name")).thenReturn("Admin Name");
        when(resultSetMock.getString("admin_contact")).thenReturn("Admin Contact");

        List<Admin> admins = adminService.getAllAdmins();

        assertFalse(admins.isEmpty());
        // Add additional assertions based on your implementation and mock setup
    }

    // ...

}




