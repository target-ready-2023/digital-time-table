package com.target_india.digitize_time_table.repository;

import com.target_india.digitize_time_table.config.DbConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class LoginDao {
    private static final Logger logger = LoggerFactory.getLogger(LoginDao.class);
    Connection connection;
    DbConnection dbConnection;

    private final StudentDao studentDao;
    private final InstructorDao instructorDao;
    private final AdminDao adminDao;

    @Autowired
    LoginDao(StudentDao studentDao, InstructorDao instructorDao, AdminDao adminDao){
        this.studentDao = studentDao;
        this.instructorDao=instructorDao;
        this.adminDao=adminDao;

        dbConnection = new DbConnection();
        connection = dbConnection.getDbConnection();
        logger.info("Opened database successfully");
    }

    public int checkStudentById(int id) {
        try{
            ResultSet resultSet = studentDao.findStudentById(id);
            if(resultSet.next()){
                return 1;
            }
            else{
                return 0;
            }
        }
        catch(Exception exception){
            logger.error(String.valueOf(exception));
        }
        return 0;
    }
    public int checkInstructorById(int id) {
        try{
            ResultSet resultSet = instructorDao.findInstructorById(id);
            if(resultSet.next()){
                return 1;
            }
            else{
                return 0;
            }
        }
        catch(Exception exception){
            logger.error(String.valueOf(exception));
        }
        return 0;
    }
    public int checkAdminById(int id) {
        try{
            ResultSet resultSet = adminDao.findAdminById(id);
            if(resultSet.next()){
                return 1;
            }
            else{
                return 0;
            }
        }
        catch(Exception exception){
            logger.error(String.valueOf(exception));
        }
        return 0;
    }

}
