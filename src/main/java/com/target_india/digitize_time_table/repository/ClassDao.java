package com.target_india.digitize_time_table.repository;

import com.target_india.digitize_time_table.config.DbConnection;
import com.target_india.digitize_time_table.exceptions.ResourceAlreadyExistException;
import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;

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
            logger.error(exception.getMessage());
        }
        return resultSet;
    }

    public ResultSet findClassById(int id) {
        ResultSet resultSet = null;
        String query = "select * from class_table where class_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        }catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        return resultSet;
    }
    public ResultSet findAllClassInfo(){
        ResultSet resultSet=null;
        String query = "select * from class_table order by (class,section) ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        }catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        return resultSet;
    }

    public void addClass(int className, String section, int strength) {
        String query = "insert into class_table(class,section,number_of_students) values(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,className);
            preparedStatement.setString(2,section);
            preparedStatement.setInt(3,strength);
            preparedStatement.executeUpdate();
        }
        catch(SQLException exception){
            logger.error(exception.getMessage());
        }
    }

    public String updateClassStrength(int classId, int strength) {
        Statement statement;
        ResultSet resultSet = findClassById(classId);
        try{
            if(resultSet.next()){
                String query="update class_table " +
                        "set number_of_students="+strength+" " +
                        "where class_id="+classId;
                statement= connection.createStatement();
                statement.executeUpdate(query);
                return "Successfully updated class data whose id = "+ classId;
            }
            throw new ResourceNotFoundException("No class found with ID"+classId+"to update");

        }
        catch(SQLException exception){
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
    public String deleteClassById(int id) {
        ResultSet resultset = findClassById(id);
        try {
            if (resultset.next()) {
                String query = "Delete from class_table where class_id = " + id;
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                return "Successfully deleted class data whose id = " + id;
            }
            throw new ResourceNotFoundException("No class found with ID"+id+"to delete");
        }
        catch (SQLException exception){
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
}
