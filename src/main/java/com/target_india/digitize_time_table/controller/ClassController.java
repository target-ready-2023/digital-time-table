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


    @GetMapping
    public ResponseEntity<List<ClassInfo>> getAllClassInfo(){
        return new ResponseEntity<>(classService.getAllClassInfo(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassInfo> getClassById(@PathVariable int id){
        return new ResponseEntity<>(classService.getClassById(id),HttpStatus.CREATED);
    }

    @PostMapping("/{name}/{section}/{strength}")
    public ResponseEntity<String> addClass( @PathVariable int name, @PathVariable String section, @PathVariable int strength) {
        return new ResponseEntity<>(classService.addClass(name, section, strength), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/{strength}")
    public ResponseEntity<String> updateClassStrength(@PathVariable int id, @PathVariable int strength) {
        return ResponseEntity.ok(classService.updateClassStrength(id, strength));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClassById(@PathVariable int id){
        return new ResponseEntity<>(classService.deleteClassById(id),HttpStatus.OK);
    }

}
