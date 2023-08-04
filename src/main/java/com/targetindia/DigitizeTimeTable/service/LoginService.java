package com.targetindia.DigitizeTimeTable.service;

import com.targetindia.DigitizeTimeTable.repository.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    LoginDao logindao;

    public String login(String role, int id) {
        int result;
        if(role.equals("student")){
            result = logindao.checkStudentById(id);
        }
        else if(role.equals("instructor")){
            result = logindao.checkInstructorById(id);
        }
        else{
            result = logindao.checkAdminById(id);
        }
        return (result==0) ? "No " + role + " found with id = "+id : "login successful";
    }
}
