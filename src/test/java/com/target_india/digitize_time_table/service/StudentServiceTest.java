package com.target_india.digitize_time_table.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import com.target_india.digitize_time_table.model.Student;
import com.target_india.digitize_time_table.repository.StudentDao;
import com.target_india.digitize_time_table.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class StudentServiceTest {

    @Mock
    private StudentDao studentDaoMock;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentService = new StudentService(studentDaoMock);
    }

    @Test
    void testGetAllStudents() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true, false);
        when(resultSetMock.getInt("student_id")).thenReturn(1);
        when(resultSetMock.getString("student_name")).thenReturn("John Doe");
        when(resultSetMock.getInt("class_id")).thenReturn(1);
        when(resultSetMock.getString("student_contact")).thenReturn("1234567890");
        when(studentDaoMock.findAllStudents()).thenReturn(resultSetMock);

        List<Student> students = studentService.getAllStudents();

        assertNotNull(students);
        assertEquals(1, students.size());

        Student student = students.get(0);
        assertEquals(1, student.getStudentId());
        assertEquals("John Doe", student.getStudentName());
        assertEquals(1, student.getClassId());
        assertEquals("1234567890", student.getStudentContact());
    }

    @Test
    void testGetAllStudents_NoStudentsFound() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(false);
        when(studentDaoMock.findAllStudents()).thenReturn(resultSetMock);

        assertThrows(ResourceNotFoundException.class, () -> studentService.getAllStudents());
    }

    @Test
    void testGetStudentById() throws SQLException {
        int id = 1;
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getInt(1)).thenReturn(id);
        when(resultSetMock.getString(2)).thenReturn("John Doe");
        when(resultSetMock.getInt(3)).thenReturn(1);
        when(resultSetMock.getString(4)).thenReturn("1234567890");
        when(studentDaoMock.findStudentById(id)).thenReturn(resultSetMock);

        Student student = studentService.getStudentById(id);

        assertNotNull(student);
        assertEquals(id, student.getStudentId());
        assertEquals("John Doe", student.getStudentName());
        assertEquals(1, student.getClassId());
        assertEquals("1234567890", student.getStudentContact());
    }

    @Test
    void testGetStudentById_InvalidId() throws SQLException {
        int id = 1;
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(false);
        when(studentDaoMock.findStudentById(id)).thenReturn(resultSetMock);

        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById(id));
    }

    @Test
    void testAddStudent() {
        String name = "Alice";
        int id = 1;
        String contact = "9876543210";
        doNothing().when(studentDaoMock).addStudent(name, id, contact);

        String result = studentService.addStudent(name, id, contact);

        assertEquals("Successfully added student", result);
    }

    @Test
    void testUpdateStudent_WithObject() {
        Student student = new Student(1, "Alice", 1, "9876543210");
        when(studentDaoMock.updateStudent(student)).thenReturn("Successfully updated student");

        String result = studentService.updateStudent(student);

        assertEquals("Successfully updated student", result);
    }

    @Test
    void testUpdateStudent_WithParams() {
        int id = 1;
        String name = "Alice";
        int classId = 1;
        String contact = "9876543210";
        when(studentDaoMock.updateStudent(id, name, classId, contact)).thenReturn("Successfully updated student");

        String result = studentService.updateStudent(id, name, classId, contact);

        assertEquals("Successfully updated student", result);
    }

    @Test
    void testDeleteStudentById() {
        int id = 1;
        when(studentDaoMock.deleteStudentById(id)).thenReturn("Successfully deleted student");

        String result = studentService.deleteStudentById(id);

        assertEquals("Successfully deleted student", result);
    }
}
//
//
//public class StudentServiceTest {
//}
