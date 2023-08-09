
package com.target_india.digitize_time_table.controller;

import com.target_india.digitize_time_table.model.Instructor;
import com.target_india.digitize_time_table.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("instructors")
public class InstructorController {
    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<String> getAllInstructors(){
        Optional<String> instructors = instructorService.getAllInstructors();
        if (instructors.isEmpty()) {
            return new ResponseEntity<>("No data found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(String.valueOf(Optional.of(instructors)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getInstructorById(@PathVariable int id){
        Optional<String> instructor = instructorService.getInstructorById(id);
        if (instructor.isEmpty()){

            return new ResponseEntity<>("No data found for instructor with ID="+id,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(String.valueOf(Optional.of(instructor)),HttpStatus.CREATED);
    }

    @PostMapping("")
    public ResponseEntity<String> addInstructor(@RequestBody Instructor instructor) {
        instructorService.addInstructor(instructor);
        return new ResponseEntity<>("Instructor added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}/{name}/{contact}")
    public ResponseEntity<String> updateInstructor(@PathVariable int id, @PathVariable String name, @PathVariable String contact) {
        String result = instructorService.updateInstructor(id, name, contact);
        if (result.isEmpty())
            return new ResponseEntity<>("No instructor found with ID"+id+"to update",HttpStatus.CREATED);

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
        int deleted = instructorService.deleteInstructorById(id);
        if(deleted == 1){

            return new ResponseEntity<>("Successfully deleted instructor with ID="+id,HttpStatus.OK);
        }

        return new ResponseEntity<>("No instructor found with ID="+id+ "to delete",HttpStatus.NOT_FOUND);
    }

}

