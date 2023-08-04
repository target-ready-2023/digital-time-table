package com.targetindia.DigitizeTimeTable.repository;

import com.targetindia.DigitizeTimeTable.model.Instructor;
import com.targetindia.DigitizeTimeTable.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class LoginDao {
    Connection conn;
    ConnectionDao connectionDao;
    LoginDao(){
        connectionDao = new ConnectionDao();
        conn = connectionDao.getDBConnection();
        System.out.println("Opened database successfully");
    }

    LoginDao(Connection conn){
        this.conn = conn;
    }
    public int checkStudentById(int id) {
        try{
            String query = "select count(*) from student_table where student_id=?";

            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setInt(1,id);
            ResultSet resultset = stmnt.executeQuery();
            resultset.next();
            return resultset.getInt(1);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }
    public int checkInstructorById(int id) {
        try{
            String query = "select count(*) from instructor_table where instructor_id=?";
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setInt(1,id);
            ResultSet resultset = stmnt.executeQuery();
            resultset.next();
            return resultset.getInt(1);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }
    public int checkAdminById(int id) {
        try{
            String query = "select count(*) from admin_table where admin_id=?";
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setInt(1,id);
            ResultSet resultset = stmnt.executeQuery();
            resultset.next();
            return resultset.getInt(1);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }

}
