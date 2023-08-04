package com.targetindia.DigitizeTimeTable.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.targetindia.DigitizeTimeTable.DbSettings;
import com.targetindia.DigitizeTimeTable.model.ClassInfo;
import com.targetindia.DigitizeTimeTable.repository.AdminDao;
import com.targetindia.DigitizeTimeTable.model.Instructor;
import com.targetindia.DigitizeTimeTable.model.Course;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.*;


@Service
public class AdminService {

    @Autowired
    DbSettings dbSettings;

    @Autowired
    AdminDao dao;



    public String updateInstructor(Instructor instr){
        return dao.updateInstructor(instr);
    }
    public String updateInstructor(int id,String name,long contact){
        return dao.updateInstructor(id,name,contact);
    }
    @SneakyThrows
    public String getAllInstructors() {
        ResultSet resultset = dao.findAllInstructors();
        List<Instructor> instructors = new ArrayList<Instructor>();
        while (resultset.next()) {
            int id = resultset.getInt("instructor_id");
            String name = resultset.getString("instructor_name");
            String contact = resultset.getString("contact");
            Instructor instructor = new Instructor(id,name, contact);
            instructors.add(instructor);
        }
        if(instructors.isEmpty()){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jacksonData = objectMapper.writeValueAsString(instructors);
        return jacksonData;
    }
    @SneakyThrows
    public String getInstructorById(int id) {
        ResultSet resultset = dao.findInstructorById(id);
        Instructor instructor = new Instructor();
        if(resultset.next()){
            instructor.setInstructor_id(resultset.getInt(1));
            instructor.setInstructor_name(resultset.getString(2));
            instructor.setInstructor_contact(resultset.getString(3));
        }
        else{
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jacksonData = objectMapper.writeValueAsString(instructor);
        return jacksonData;
    }

    public int deleteInstructorById(int id) {
        return dao.deleteInstructorById(id);
    }

    public String addInstructor(Instructor instructor) {
        dao.addInstructor(instructor);
        String str = "Successfully added instructor";
        return str;

    }
    @SneakyThrows
    public String getClassInfo(int id) {
        ResultSet resultset = dao.findClassInfo(id);
        ClassInfo classInfo = new ClassInfo();
        if(resultset.next()){
            classInfo.setClassId(resultset.getInt(1));
            classInfo.setClassNumber(resultset.getInt(2));
            classInfo.setSection(resultset.getString(3));
            classInfo.setNumberOfStudents(resultset.getInt(4));
        }
        else{
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jacksonData = objectMapper.writeValueAsString(classInfo);
        return jacksonData;
    }


    @SneakyThrows
    public String getAllClassInfo() {
        ResultSet resultset = dao.findAllClassInfo();
        List<ClassInfo> classes_info = new ArrayList<ClassInfo>();
        while (resultset.next()) {
            int id = resultset.getInt("class_id");
            int classNumber = resultset.getInt("class");
            String section = resultset.getString("section");
            int numberOfStudents = resultset.getInt("number_of_students");
            ClassInfo classInfo = new ClassInfo(id, section, classNumber, numberOfStudents);
            classes_info.add(classInfo);
        }
        if (classes_info.isEmpty()) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jacksonData = objectMapper.writeValueAsString(classes_info);
        return jacksonData;
    }

    @SneakyThrows
    public String getCourseByClassId(int class_id){
        ResultSet resultset =dao.findCourseByClassId(class_id);
        List<List<String>> courses = new ArrayList<>();
        while (resultset.next()) {
            List<String> course = new ArrayList<>();
            String id = String.valueOf(resultset.getInt("course_id"));
            String name = resultset.getString("course_name");
            String instructorName = resultset.getString("instructor_name");
            course.add(id);
            course.add(name);
            course.add(instructorName);
            courses.add(course);
        }
        if (courses.isEmpty()) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jacksonData = objectMapper.writeValueAsString(courses);
        return jacksonData;
    }

}

//    public void addAllInstructors(List<Instructor> instructor){
//        dao.addAllInstructors(instructor);
//    }

//    public void updateInstructor(int id,String name,long contact){
//        dao.updateInstructor(id,name,contact);
//    }


//    public String updatePeriodInfo(int classId, String dow, String slot) {
//        dao.updatePeriodInfo(classId,dow,slot);
//        return "The subject info of "+dow+" "+slot+" time is updated.";
//    }
//    public String check_availability_of_instructor(int id,String slot){
//        ResultSet result=dao.check_availability_of_instructor(id,slot);
//        if (!result.next()) {
//
//        } else {
//            // Process the ResultSet and retrieve data from columns
//        }
//    }

