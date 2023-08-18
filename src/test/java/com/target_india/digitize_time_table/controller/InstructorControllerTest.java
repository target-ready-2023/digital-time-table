package com.target_india.digitize_time_table.controller;

import com.target_india.digitize_time_table.model.Instructor;
import com.target_india.digitize_time_table.service.InstructorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {InstructorControllerTest.class})
public class InstructorControllerTest {

    @Mock
    private InstructorService instructorService;

    @InjectMocks
    private InstructorController instructorController;

    @Test
    public void testGetAllInstructors() {
        Instructor instructor1 = new Instructor(1, "John Doe", "1234567890");
        Instructor instructor2 = new Instructor(2, "Jane Smith", "9876543210");
        List<Instructor> instructors = new ArrayList<>();
        instructors.add(instructor1);
        instructors.add(instructor2);

        when(instructorService.getAllInstructors()).thenReturn(instructors);

        ResponseEntity<List<Instructor>> response = instructorController.getAllInstructors();

        List<Instructor> responseInstructors = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(2, responseInstructors.size());
        assertEquals(instructor1.getInstructorId(), responseInstructors.get(0).getInstructorId());
        assertEquals(instructor1.getInstructorName(), responseInstructors.get(0).getInstructorName());
        assertEquals(instructor1.getInstructorContact(), responseInstructors.get(0).getInstructorContact());

        assertEquals(instructor2.getInstructorId(), responseInstructors.get(1).getInstructorId());
        assertEquals(instructor2.getInstructorName(), responseInstructors.get(1).getInstructorName());
        assertEquals(instructor2.getInstructorContact(), responseInstructors.get(1).getInstructorContact());
    }

    @Test
    public void testGetInstructorById() {
        int instructorId = 1;
        Instructor instructor = new Instructor(instructorId, "John Doe", "1234567890");

        when(instructorService.getInstructorById(instructorId)).thenReturn(instructor);

        ResponseEntity<Instructor> response = instructorController.getInstructorById(instructorId);

        Instructor responseInstructor = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(instructor.getInstructorId(), responseInstructor.getInstructorId());
        assertEquals(instructor.getInstructorName(), responseInstructor.getInstructorName());
        assertEquals(instructor.getInstructorContact(), responseInstructor.getInstructorContact());
    }

    @Test
    public void testAddInstructor() {
        String name = "John Doe";
        String contact = "1234567890";

        when(instructorService.addInstructor(name,contact)).thenReturn("Instructor added successfully");

        ResponseEntity<String> response = instructorController.addInstructor(name,contact);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(response.getBody(),"Instructor added successfully");
    }

    @Test
    public void testUpdateInstructor() {
        int instructorId = 1;
        String newName = "Anand";
        String newContact = "9876543210";

        when(instructorService.updateInstructor(instructorId, newName, newContact)).thenReturn("Successfully updated instructor data whose id = "+instructorId);

        ResponseEntity<String> response = instructorController.updateInstructor(instructorId, newName, newContact);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully updated instructor data whose id = 1", response.getBody());
    }

    @Test
    public void testDeleteInstructor() {
        int instructorId = 1;

        when(instructorService.deleteInstructorById(instructorId)).thenReturn("Successfully deleted instructor data whose id = "+instructorId);

        ResponseEntity<String> response = instructorController.deleteInstructor(instructorId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully deleted instructor data whose id = 1", response.getBody());
    }
}
