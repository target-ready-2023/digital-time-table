package com.target_india.digitize_time_table.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import com.target_india.digitize_time_table.model.ClassInfo;
import com.target_india.digitize_time_table.repository.AdminDao;
import com.target_india.digitize_time_table.repository.ClassDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    private final ClassDao classDao;

    @Autowired
    public ClassService(ClassDao classDao){
        this.classDao=classDao;
    }
    public ClassInfo getClassInfo(int id) {
        try {
            ResultSet resultSet = classDao.findClassInfo(id);
            ClassInfo classInfo = new ClassInfo();
            if (resultSet.next()) {
                classInfo.setClassId(resultSet.getInt(1));
                classInfo.setClassName(resultSet.getInt(2));
                classInfo.setClassSection(resultSet.getString(3));
                classInfo.setClassStrength(resultSet.getInt(4));

                return classInfo;
            }
            throw new ResourceNotFoundException( "No class found with id: "+id );
        } catch (SQLException exception) {
            throw new ResourceNotFoundException(String.valueOf(exception));
        }
    }


    public List<ClassInfo> getAllClassInfo () {
        ResultSet resultSet = classDao.findAllClassInfo();
        List<ClassInfo> classes_info = new ArrayList<ClassInfo>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("class_id");
                int className = resultSet.getInt("class");
                String section = resultSet.getString("section");
                int numberOfStudents = resultSet.getInt("number_of_students");
                ClassInfo classInfo = new ClassInfo(id, className, section, numberOfStudents);
                classes_info.add(classInfo);
            }
            if (classes_info.isEmpty()) {
                throw new ResourceNotFoundException("No data found");
            }
            return classes_info;
        } catch (SQLException exception) {
            throw new ResourceNotFoundException(String.valueOf(exception));
        }
    }

}
