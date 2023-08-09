package com.target_india.digitize_time_table.controller;


import com.target_india.digitize_time_table.service.DateValidationService;
import com.target_india.digitize_time_table.service.InstructorService;
import com.target_india.digitize_time_table.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
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
    public ResponseEntity<String> getInstructorDayTimeTable(@PathVariable int id, @PathVariable String date){
        //check date is correct or not
        String[] date_array = date.split("-",3);

        int day = Integer.parseInt(date_array[0]);
        int month = Integer.parseInt(date_array[1]);
        int year = Integer.parseInt(date_array[2]);

        dateValidationService.validateDate(day, month, year);

        String instructorDayTimeTable = String.valueOf(timeTableService.getInstructorDayTimeTable(id, date));
        if (instructorDayTimeTable.equals("saturday")){
            return new ResponseEntity<>("No timetable found on "+date+"as it is Saturday", HttpStatus.CREATED);
        }
        else if (instructorDayTimeTable.equals("sunday")){
            return new ResponseEntity<>("No timetable found on "+date+"as it is Sunday", HttpStatus.CREATED);
        }
        else if(instructorDayTimeTable == null){
            return new ResponseEntity<>("No timetable found", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(instructorDayTimeTable, HttpStatus.CREATED);
    }


    @GetMapping("/instructor/{id}")
    public ResponseEntity<String> getInstructorWeeklyTimeTable(@PathVariable int id){
        Optional instructorWeeklyTimeTable = timeTableService.getInstructorWeeklyTimeTable(id);
        if(instructorWeeklyTimeTable.isEmpty()){
            return new ResponseEntity<>("No timetable found", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(String.valueOf(instructorWeeklyTimeTable), HttpStatus.CREATED);
    }


    @GetMapping("/student/{class_id}/{date}")
    public ResponseEntity<String> getStudentDayTimeTable(@PathVariable(name = "class_id") int classId, @PathVariable String date){
        String[] dateArray = date.split("-",3);


        int day = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int year = Integer.parseInt(dateArray[2]);

        dateValidationService.validateDate(day, month, year);

        Optional<String> studentDayTimeTable = timeTableService.getStudentDayTimeTable(classId, date);
        if (studentDayTimeTable.equals("saturday")){
            return new ResponseEntity<>("No timetable found for class ID="+classId+"as it is saturday",HttpStatus.NOT_FOUND);
        }
        else if (studentDayTimeTable.equals("sunday")){
            return new ResponseEntity<>("No timetable found for class ID="+classId+"as it is sunday",HttpStatus.NOT_FOUND);
        }
        else if(studentDayTimeTable.isEmpty()){
            return new ResponseEntity<>("No data found for instructor with ID="+date,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(String.valueOf(studentDayTimeTable), HttpStatus.CREATED);
    }

    @GetMapping("/student/{class_id}")
    public ResponseEntity<String> getStudentWeeklyTimeTable(@PathVariable(name = "class_id") int classId){
        Optional<String> studentWeeklyTimeTable = timeTableService.getStudentWeeklyTimeTable(classId);
        if(studentWeeklyTimeTable.isEmpty()){
            return new ResponseEntity<>("No timetable found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(String.valueOf(studentWeeklyTimeTable), HttpStatus.CREATED);
    }


}
