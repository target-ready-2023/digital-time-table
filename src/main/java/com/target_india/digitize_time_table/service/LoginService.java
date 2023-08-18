package com.target_india.digitize_time_table.service;

import com.target_india.digitize_time_table.exceptions.InvalidRoleException;
import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import com.target_india.digitize_time_table.repository.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginDao loginDao;

    @Autowired
    public LoginService(LoginDao loginDao)
    {
        this.loginDao = loginDao;
    }

    public String login(String role, int id) {
        int result = switch (role) {
            case "student" -> loginDao.checkStudentById(id);
            case "instructor" -> loginDao.checkInstructorById(id);
            case "admin" -> loginDao.checkAdminById(id);
            default -> throw new InvalidRoleException("No role found with name " + role);
        };

        if (result==0) throw new ResourceNotFoundException( "No " + role + " found with id "+id );

        return "login successful";
    }
}
