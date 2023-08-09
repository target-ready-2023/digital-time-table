package com.target_india.digitize_time_table.service;

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
        int result;
        if(role.equals("student")){
            result = loginDao.checkStudentById(id);
        }
        else if(role.equals("instructor")){
            result = loginDao.checkInstructorById(id);
        }
        else{
            result = loginDao.checkAdminById(id);
        }
        return (result==0) ? "No " + role + " found with id = "+id : "login successful";
    }
}
