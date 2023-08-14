package com.target_india.digitize_time_table.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target_india.digitize_time_table.exceptions.ResourceAlreadyExistException;
import com.target_india.digitize_time_table.model.Course;
import com.target_india.digitize_time_table.repository.ClassDao;
import com.target_india.digitize_time_table.repository.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseDao courseDao;

    @Autowired
    public CourseService(CourseDao courseDao){
        this.courseDao=courseDao;
    }

    public Optional<String> getCourseByClassId (int classId){
        try {
            ResultSet resultSet = courseDao.findCourseByClassId(classId);
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

    public String addCourse(Course course) {
        courseDao.addCourse(course);
        return "Successfully added course";
    }

    public String updateCourse(int id, String name, int classID, int instructorId) throws ResourceAlreadyExistException{
        return courseDao.updateCourse(id,name,classID,instructorId);
    }

    public String updateCourse(Course course) throws ResourceAlreadyExistException {
        return courseDao.updateCourse(course);
    }

    public String deleteCourseById(int id) {
        return courseDao.deleteCourseById(id);
    }
}
