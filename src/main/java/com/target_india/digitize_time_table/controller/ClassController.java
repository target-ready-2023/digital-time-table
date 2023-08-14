package com.target_india.digitize_time_table.controller;

import com.target_india.digitize_time_table.model.ClassInfo;
import com.target_india.digitize_time_table.service.ClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("class")
@CrossOrigin(origins = "http://localhost:3000")
public class ClassController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final ClassService classService;
    public ClassController(ClassService classService)
    {
        this.classService=classService;
    }

    @GetMapping("/{class_id}")
    public ResponseEntity<ClassInfo> getClassInfo(@PathVariable(name = "class_id") int classId) {
        return new ResponseEntity<>(classService.getClassInfo(classId),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClassInfo>> getAllClassInfo(){
        return new ResponseEntity<>(classService.getAllClassInfo(), HttpStatus.CREATED);
    }

}
