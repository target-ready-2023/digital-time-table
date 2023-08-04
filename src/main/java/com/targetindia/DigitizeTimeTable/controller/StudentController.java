//package com.targetindia.DigitizeTimeTable.controller;
//
//import com.targetindia.DigitizeTimeTable.service.AdminService;
//import com.targetindia.DigitizeTimeTable.service.StudentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//@RestController
//@RequestMapping("student")
//public class StudentController {
//    @Autowired
//    StudentService service;
//
//    @GetMapping("/GET/time_table/class_id/{class_id}/day/{day}")
//    public HashMap<String, ArrayList<String>> getStudentDayTimeTable(@PathVariable int class_id, @PathVariable String day){
//        return service.getStudentDayTimeTable(class_id, day);
//    }
//
//    @GetMapping("/time_table/{class_id}")
//    public HashMap<String, HashMap<String, ArrayList<String>>> getStudentWeeklyTimeTable(@PathVariable int class_id){
//        return service.getStudentWeeklyTimeTable(class_id);
//    }
//}
package com.targetindia.DigitizeTimeTable.controller;

import com.targetindia.DigitizeTimeTable.service.AdminService;
import com.targetindia.DigitizeTimeTable.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static java.time.Year.isLeap;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    StudentService service;

    @GetMapping("/time_table/{class_id}/{date}")
    public ResponseEntity<String> getStudentDayTimeTable(@PathVariable int class_id, @PathVariable String date){
        String[] date_array = date.split("-",3);


        int day = Integer.parseInt(date_array[0]);
        int month = Integer.parseInt(date_array[1]);
        int year = Integer.parseInt(date_array[2]);

        int num_of_days = getDaysOfMonth(month, year);

        if(day > num_of_days || day < 1){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Invalid day provided");
        }
        else if(month > 12 || month < 1){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Invalid month provided");
        }
        String studentDayTimeTable = service.getStudentDayTimeTable(class_id, date);
        if (studentDayTimeTable.equals("saturday")){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("No time table found for class_id = "+class_id+" on "+date + " as it is saturday");
        }
        else if (studentDayTimeTable.equals("sunday")){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("No time table found for class_id = "+class_id+" on "+date + " as it is sunday");
        }
        else if(studentDayTimeTable == null){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("No time table found on "+date);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(studentDayTimeTable);
    }

    @GetMapping("/time_table/{class_id}")
    public ResponseEntity<String> getStudentWeeklyTimeTable(@PathVariable int class_id){
        String studentWeeklyTimeTable = service.getStudentWeeklyTimeTable(class_id);
        if(studentWeeklyTimeTable == null){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("No time table found");
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(studentWeeklyTimeTable);
    }
    private int getDaysOfMonth(int month, int year) {
        switch(month){
            case 1,3,5,7,8,10,12:
                return 31;
            case 2:
                return isLeap(year)?29:28;
            default:
                return 30;
        }
    }

}

