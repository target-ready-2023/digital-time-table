package com.target_india.digitize_time_table.repository;

import com.target_india.digitize_time_table.config.DbConnection;
import com.target_india.digitize_time_table.model.Instructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class InstructorDao {

    private static final Logger logger = LoggerFactory.getLogger(InstructorDao.class);
    Connection connection;
    DbConnection dbConnection;

    InstructorDao() {
        dbConnection = new DbConnection();
        connection = dbConnection.getDbConnection();
        System.out.println("Opened database successfully");
    }

    InstructorDao(Connection connection) {
        this.connection = connection;
    }

    public ResultSet findInstructorById(int id){
        ResultSet resultSet = null;
        try {
            String query = "select * from instructor_table where instructor_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        }catch (SQLException exception) {
            logger.error(String.valueOf(exception));
        }
        return resultSet;
    }
    public ResultSet findAllInstructors() {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM Instructor_table";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException exception) {
            logger.error(String.valueOf(exception));
        }
        return resultSet;
    }


    public void addInstructor(Instructor instructor) {

        try {
            String query = "insert into instructor_table(instructor_id,instructor_name,contact) values(?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,instructor.getInstructorId());
            preparedStatement.setString(3,instructor.getInstructorContact());
            preparedStatement.setString(2,instructor.getInstructorName());
            preparedStatement.executeUpdate();
        }
        catch(SQLException exception){
            logger.error(String.valueOf(exception));
        }
    }

    public String updateInstructor(int id, String name, String contact){
        Statement statement;
        try{
            ResultSet resultSet = findInstructorById(id);
            if(resultSet.next()){
                String query="update instructor_table " +
                        "set instructor_name='"+name+"', contact="+contact+" " +
                        "where instructor_id="+id+" ";
                statement= connection.createStatement();
                statement.executeUpdate(query);
                return "Successfully updated instructor data whose id = "+ id;
            }
            else{
                return null;
            }

        }
        catch(Exception exception){
            logger.error(String.valueOf(exception));
        }
        return null;
    }

    public String updateInstructor(Instructor instructor){
        PreparedStatement preparedStatement;
        try{
            ResultSet resultset = findInstructorById(instructor.getInstructorId());
            if(resultset.next()){
                String query="update instructor_table " +
                        "set instructor_name=?, contact=? " +
                        "where instructor_id=?";
                preparedStatement= connection.prepareStatement(query);
                preparedStatement.setString(1,instructor.getInstructorName());
                preparedStatement.setString(2,instructor.getInstructorContact());
                preparedStatement.setInt(3,instructor.getInstructorId());
                preparedStatement.executeUpdate(query);
                return "Successfully updated instructor data whose id = "+ instructor.getInstructorId();
            }
            else{
                return null;
            }

        }
        catch(Exception exception){
            logger.error(String.valueOf(exception));
        }
        return null;
    }
    public void replaceInstructor(Connection connection,int id, String name, long contact){
        Statement statement;
        ResultSet resultSet=null;
        try{
            String selectQuery="select instructor_name,contact " +
                    "from instructor_table " +
                    "where instructor_id="+id+" ";
            statement=connection.createStatement();
            resultSet=statement.executeQuery(selectQuery);
            resultSet.next();
            String name1 = resultSet.getString("instructor_name");
            long  contact1 =resultSet.getLong("contact");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String insertQuery=String.format("insert into delete_table(instructor_id,instructor_name,contact,deleted_time) values ('%d','%s','%d','%tF')",id,name1,contact1,currentTimestamp);
            statement.executeUpdate(insertQuery);
            String query="update instructor_table " +
                    "set instructor_name='"+name+"', contact="+contact+" " +
                    "where instructor_id="+id+" ";
            statement.executeUpdate(query);
        }
        catch(Exception exception){
            logger.error(String.valueOf(exception));
        }
    }
    public int deleteInstructorById(int id) {
        try {
            String query = "Delete from instructor_table where instructor_id = " + id;

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return 1;
        }
        catch (Exception exception){
            return 0;
        }

    }


}
