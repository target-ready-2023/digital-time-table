package com.target_india.digitize_time_table.controller;
import com.target_india.digitize_time_table.model.Student;
import com.target_india.digitize_time_table.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<String> getAllStudents(){
        Optional<String> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            return new ResponseEntity<>("No data found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(String.valueOf(Optional.of(students)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getStudentById(@PathVariable int id){
        Optional<String> student = studentService.getStudentById(id);
        if (student.isEmpty()){

            return new ResponseEntity<>("No data found for student with ID="+id,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(String.valueOf(Optional.of(student)),HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        String result = studentService.addStudent(student);
        return new ResponseEntity<>("Student added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}/{name}/{class_id}/{contact}")
    public ResponseEntity<String> updateStudent(@PathVariable int id, @PathVariable String name, @PathVariable(name = "class_id") int classId, @PathVariable String contact) {
        String result = studentService.updateStudent(id, name, classId, contact);
        if (result.isEmpty())
            return new ResponseEntity<>("No student found with ID"+id+"to update",HttpStatus.CREATED);

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
        int deleted = studentService.deleteStudentById(id);
        if(deleted == 1){

            return new ResponseEntity<>("Successfully deleted student with ID="+id,HttpStatus.OK);
        }

        return new ResponseEntity<>("No student found with ID="+id+ "to delete",HttpStatus.NOT_FOUND);
    }


}

