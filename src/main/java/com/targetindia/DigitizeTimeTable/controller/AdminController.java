
package com.targetindia.DigitizeTimeTable.controller;

import com.targetindia.DigitizeTimeTable.model.ClassInfo;
import com.targetindia.DigitizeTimeTable.model.Instructor;
import com.targetindia.DigitizeTimeTable.model.Course;
import com.targetindia.DigitizeTimeTable.service.AdminService;
//import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admins")
public class AdminController {
    @Autowired
    AdminService service;

    @PostMapping("/instructors")
    public ResponseEntity<String> addInstructor(@RequestBody Instructor instructor)
    {
        service.addInstructor(instructor);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("Instructor added successfully");
    }
    @PutMapping("/instructors/{id}/{name}/{contact}")
    public ResponseEntity<String> updateInstructor(@PathVariable int id, @PathVariable String name, @PathVariable long contact) {
        String result = service.updateInstructor(id, name, contact);
        if (result == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("No instructor found with ID = "+ id + " to update.");
        }
        return ResponseEntity.ok(result);

    }

    @PutMapping("/instructors")
    public ResponseEntity<String> updateInstructor(@RequestBody Instructor instr){
        String result = service.updateInstructor(instr);
        if(result == null){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("No instructor found with ID = "+ instr.getInstructor_id() + " to update.");
        }
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/instructors/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable int id){
        int deleted = service.deleteInstructorById(id);
        if(deleted == 1){
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Successfully deleted instructor with id = "+id);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("No instructor found with id = {%d} to delete"+ id);
    }

    @GetMapping("/instructors")
    public ResponseEntity<String> getAllInstructors(){
        String instructors = service.getAllInstructors();
        if (instructors == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("No data found");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(instructors);
    }

    @GetMapping("/instructors/{id}")
    public ResponseEntity<String> getInstructorById(@PathVariable int id){
        String instructor = service.getInstructorById(id);
        if (instructor == null){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("No data found for instructor with ID = "+id);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(instructor);
    }


    @GetMapping("/classInfo/{id}")
    public ResponseEntity<String> getClassInfo(@PathVariable int id) {
        String classInfo = service.getClassInfo(id);
        if (classInfo == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("No information found for class ID "+id);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(classInfo);
    }

    @GetMapping("/classInfo")
    public ResponseEntity<String> getAllClassInfo(){
        String classInfo=service.getAllClassInfo();
        if(classInfo==null){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("No data found");
        }
        System.out.println(classInfo);
        return ResponseEntity.of(Optional.of(classInfo));
    }
    ///course/{class_id}
    //course_id, course_name, instructor_name
    @GetMapping("/course/{class_id}")
    public ResponseEntity<String> getCourseByClassId(@PathVariable int class_id){
        String courseList =service.getCourseByClassId(class_id);
        if(courseList == null){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("No course data found for class ID = " + class_id);
        }
        return ResponseEntity.of(Optional.of(courseList));
    }

}






//@PostMapping("/addInstructors")
//    public void addAllInstructor(@RequestBody List<Instructor> instr)
//    {
//
//        service.addAllInstructors(instructors);
//        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("Instructors added successfully");
//    }
//   //instructor data in obj format
//
//    @PutMapping("/updateInstructor")
//    public void updateInstructor(@RequestBody Instructor instr){
//        service.updateInstructor(instr);
//    }
//    @PutMapping("/{class_id}/{dow}/{slot}")
//    public ResponseEntity<Optional<String>> updatePeriodInfo(@PathVariable int class_id, @PathVariable String dow, @PathVariable String slot){
//        String updatedPeriodInfo = service.updatePeriodInfo(class_id,dow,slot);
//        if (updatedPeriodInfo.isEmpty()) {
//            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
//        }
//        return ResponseEntity.ok(Optional.of(updatedPeriodInfo));
//
//    }
