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
//
//    @Value("${spring.datasource.url}")
//    public String url;
//
//    @Value("${spring.datasource.username}")
//    public String username;
//
//    @Value("${spring.datasource.password}")
//    public String password;
//
//


    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

//    @GetMapping("")
//    public ResponseEntity<List> getinfo(){
//        DbSettings dbSettings = new DbSettings();
//        dbSettings.setPassword(password);
//        dbSettings.setUrl(url);
//        dbSettings.setUsername(username);
//
//        return new ResponseEntity<>(dbSettings.getDbInfo(), HttpStatus.CREATED);
//    }
    @GetMapping("/{role}/{id}")
    public ResponseEntity<String> login(@PathVariable String role, @PathVariable int id){
        String result = loginService.login(role, id);
        if(result.equals("login successful")){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}
