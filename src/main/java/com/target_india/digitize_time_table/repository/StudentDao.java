package com.target_india.digitize_time_table.repository;

import com.target_india.digitize_time_table.config.DbConnection;

import com.target_india.digitize_time_table.exceptions.ResourceAlreadyExistException;
import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import com.target_india.digitize_time_table.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class StudentDao {

    private static final Logger logger = LoggerFactory.getLogger(StudentDao.class);
    Connection connection;

    DbConnection dbConnection;
    StudentDao(){
        dbConnection = new DbConnection() ;
        connection = dbConnection.getDbConnection();
        System.out.println("Opened database successfully");
    }

    StudentDao(Connection connection){
        this.connection = connection;
    }

    public ResultSet findStudentById(int id) {
        ResultSet resultSet = null;
        String query = "select * from student_table where student_id = ?";
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
    public ResultSet findStudentByContact(String contact) {
        ResultSet resultSet = null;
        String query = "select * from student_table where student_contact = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, contact);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        }catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        return resultSet;
    }

    public ResultSet findAllStudents() {
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM student_table";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        return resultSet;
    }


    public void addStudent(String name, int id, String contact) {
        String query = "insert into student_table(student_name,class_id,student_contact) values(?,?,?)";

        try(ResultSet rs = findStudentByContact(contact)) {
            if(rs.next()){
                throw new ResourceAlreadyExistException("This contact already exists!");
            }
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,id);
            preparedStatement.setString(3,contact);
            preparedStatement.executeUpdate();
        }
        catch(SQLException exception){
            logger.error(exception.getMessage());
        }
    }
    public String updateStudent(Student student) {
        PreparedStatement preparedStatement;

        try(ResultSet resultset = findStudentById(student.getStudentId())){
            if(resultset.next()){
                String query="update student_table " +
                        "set student_name=?, class_id = ?, student_contact=? " +
                        "where student_id=?";
                preparedStatement= connection.prepareStatement(query);
                preparedStatement.setString(1,student.getStudentName());
                preparedStatement.setInt(2,student.getClassId());
                preparedStatement.setString(3,student.getStudentContact());
                preparedStatement.setInt(4,student.getStudentId());
                preparedStatement.executeUpdate(query);
                return "Successfully updated student data whose id = "+ student.getStudentId();
            }
            else{
                throw new ResourceNotFoundException("No student found with ID"+student.getStudentId()+"to update");
            }

        }
        catch(Exception exception){
            logger.error(exception.getMessage());
        }
        return null;
    }



    public String updateStudent(int id, String name, int classId, String contact) {
        Statement statement;
        try(ResultSet resultSet = findStudentById(id)){
            if(resultSet.next()){
                ResultSet rs = findStudentByContact(contact);
                if(rs.next() && rs.getInt("student_id") != id){
                    throw new ResourceAlreadyExistException("This contact already exists!");
                }
                String query="update student_table " +
                        "set student_name='"+name+"', class_id='"+classId+"', student_contact="+contact+" " +
                        "where student_id="+id+" ";
                statement= connection.createStatement();
                statement.executeUpdate(query);
                return "Successfully updated student data whose id = "+ id;
            }
            throw new ResourceNotFoundException("No student found with ID"+id+"to update");
        }
        catch(SQLException exception){
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }

    public String deleteStudentById(int id) {
        try(ResultSet resultset = findStudentById(id)) {
            if (resultset.next()) {
                String query = "Delete from student_table where student_id = " + id;
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                return "Successfully deleted student data whose id = " + id;
            }
            throw new ResourceNotFoundException("No student found with ID"+id+"to delete");
        }
        catch (SQLException exception){
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
}
