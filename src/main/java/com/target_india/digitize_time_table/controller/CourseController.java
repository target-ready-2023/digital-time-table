package com.target_india.digitize_time_table.controller;

import com.target_india.digitize_time_table.model.Course;
import com.target_india.digitize_time_table.model.CourseInfo;
import com.target_india.digitize_time_table.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("courses")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping("/{class_id}")
    public ResponseEntity<List<CourseInfo>> getAllCoursesOfClass(@PathVariable(name="class_id") int classId){
        return new ResponseEntity<>(courseService.getCourseByClassId(classId), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<CourseInfo>> getAllCourses(){
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.CREATED);
    }

    @PostMapping("/{course_name}/{class_name}/{section}/{instructor_id}")
    public ResponseEntity<String> addCourse(@RequestBody Course course) {
        return new ResponseEntity<>(courseService.addCourse(course), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/{name}/{instructor_id}")
    public ResponseEntity<String> updateCourse(@PathVariable int id, @PathVariable String name, @PathVariable(name = "class_id") int classID, @PathVariable(name="instructor_id") int instructorId) {
        String result = courseService.updateCourse(id, name, classID,instructorId);
        if (result == null)
            return new ResponseEntity<>("No course found with ID = "+id+" to update",HttpStatus.CREATED);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<String> updateCourse(@RequestBody Course course){
        String result = courseService.updateCourse(course);
        if(result.isEmpty()){
            return new ResponseEntity<>("No course found with ID"+course.getCourseId()+ "to update",HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id){
        String deleted = courseService.deleteCourseById(id);
        return new ResponseEntity<>(deleted,HttpStatus.NOT_FOUND);
    }



}
