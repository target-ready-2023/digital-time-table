package com.target_india.digitize_time_table.controller;
import com.target_india.digitize_time_table.model.Student;
import com.target_india.digitize_time_table.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("students")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id){
        return new ResponseEntity<>(studentService.getStudentById(id),HttpStatus.CREATED);
    }

    @PostMapping("/{name}/{class_id}/{contact}")
    public ResponseEntity<String> addStudent(@PathVariable String name,@PathVariable(name="class_id") int id, @PathVariable String contact) {
        studentService.addStudent(name,id,contact);
        return new ResponseEntity<>("Student added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}/{name}/{class_id}/{contact}")
    public ResponseEntity<String> updateStudent(@PathVariable int id, @PathVariable String name, @PathVariable(name = "class_id") int classId, @PathVariable String contact) {
        String result = studentService.updateStudent(id, name, classId, contact);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<String> updateStudent(@RequestBody Student student){
        String result = studentService.updateStudent(student);
        if(result.isEmpty()){
            return new ResponseEntity<>("No student found with ID"+student.getStudentId()+ "to update",HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id){
        String deleted = studentService.deleteStudentById(id);
        return new ResponseEntity<>(deleted,HttpStatus.NOT_FOUND);
    }


}

