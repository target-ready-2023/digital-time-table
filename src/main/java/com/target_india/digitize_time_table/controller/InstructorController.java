
package com.target_india.digitize_time_table.controller;

import com.target_india.digitize_time_table.model.Instructor;
import com.target_india.digitize_time_table.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("instructors")
@CrossOrigin(origins = "http://localhost:3000")
public class InstructorController {
    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<List<Instructor>> getAllInstructors(){
        return new ResponseEntity<>(instructorService.getAllInstructors(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable int id){
        return new ResponseEntity<>(instructorService.getInstructorById(id),HttpStatus.CREATED);
    }

    @PostMapping("/{name}/{contact}")
    public ResponseEntity<String> addInstructor( @PathVariable String name, @PathVariable String contact) {
        System.out.println(name+contact);
        instructorService.addInstructor(name, contact);
        return new ResponseEntity<>("Instructor added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}/{name}/{contact}")
    public ResponseEntity<String> updateInstructor(@PathVariable int id, @PathVariable String name, @PathVariable String contact) {
        String result = instructorService.updateInstructor(id, name, contact);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<String> updateInstructor(@RequestBody Instructor instructor){
        String result = instructorService.updateInstructor(instructor);
        if(result.isEmpty()){
            return new ResponseEntity<>("No instructor found with ID"+instructor.getInstructorId()+ "to update",HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable int id){
        String deleted = instructorService.deleteInstructorById(id);
        return new ResponseEntity<>(deleted,HttpStatus.OK);
    }

}

