package com.target_india.digitize_time_table.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target_india.digitize_time_table.exceptions.ResourceAlreadyExistException;
import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import com.target_india.digitize_time_table.model.Course;
import com.target_india.digitize_time_table.model.CourseInfo;
import com.target_india.digitize_time_table.model.Student;
import com.target_india.digitize_time_table.repository.ClassDao;
import com.target_india.digitize_time_table.repository.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public List<CourseInfo> getCourseByClassId (int classId){
        ResultSet resultSet;
        resultSet = courseDao.findCourseByClassId(classId);
        List<CourseInfo> courses = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int courseId = resultSet.getInt("course_id");
                String name = resultSet.getString("course_name");
                String instructorName = resultSet.getString("instructor_name");
                CourseInfo course = new CourseInfo(courseId,name,classId,instructorName);
                courses.add(course);
            }
            if (courses.isEmpty()) {
                throw new ResourceNotFoundException("No data found");
            }
            return courses;
        } catch (SQLException exception) {
            throw new ResourceNotFoundException(String.valueOf(exception));
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

    public List<CourseInfo> getAllCourses() {
        ResultSet resultSet = courseDao.findAllCourses();
        List<CourseInfo> courseInfo = new ArrayList<CourseInfo>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("course_id");
                String name = resultSet.getString("course_name");
                int classId = resultSet.getInt("class_id");
                String instructorName = resultSet.getString("instructor_name");
                CourseInfo course = new CourseInfo(id, name, classId, instructorName);
                courseInfo.add(course);
            }
            if (courseInfo.isEmpty()) {
                throw new ResourceNotFoundException("No data found");
            }
            return courseInfo;
        }
        catch(SQLException exception){
            throw new ResourceNotFoundException(String.valueOf(exception));
        }
    }
}
