package com.target_india.digitize_time_table.repository;

import com.target_india.digitize_time_table.config.DbConnection;
import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
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
        ResultSet resultSet = studentDao.findStudentById(id);
        try{
            if(resultSet.next()){
                return 1;
            }
            else{
                return 0;
            }

        }
        catch(SQLException exception){
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
    public int checkInstructorById(int id) {
        ResultSet resultSet = instructorDao.findInstructorById(id);
        try{
            if(resultSet.next()){
                return 1;
            }
            else{
                return 0;
            }
        }
        catch(SQLException exception){
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
    public int checkAdminById(int id) {
        ResultSet resultSet = adminDao.findAdminById(id);
        try{
            if(resultSet.next()){
                return 1;
            }
            else{
                return 0;
            }
        }
        catch(SQLException exception){
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
}
