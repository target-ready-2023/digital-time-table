package com.targetindia.DigitizeTimeTable.repository;
import java.sql.*;
import java.util.List;


import com.targetindia.DigitizeTimeTable.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class AdminDao{

    Connection conn;

    ConnectionDao connectionDao;
    AdminDao(){
        connectionDao = new ConnectionDao();
        conn = connectionDao.getDBConnection();
        System.out.println("Opened database successfully");
    }

    AdminDao(Connection conn){
        this.conn = conn;
    }

    public ResultSet findInstructorById(int id){
        ResultSet result = null;
        try {
            String query = "select * from instructor_table where instructor_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            result = statement.executeQuery();
            return result;
        }catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    public ResultSet findAllInstructors() {
        ResultSet resultset;
        try {
            String query = "SELECT * FROM Instructor_table";
            Statement statement = conn.createStatement();
            resultset = statement.executeQuery(query);
            return resultset;
        } catch (SQLException e) {
            throw new RuntimeException(String.format("No instructor available"));
        }
    }


    public int deleteInstructorById(int id) {
        try {
            String query = "Delete from instructor_table where instructor_id = " + id;

            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            return 1;
        }
        catch (Exception e){
            return 0;
        }

    }

    public void addInstructor(Instructor newInstr) {

        try {
            String query = "insert into instructor_table(instructor_id,instructor_name,contact) values(?,?,?)";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,newInstr.getInstructor_id());
            statement.setString(3,newInstr.getInstructor_contact());
            statement.setString(2,newInstr.getInstructor_name());
            statement.executeQuery();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public String updateInstructor(int id, String name, long contact){
        Statement statement;
        try{
            ResultSet resultset = findInstructorById(id);
            if(resultset.next()){
                String query="update instructor_table " +
                        "set instructor_name='"+name+"', contact="+contact+" " +
                        "where instructor_id="+id+" ";
                statement=conn.createStatement();
                statement.executeQuery(query);
                return "Successfully updated instructor data whose id = "+ id;
            }
            else{
                return null;
            }

        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public String updateInstructor(Instructor instr){
        PreparedStatement statement;
        try{
            ResultSet resultset = findInstructorById(instr.getInstructor_id());
            if(resultset.next()){
                String query="update instructor_table " +
                        "set instructor_name=?, contact=? " +
                        "where instructor_id=?";
                statement=conn.prepareStatement(query);
                statement.setString(1,instr.getInstructor_name());
                statement.setString(2,instr.getInstructor_contact());
                statement.setInt(3,instr.getInstructor_id());
                statement.executeQuery(query);
                return "Successfully updated instructor data whose id = "+ instr.getInstructor_id();
            }
            else{
                return null;
            }

        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    public void replaceInstructor(Connection con,int id, String name, long contact){
        Statement statement;
        ResultSet resultset=null;
        try{
            String query1="select instructor_name,contact " +
                    "from instructor_table " +
                    "where instructor_id="+id+" ";
            statement=con.createStatement();
            resultset=statement.executeQuery(query1);
            resultset.next();
            String n=resultset.getString("instructor_name");
            String l=resultset.getString("contact");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            String query2=String.format("insert into delete_table(instructor_id,instructor_name,contact,deleted_time) values ('%d','%s','%d','%tF')",id,n,l,currentTimestamp);
            statement.executeUpdate(query2);
            String query="update instructor_table " +
                    "set instructor_name='"+name+"', contact="+contact+" " +
                    "where instructor_id="+id+" ";
            statement.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public ResultSet findCourseByClassId(int class_id){
        ResultSet resultset=null;
        try {
            String query = "select course_table.course_id,course_table.course_name,instructor_table.instructor_name " +
                    "from course_table " +
                    "inner join instructor_table on course_table.instructor_id = instructor_table.instructor_id " +
                    "where course_table.class_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, class_id);
            resultset = statement.executeQuery();
            return resultset;
        }catch (SQLException e) {
            System.out.println(e);
        }
        return resultset;
    }

    public ResultSet findClassInfo(int id){
        ResultSet resultset=null;
        try {
            String query = "select * from class_table where class_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            resultset = statement.executeQuery();
            return resultset;
        }catch (SQLException e) {
            System.out.println(e);
        }
        return resultset;
    }

    public ResultSet findAllClassInfo(){
        ResultSet result;
        try {
            String query = "select * from class_table ";
            PreparedStatement statement = conn.prepareStatement(query);
            result = statement.executeQuery();
            return result;
        }catch (SQLException e) {
            throw new RuntimeException(String.format("No classes found"));
        }
    }
}


//    public void addAllInstructors(List<Instructor> instructors){
//        int count = instructors.size();
//
//        for(int i=0; i<count; i++){
//            addInstructor(instructors.get(i));
//        }
//    }

//    public void updateInstructor(Instructor instr){
//        try{
//
//            findInstructorById(instr.getInstructor_id());
//            String query = "update instructor_table set contact=?, instructor_name=? where instructor_id=?";
//            PreparedStatement preparedStmt = conn.prepareStatement(query);
//            preparedStmt.setLong(1, instr.getInstructor_contact());
//            preparedStmt.setString(2, instr.getInstructor_name());
//            preparedStmt.setInt(3, instr.getInstructor_id());
//            preparedStmt.executeUpdate();
//
//        }
//        catch(SQLException e) {
//            throw new RuntimeException("Update Failed!");
//        }
//    }

// slot ||intsruct_name(instr table)||   location,course_name(course table)



