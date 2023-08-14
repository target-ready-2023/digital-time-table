package com.target_india.digitize_time_table.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import com.target_india.digitize_time_table.model.Instructor;
import com.target_india.digitize_time_table.repository.InstructorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    private final InstructorDao instructorDao;

    @Autowired
    public InstructorService(InstructorDao instructorDao) {
        this.instructorDao = instructorDao;
    }


    public List<Instructor> getAllInstructors() {
        ResultSet resultSet = instructorDao.findAllInstructors();
        List<Instructor> instructors = new ArrayList<Instructor>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("instructor_id");
                String name = resultSet.getString("instructor_name");
                String contact = resultSet.getString("contact");
                Instructor instructor = new Instructor(id, name, contact);
                instructors.add(instructor);
            }
            if(instructors.isEmpty()){
                throw new ResourceNotFoundException("No data found");
            }
            else{
                return instructors;
            }
        }
        catch(SQLException exception){ //sql exception
            throw new ResourceNotFoundException(String.valueOf(exception));
        }
    }

    public Instructor getInstructorById(int id) {
        ResultSet resultSet = instructorDao.findInstructorById(id);
        Instructor instructor = new Instructor();
        try {
            if (resultSet.next()) {
                instructor.setInstructorId(resultSet.getInt(1));
                instructor.setInstructorName(resultSet.getString(2));
                instructor.setInstructorContact(resultSet.getString(3));
                return instructor;
            }
            throw new ResourceNotFoundException( "No instructor found with id: "+id );


        } catch (SQLException exception) {
            throw new ResourceNotFoundException(String.valueOf(exception));
        }
    }

    public String addInstructor(Instructor instructor) {
        instructorDao.addInstructor(instructor);
        String result = "Successfully added instructor";
        return result;
    }
    public String updateInstructor(Instructor instructor){
        return instructorDao.updateInstructor(instructor);
    }
    public String updateInstructor(int id,String name,String contact){
        return instructorDao.updateInstructor(id,name,contact);
    }

    public String deleteInstructorById(int id) {
        return instructorDao.deleteInstructorById(id);
    }


}
