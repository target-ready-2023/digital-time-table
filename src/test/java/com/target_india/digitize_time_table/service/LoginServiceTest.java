package com.target_india.digitize_time_table.service;
import com.target_india.digitize_time_table.exceptions.InvalidRoleException;
import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import com.target_india.digitize_time_table.repository.LoginDao;
import com.target_india.digitize_time_table.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceTest {

    @Mock
    private LoginDao loginDaoMock;

    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginService = new LoginService(loginDaoMock);
    }

    @Test
    void testLogin_StudentSuccess() {
        int id = 123;
        String role = "student";
        when(loginDaoMock.checkStudentById(id)).thenReturn(1);

        String result = loginService.login(role, id);

        assertEquals("login successful", result);
        verify(loginDaoMock).checkStudentById(id);
    }

    @Test
    void testLogin_InstructorSuccess() {
        int id = 456;
        String role = "instructor";
        when(loginDaoMock.checkInstructorById(id)).thenReturn(1);

        String result = loginService.login(role, id);

        assertEquals("login successful", result);
        verify(loginDaoMock).checkInstructorById(id);
    }

    @Test
    void testLogin_AdminSuccess() {
        int id = 789;
        String role = "admin";
        when(loginDaoMock.checkAdminById(id)).thenReturn(1);

        String result = loginService.login(role, id);

        assertEquals("login successful", result);
        verify(loginDaoMock).checkAdminById(id);
    }

    @Test
    void testLogin_InvalidRole() {
        int id = 123;
        String role = "invalidRole";
        assertThrows(InvalidRoleException.class, () -> loginService.login(role, id));
    }

    @Test
    void testLogin_ResourceNotFound() {
        int id = 123;
        String role = "student";
        when(loginDaoMock.checkStudentById(id)).thenReturn(0);

        assertThrows(ResourceNotFoundException.class, () -> loginService.login(role, id));
    }
}
//
//public class LoginServiceTest {
//}
