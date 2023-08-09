package com.target_india.digitize_time_table.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target_india.digitize_time_table.config.DbSettings;
import com.target_india.digitize_time_table.model.Admin;
import com.target_india.digitize_time_table.model.ClassInfo;
import com.target_india.digitize_time_table.model.Student;
import com.target_india.digitize_time_table.repository.AdminDao;
import com.target_india.digitize_time_table.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.*;


@Service
public class AdminService {

    private final AdminDao adminDao;

    @Autowired
    public AdminService(AdminDao adminDao){
        this.adminDao=adminDao;
    }


    public Optional<String> getClassInfo(int id) {
        try {
            ResultSet resultSet = adminDao.findClassInfo(id);
            ClassInfo classInfo = new ClassInfo();
            if (resultSet.next()) {
                classInfo.setClassId(resultSet.getInt(1));
                classInfo.setClassNumber(resultSet.getInt(2));
                classInfo.setSection(resultSet.getString(3));
                classInfo.setNumberOfStudents(resultSet.getInt(4));
            } else {
                return Optional.empty();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jacksonData = objectMapper.writeValueAsString(classInfo);
            return Optional.of(jacksonData);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }


    public Optional<String> getAllClassInfo () {
        try {
            ResultSet resultSet = adminDao.findAllClassInfo();
            List<ClassInfo> classes_info = new ArrayList<ClassInfo>();
            while (resultSet.next()) {
                int id = resultSet.getInt("class_id");
                int classNumber = resultSet.getInt("class");
                String section = resultSet.getString("section");
                int numberOfStudents = resultSet.getInt("number_of_students");
                ClassInfo classInfo = new ClassInfo(id, section, classNumber, numberOfStudents);
                classes_info.add(classInfo);
            }
            if (classes_info.isEmpty()) {
                return Optional.empty();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jacksonData = objectMapper.writeValueAsString(classes_info);
            return Optional.of(jacksonData);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }


    public Optional<String> getCourseByClassId ( int classId){
        try {
            ResultSet resultSet = adminDao.findCourseByClassId(classId);
            List<List<String>> courses = new ArrayList<>();
            while (resultSet.next()) {
                List<String> course = new ArrayList<>();
                String courseId = String.valueOf(resultSet.getInt("course_id"));
                String name = resultSet.getString("course_name");
                String instructorName = resultSet.getString("instructor_name");
                course.add(courseId);
                course.add(name);
                course.add(instructorName);
                courses.add(course);
            }
            if (courses.isEmpty()) {
                return Optional.empty();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jacksonData = objectMapper.writeValueAsString(courses);
            return Optional.of(jacksonData);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    public Optional<String> getAllAdmins() {
        try {
            ResultSet resultSet = adminDao.findAllAdmins();
            List<Admin> admins = new ArrayList<Admin>();
            while (resultSet.next()) {
                int id = resultSet.getInt("admin_id");
                String name = resultSet.getString("admin_name");
                String contact = resultSet.getString("admin_contact");
                Admin admin = new Admin(id, name, contact);
                admins.add(admin);
            }
            if (admins.isEmpty()) {
                return Optional.empty();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jacksonData = objectMapper.writeValueAsString(admins);
            return Optional.of(jacksonData);
        }
        catch(Exception exception){
            return Optional.empty();
        }
    }

    public Optional<String> getAdminById(int id) {
        try {
            ResultSet resultSet = adminDao.findAdminById(id);
            Admin admin = new Admin();
            if (resultSet.next()) {
                admin.setAdminId(resultSet.getInt(1));
                admin.setAdminName(resultSet.getString(2));
                admin.setAdminContact(resultSet.getString(3));
            } else {
                return Optional.empty();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jacksonData = objectMapper.writeValueAsString(admin);
            return Optional.of(jacksonData);
        }
        catch(Exception exception){
            return Optional.empty();
        }
    }
}