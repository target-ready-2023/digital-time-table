package com.target_india.digitize_time_table.repository;

import com.target_india.digitize_time_table.config.DbConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ClassDao {

    private static final Logger logger = LoggerFactory.getLogger(ClassDao.class);
    Connection connection;

    DbConnection dbConnection;

    ClassDao(){
        dbConnection = new DbConnection();
        connection = dbConnection.getDbConnection();
        logger.info("Opened database successfully");
    }

    ClassDao(Connection connection){
        this.connection = connection;
    }

    public ResultSet findClassInfo(int classId){
        ResultSet resultSet=null;
        String query = "select * from class_table where class_id = ?";
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

    public ResultSet findAllClassInfo(){
        ResultSet resultSet=null;
        String query = "select * from class_table ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        }catch (SQLException exception) {
            logger.error(String.valueOf(exception));
        }
        return resultSet;
    }

}
