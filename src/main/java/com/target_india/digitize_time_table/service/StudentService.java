package com.target_india.digitize_time_table.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import com.target_india.digitize_time_table.model.Student;
import com.target_india.digitize_time_table.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public List<Student> getAllStudents() {
        ResultSet resultSet = studentDao.findAllStudents();
        List<Student> students = new ArrayList<Student>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("student_id");
                String name = resultSet.getString("student_name");
                int classId = resultSet.getInt("class_id");
                String contact = resultSet.getString("student_contact");
                Student student = new Student(id, name, classId, contact);
                students.add(student);
            }
            if (students.isEmpty()) {
                throw new ResourceNotFoundException("No data found");
            }
            return students;
        }
        catch(SQLException exception){
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }

    public Student getStudentById(int id) {
        ResultSet resultSet = studentDao.findStudentById(id);
        Student student = new Student();
        try {
            if (resultSet.next()) {
                student.setStudentId(resultSet.getInt(1));
                student.setStudentName(resultSet.getString(2));
                student.setClassId(resultSet.getInt(3));
                student.setStudentContact(resultSet.getString(4));
                return student;
            }
            throw new ResourceNotFoundException( "No student found with id: "+id );
        }
        catch(SQLException exception){
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }

    public String addStudent(String name, int id, String contact) {
        studentDao.addStudent(name, id, contact);
        return "Successfully added student";

    }
    public String updateStudent(Student student){
        return studentDao.updateStudent(student);
    }
    public String updateStudent(int id,String name,int classId, String contact){
        return studentDao.updateStudent(id,name,classId,contact);
    }

    public String deleteStudentById(int id) {
        return studentDao.deleteStudentById(id);
    }


}
