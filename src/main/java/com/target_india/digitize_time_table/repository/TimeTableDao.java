package com.target_india.digitize_time_table.repository;

import com.target_india.digitize_time_table.config.DbConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
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

            String query="select time_table.slot,class_table.class,class_table.section,time_table.location "+
                    "from course_table "+
                    "inner join time_table on course_table.course_id=time_table.course_id "+
                    "inner join class_table on time_table.class_id=class_table.class_id "+
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

            String query="select time_table.week, time_table.slot,class_table.class,class_table.section,time_table.location "+
                    "from course_table "+
                    "inner join time_table on course_table.course_id=time_table.course_id "+
                    "inner join class_table on time_table.class_id=class_table.class_id "+
                    "where course_table.instructor_id="+instructorId;
            statement= connection.createStatement();
            resultSet=statement.executeQuery(query);
        }
        catch(Exception exception){
            logger.error(String.valueOf(exception));
        }
        return resultSet;
    }


    public ResultSet getStudentWeeklyTimeTable(int classId){
        Statement statement;
        ResultSet resultSet=null;
        try{
            String query="select time_table.week,time_table.slot,course_table.course_name,instructor_table.instructor_name,time_table.location "+
                    "from time_table "+
                    "inner join course_table on time_table.course_id=course_table.course_id "+
                    "inner join instructor_table on course_table.instructor_id=instructor_table.instructor_id "+
                    "where time_table.class_id="+classId;
            statement= connection.createStatement();
            resultSet=statement.executeQuery(query);
            System.out.println(resultSet);
        }
        catch(Exception exception){
            logger.error(String.valueOf(exception));
        }
        return resultSet;
    }

    public ResultSet getStudentDayTimeTable(int classId, String day){
        Statement statement;
        ResultSet resultSet=null;
        try{
            String query="select time_table.slot,course_table.course_name,instructor_table.instructor_name,time_table.location "+
                    "from time_table "+
                    "inner join course_table on time_table.course_id=course_table.course_id "+
                    "inner join instructor_table on course_table.instructor_id=instructor_table.instructor_id "+
                    "where time_table.class_id="+classId+" and time_table.week='"+day+"'";
            statement= connection.createStatement();
            resultSet=statement.executeQuery(query);
        }
        catch(Exception exception){
            logger.error(String.valueOf(exception));
        }
        return resultSet;
    }
}
