package com.target_india.digitize_time_table.controller;

import com.target_india.digitize_time_table.config.DbSettings;
import com.target_india.digitize_time_table.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {
    //private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/{role}/{id}")
    public ResponseEntity<String> login(@PathVariable String role, @PathVariable int id){

        return new ResponseEntity<>(loginService.login(role, id), HttpStatus.OK);

    }
}
