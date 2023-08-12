package com.target_india.digitize_time_table.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target_india.digitize_time_table.model.Student;
import com.target_india.digitize_time_table.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentDao studentDao;

    @Autowired
    public StudentService(StudentDao studentDao){
        this.studentDao = studentDao;
    }

    public Optional<String> getAllStudents() {
        try {
            ResultSet resultSet = studentDao.findAllStudents();
            List<Student> students = new ArrayList<Student>();
            while (resultSet.next()) {
                int id = resultSet.getInt("student_id");
                String name = resultSet.getString("student_name");
                int classId = resultSet.getInt("class_id");
                String contact = resultSet.getString("student_contact");
                Student student = new Student(id, name, classId, contact);
                students.add(student);
            }
            if (students.isEmpty()) {
                return Optional.empty();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jacksonData = objectMapper.writeValueAsString(students);
            return Optional.of(jacksonData);
        }
        catch(Exception exception){
            return Optional.empty();
        }
    }

    public Optional<String> getStudentById(int id) {
        try {
            ResultSet resultSet = studentDao.findStudentById(id);
            Student student = new Student();
            if (resultSet.next()) {
                student.setStudentId(resultSet.getInt(1));
                student.setStudentName(resultSet.getString(2));
                student.setClassId(resultSet.getInt(3));
                student.setStudentContact(resultSet.getString(4));
            } else {
                return Optional.empty();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jacksonData = objectMapper.writeValueAsString(student);
            return Optional.of(jacksonData);
        }
        catch(Exception exception){
            return Optional.empty();
        }
    }

    public String addStudent(Student student) {
        studentDao.addStudent(student);
        return "Successfully added student";

    }
    public String updateStudent(Student student){
        return studentDao.updateStudent(student);
    }
    public String updateStudent(int id,String name,int classId, String contact){
        return studentDao.updateStudent(id,name,classId,contact);
    }

    public int deleteStudentById(int id) {
        return studentDao.deleteStudentById(id);
    }


}
