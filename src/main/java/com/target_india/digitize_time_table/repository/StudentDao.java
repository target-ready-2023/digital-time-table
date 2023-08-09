package com.target_india.digitize_time_table.repository;

import com.target_india.digitize_time_table.config.DbConnection;

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
        try {
            String query = "select * from student_table where student_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        }catch (SQLException exception) {
            logger.error(String.valueOf(exception));
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
            logger.error(String.valueOf(exception));
        }
        return resultSet;
    }


    public void addStudent(Student student) {
        try {
            String query = "insert into student_table(student_id,student_name,class_id,student_contact) values(?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,student.getStudentId());
            preparedStatement.setString(2,student.getStudentName());
            preparedStatement.setInt(3,student.getClassId());
            preparedStatement.setString(4,student.getStudentContact());
            preparedStatement.executeUpdate();
        }
        catch(SQLException exception){
            logger.error(String.valueOf(exception));
        }
    }
    public String updateStudent(Student student) {
        PreparedStatement preparedStatement;
        try{
            ResultSet resultset = findStudentById(student.getStudentId());
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
                return null;
            }

        }
        catch(Exception exception){
            logger.error(String.valueOf(exception));
        }
        return null;
    }


    public String updateStudent(int id, String name, int classId, String contact) {
        Statement statement;
        try{
            ResultSet resultSet = findStudentById(id);
            if(resultSet.next()){
                String query="update student_table " +
                        "set student_name='"+name+"', class_id='"+classId+"', student_contact="+contact+" " +
                        "where student_id="+id+" ";
                statement= connection.createStatement();
                statement.executeUpdate(query);
                return "Successfully updated student data whose id = "+ id;
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

    public int deleteStudentById(int id) {
        try {
            String query = "Delete from student_table where student_id = " + id;

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return 1;
        }
        catch (Exception exception){
            return 0;
        }
    }

}
