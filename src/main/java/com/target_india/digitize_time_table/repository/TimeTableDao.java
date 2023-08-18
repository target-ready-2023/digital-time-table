package com.target_india.digitize_time_table.repository;

import com.target_india.digitize_time_table.config.DbConnection;
import com.target_india.digitize_time_table.exceptions.ResourceAlreadyExistException;
import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class TimeTableDao {

    private static final Logger logger = LoggerFactory.getLogger(TimeTableDao.class);
    Connection connection;
    DbConnection dbConnection;
    TimeTableDao(){
        dbConnection = new DbConnection() ;
        connection = dbConnection.getDbConnection();
        logger.info("Opened database successfully");
    }

    TimeTableDao(Connection connection){
        this.connection = connection;
    }
    public ResultSet getInstructorDayTimeTable(int instructorId, String week){
        Statement statement;
        ResultSet resultSet=null;
        try{
            String query="select time_table.week, time_table.slot, time_table.class_id,class_table.class,class_table.section," +
                    "time_table.course_id,course_table.course_name, course_table.instructor_id, instructor_table.instructor_name,time_table.location "+
                    "from course_table "+
                    "inner join time_table on course_table.course_id=time_table.course_id "+
                    "inner join class_table on time_table.class_id=class_table.class_id "+
                    "inner join instructor_table on course_table.instructor_id=instructor_table.instructor_id "+
                    "where course_table.instructor_id="+instructorId+" and time_table.week='"+week+"'";
            statement= connection.createStatement();
            resultSet=statement.executeQuery(query);
        }
        catch(Exception exception){
            logger.error(String.valueOf(exception));
        }
        return resultSet;
    }
    public ResultSet getInstructorWeeklyTimeTable(int instructorId) {
        Statement statement;
        ResultSet resultSet=null;
        try{

            String query="select time_table.week,time_table.slot,time_table.class_id,class_table.class,class_table.section," +
                    "time_table.course_id,course_table.course_name, course_table.instructor_id," +
                    "instructor_table.instructor_name,time_table.location from course_table "+
                    "inner join time_table on course_table.course_id=time_table.course_id "+
                    "inner join class_table on time_table.class_id=class_table.class_id "+
                    "inner join instructor_table on course_table.instructor_id=instructor_table.instructor_id "+
                    "where course_table.instructor_id="+instructorId+" order by time_table.week";
            statement= connection.createStatement();
            resultSet=statement.executeQuery(query);
        }
        catch(Exception exception){
            logger.error(exception.getMessage());
        }
        return resultSet;
    }


    public ResultSet getStudentWeeklyTimeTable(int classId){
        Statement statement;
        ResultSet resultSet=null;
        try{
            String query="select time_table.week, time_table.slot, time_table.class_id,class_table.class,class_table.section," +
                    "time_table.course_id,course_table.course_name, course_table.instructor_id, instructor_table.instructor_name," +
                    "time_table.location from time_table "+
                    "inner join class_table on time_table.class_id=class_table.class_id "+
                    "inner join course_table on time_table.course_id=course_table.course_id "+
                    "inner join instructor_table on course_table.instructor_id=instructor_table.instructor_id "+
                    "where time_table.class_id="+classId+" order by time_table.week";
            statement= connection.createStatement();
            resultSet=statement.executeQuery(query);

        }
        catch(Exception exception){
            logger.error(exception.getMessage());
        }
        return resultSet;
    }

    public ResultSet getStudentDayTimeTable(int classId, String day){
        Statement statement;
        ResultSet resultSet=null;
        try{
            String query="select time_table.week, time_table.slot, time_table.class_id,class_table.class,class_table.section," +
                    "time_table.course_id,course_table.course_name, course_table.instructor_id, " +
                    "instructor_table.instructor_name,time_table.location from time_table "+
                    "inner join class_table on time_table.class_id=class_table.class_id "+
                    "inner join course_table on time_table.course_id=course_table.course_id "+
                    "inner join instructor_table on course_table.instructor_id=instructor_table.instructor_id "+
                    "where time_table.class_id="+classId+" and time_table.week='"+day+"'";
            statement= connection.createStatement();
            resultSet=statement.executeQuery(query);
        }
        catch(Exception exception){
            logger.error(exception.getMessage());
        }
        return resultSet;
    }
    public String deleteTimeTableById(int classId,String week,String slot) {
        try {
            String query = "Delete from time_table " +
                    "where class_id = " + classId+" and week='"+week+"' and slot='"+slot+"'";
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return "Successfully freed up the slot";
        }
        catch(SQLException exception){
            throw new ResourceNotFoundException(String.valueOf(exception));
        }
    }
    public String updateTimeTableSlot(int classId,String week,String slot, String subject,String location){
        try {
            ResultSet resultSet3=getcourseId(subject,classId);
            int courseId=0;
            int instructorId=0;
            if(resultSet3.next()){
                courseId=resultSet3.getInt(1);
                instructorId=resultSet3.getInt(2);}
            ResultSet resultSet1=getInstructorAvailability(week,slot,instructorId,classId);
            ResultSet resultSet2=getLocationAvailability(week,slot,location,classId);
            if(resultSet1.next()){
                System.out.println("This instructor is having another class at this time");
                throw new ResourceAlreadyExistException("This instructor is having another class at this time");
            }
            else if(resultSet2.next()){
                System.out.println("This location is occupied at this time");
                throw new ResourceAlreadyExistException("This location is occupied at this time");
            }
            else{
                String query="update time_table " +
                        "set course_id='"+courseId+"', location='"+location+"' " +
                        "where class_id="+classId+" and week='"+week+"' and slot='"+slot+"'";

                Statement statement= connection.createStatement();
                statement.executeUpdate(query);
                return "Successfully updated the slot";}
        }
        catch(SQLException exception){
            throw new ResourceNotFoundException(String.valueOf(exception));
        }
    }
    public ResultSet getcourseId(String subject,int classId){
        try{
            String query = "select course_table.course_id,course_table.instructor_id " +
                    "from course_table " +
                    "where course_table.class_id ="+classId+" and course_name='"+subject+"'";
            Statement statement= connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            return resultSet;
        }
        catch(SQLException exception){
            System.out.println(exception);
            throw new ResourceNotFoundException(String.valueOf(exception));
        }
    }
    public ResultSet getInstructorAvailability(String week,String slot,int instructorId,int classId){
        try{
            String query = "select time_table.class_id " +
                    "from course_table "+
                    "inner join time_table on course_table.course_id=time_table.course_id "+
                    "where time_table.week ='"+week+"' and time_table.class_id!="+classId+" and time_table.slot='"+slot+"' and course_table.instructor_id="+instructorId;
            Statement statement= connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            return resultSet;
        }
        catch(SQLException exception){
            throw new ResourceNotFoundException(String.valueOf(exception));
        }
    }
    public ResultSet getLocationAvailability(String week,String slot, String location,int classId){
        try{
            String query = "select time_table.class_id " +
                    "from time_table " +
                    "where time_table.week ='"+week+"' and time_table.class_id!="+classId+" and slot='"+slot+"' and time_table.location='"+location+"'";
            Statement statement= connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            return resultSet;
        }
        catch(SQLException exception){
            throw new ResourceNotFoundException(String.valueOf(exception));
        }
    }
}