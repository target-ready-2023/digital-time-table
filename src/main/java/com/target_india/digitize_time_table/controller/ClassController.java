//package com.target_india.digitize_time_table.controller;
//
//import com.target_india.digitize_time_table.service.ClassService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.Optional;
//@RestController
//@RequestMapping("/")
//public class ClassController {
//    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
//    private final ClassService classService;
//    public ClassController(ClassService classService)
//    {
//        this.classService=classService;
//    }
//
//    @GetMapping("/class_info/{class_id}")
//    public ResponseEntity<String> getClassInfo(@PathVariable(name = "class_id") int classId) {
//        Optional<String> classInfo = classService.getClassInfo(classId);
//        if (classInfo == null) {
//            logger.error("No information found for class ID={}"+classId);
//            return new ResponseEntity<>("No information found for class ID={}"+classId, HttpStatus.NOT_FOUND);
//        }
//        logger.info("class info fetched successfully of class with id ="+classId);
//        return new ResponseEntity<>(String.valueOf(Optional.of(classInfo)),HttpStatus.CREATED);
//    }
//
//    @GetMapping("/class_info")
//    public ResponseEntity<String> getAllClassInfo(){
//        Optional<String> classInfo=classService.getAllClassInfo();
//        if(classInfo==null){
//            logger.error("no class found");
//            return new ResponseEntity<>("No data found",HttpStatus.NOT_FOUND);
//        }
//        System.out.println(classInfo);
//        logger.info("fetched all classes info successfully");
//        return ResponseEntity.of(Optional.of(String.valueOf(classInfo)));
//    }
//
//}
