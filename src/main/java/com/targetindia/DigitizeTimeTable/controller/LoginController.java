package com.targetindia.DigitizeTimeTable.controller;

import com.targetindia.DigitizeTimeTable.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    LoginService service;
    @GetMapping("/{role}/{id}")
    public ResponseEntity<String> login(@PathVariable String role, @PathVariable int id){
        String result = service.login(role, id);
        if(result.equals("login successful")){
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(result);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(result);
    }
}
