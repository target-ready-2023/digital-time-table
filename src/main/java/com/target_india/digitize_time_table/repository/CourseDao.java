package com.target_india.digitize_time_table.repository;

import com.target_india.digitize_time_table.config.DbConnection;
import com.target_india.digitize_time_table.exceptions.ResourceAlreadyExistException;
import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import com.target_india.digitize_time_table.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class CourseDao {
    private static final Logger logger = LoggerFactory.getLogger(CourseDao.class);
    Connection connection;

    DbConnection dbConnection;

    CourseDao(){
        dbConnection = new DbConnection();
        connection = dbConnection.getDbConnection();
        logger.info("Opened database successfully");
    }

    CourseDao(Connection connection){
        this.connection = connection;
    }

    public ResultSet findCourseByClassId(int classId){
        ResultSet resultSet=null;
        String query = "select course_table.course_id,course_table.course_name,instructor_table.instructor_name " +
                "from course_table " +
                "inner join instructor_table on course_table.instructor_id = instructor_table.instructor_id " +
                "where course_table.class_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, classId);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        }catch (SQLException exception) {
            logger.error(String.valueOf(exception));
        }
        return resultSet;
    }

    public ResultSet findAllCourses() {
        ResultSet resultSet = null;
        String query = "select course_table.course_id, course_table.course_name, " +
                "class_table.class, class_table.section, instructor_table.instructor_name from course_table " +
                "inner join instructor_table on course_table.instructor_id = instructor_table.instructor_id " +
                "inner join class_table on course_table.class_id = class_table.class_id";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        }catch (SQLException exception) {
            logger.error(String.valueOf(exception));
        }
        return resultSet;
    }


    public ResultSet findCourseById(int id) {
        ResultSet resultSet = null;
        String query = "select * from course_table where course_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        }catch (SQLException exception) {
            logger.error(String.valueOf(exception));
        }
        return resultSet;
    }

    public void addCourse(Course course) {
        String query = "insert into course_table(course_name,class_id,instructor_id) values(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,course.getCourseId());
            preparedStatement.setString(2,course.getCourseName());
            preparedStatement.setInt(3,course.getClassId());
            preparedStatement.setInt(4,course.getInstructorId());
            preparedStatement.executeUpdate();
        }
        catch(SQLException exception){
            logger.error(String.valueOf(exception));
        }
    }

    public String updateCourse(int id, String name, int classId, int instructorId) {
        Statement statement;
        ResultSet resultSet = findCourseById(id);
        try{
            if(resultSet.next()){
                String query="update course_table " +
                        "set course_name='"+name+"', class_id='"+classId+"', instructor_id="+instructorId+" " +
                        "where course_id="+id+" ";
                statement= connection.createStatement();
                statement.executeUpdate(query);
                return "Successfully updated course data whose id = "+ id;
            }
            else{
                throw new ResourceNotFoundException("No course found with ID"+id+"to update");
            }

        }
        catch(Exception exception) {
            throw new ResourceNotFoundException(String.valueOf(exception));
        }
    }

    public String updateCourse(Course course) {
        PreparedStatement preparedStatement;
        try(ResultSet resultSet = findCourseById(course.getCourseId())){
            if(resultSet.next()){
                String query="update course_table " +
                        "set course_name=?, class_id = ?, instructor_id=? " +
                        "where course_id=?";
                preparedStatement= connection.prepareStatement(query);
                preparedStatement.setString(1,course.getCourseName());
                preparedStatement.setInt(2,course.getClassId());
                preparedStatement.setInt(3,course.getInstructorId());
                preparedStatement.setInt(4,course.getCourseId());
                preparedStatement.executeUpdate(query);
                return "Successfully updated student data whose id = "+ course.getCourseId();
            }
            else{
                throw new ResourceNotFoundException("No course found with ID"+course.getCourseId()+"to update");
            }

        }
        catch(SQLException exception){
            throw new ResourceNotFoundException(String.valueOf(exception));
        }
    }

    public String deleteCourseById(int id) {
        ResultSet resultset = findCourseById(id);
        try {
            if (resultset.next()) {
                String query = "Delete from course_table where course_id = " + id;
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                return "Successfully deleted course data whose id = " + id;
            }
            throw new ResourceNotFoundException("No course found with ID" + id + "to delete");
        }
        catch (Exception exception){
            throw new ResourceNotFoundException(String.valueOf(exception));
        }
    }
}
