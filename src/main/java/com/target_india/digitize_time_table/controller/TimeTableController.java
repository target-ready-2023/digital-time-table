package com.target_india.digitize_time_table.controller;


import com.target_india.digitize_time_table.model.TimeTable;
import com.target_india.digitize_time_table.service.DateValidationService;
import com.target_india.digitize_time_table.service.InstructorService;
import com.target_india.digitize_time_table.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("time_table")
public class TimeTableController {
    private final TimeTableService timeTableService;
    private final DateValidationService dateValidationService;

    @Autowired
    public TimeTableController(TimeTableService timeTableService, DateValidationService dateValidationService) {
        this.timeTableService = timeTableService;
        this.dateValidationService = dateValidationService;
    }

    @GetMapping("/instructor/{id}/{date}") //2.8.2023
    public ResponseEntity<List<TimeTable>> getInstructorDayTimeTable(@PathVariable int id, @PathVariable String date){
        //check date is correct or not
        String[] date_array = date.split("-",3);

        int day = Integer.parseInt(date_array[2]);
        int month = Integer.parseInt(date_array[1]);
        int year = Integer.parseInt(date_array[0]);

        dateValidationService.validateDate(day, month, year);
        return new ResponseEntity<>(timeTableService.getInstructorDayTimeTable(id,date), HttpStatus.CREATED);
    }


    @GetMapping("/instructor/{id}")
    public ResponseEntity<List<TimeTable>> getInstructorWeeklyTimeTable(@PathVariable int id){
        return new ResponseEntity<>(timeTableService.getInstructorWeeklyTimeTable(id), HttpStatus.CREATED);
    }


    @GetMapping("/student/{class_id}/{date}")
    public ResponseEntity<List<TimeTable>> getStudentDayTimeTable(@PathVariable(name = "class_id") int classId, @PathVariable String date){
        String[] dateArray = date.split("-",3);


        int year = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[2]);


        dateValidationService.validateDate(day, month, year);

        return new ResponseEntity<>(timeTableService.getStudentDayTimeTable(classId, date), HttpStatus.CREATED);
    }

    @GetMapping("/student/{class_id}")
    public ResponseEntity<List<TimeTable>> getStudentWeeklyTimeTable(@PathVariable(name = "class_id") int classId){
        return new ResponseEntity<>(timeTableService.getStudentWeeklyTimeTable(classId), HttpStatus.CREATED);
    }
    @DeleteMapping("/{class_id}/{week}/{slot}")
    public ResponseEntity<String> deleteInstructor(@PathVariable(name = "class_id") int classId, @PathVariable String week,@PathVariable String slot ){
        String deleted = timeTableService.deleteTimeTableById(classId,week,slot);
        return ResponseEntity.ok(deleted);
    }
    @PutMapping("/{class_id}/{week}/{slot}/{subject}/{location}")
    public ResponseEntity<String>  updateTimeTableSlot(@PathVariable(name = "class_id") int classId,@PathVariable String week,@PathVariable String slot,@PathVariable String subject,@PathVariable String location ){
        String result = timeTableService.updateTimeTableSlot(classId, week, slot,subject,location);
        return ResponseEntity.ok(result);
    }
}