package com.target_india.digitize_time_table.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target_india.digitize_time_table.model.ClassInfo;
import com.target_india.digitize_time_table.repository.AdminDao;
import com.target_india.digitize_time_table.repository.ClassDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
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
    public Optional<String> getClassInfo(int id) {
        try {
            ResultSet resultSet = classDao.findClassInfo(id);
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
            ResultSet resultSet = classDao.findAllClassInfo();
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

}
