package com.target_india.digitize_time_table.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.target_india.digitize_time_table.exceptions.ResourceNotFoundException;
import com.target_india.digitize_time_table.model.ClassInfo;
import com.target_india.digitize_time_table.repository.ClassDao;
import com.target_india.digitize_time_table.service.ClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ClassServiceTest {

    @Mock
    private ClassDao classDaoMock;

    private ClassService classService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        classService = new ClassService(classDaoMock);
    }

    @Test
    void testGetClassInfo_ValidId() throws SQLException {
        int id = 1;
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getInt(1)).thenReturn(id);
        when(resultSetMock.getInt(2)).thenReturn(10);
        when(resultSetMock.getString(3)).thenReturn("A");
        when(resultSetMock.getInt(4)).thenReturn(30);
        when(classDaoMock.findClassInfo(id)).thenReturn(resultSetMock);

        ClassInfo classInfo = classService.getClassInfo(id);

        assertNotNull(classInfo);
        assertEquals(id, classInfo.getClassId());
        assertEquals(10, classInfo.getClassName());
        assertEquals("A", classInfo.getClassSection());
        assertEquals(30, classInfo.getClassStrength());
    }

    @Test
    void testGetClassInfo_InvalidId() throws SQLException {
        int id = 1;
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(false);
        when(classDaoMock.findClassInfo(id)).thenReturn(resultSetMock);

        assertThrows(ResourceNotFoundException.class, () -> classService.getClassInfo(id));
    }

    @Test
    void testGetAllClassInfo() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true, false);
        when(resultSetMock.getInt("class_id")).thenReturn(1);
        when(resultSetMock.getInt("class")).thenReturn(10);
        when(resultSetMock.getString("section")).thenReturn("A");
        when(resultSetMock.getInt("number_of_students")).thenReturn(30);
        when(classDaoMock.findAllClassInfo()).thenReturn(resultSetMock);

        List<ClassInfo> classInfoList = classService.getAllClassInfo();

        assertNotNull(classInfoList);
        assertEquals(1, classInfoList.size());

        ClassInfo classInfo = classInfoList.get(0);
        assertEquals(1, classInfo.getClassId());
        assertEquals(10, classInfo.getClassName());
        assertEquals("A", classInfo.getClassSection());
        assertEquals(30, classInfo.getClassStrength());
    }

    @Test
    void testGetAllClassInfo_EmptyResultSet() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(false);
        when(classDaoMock.findAllClassInfo()).thenReturn(resultSetMock);

        assertThrows(ResourceNotFoundException.class, () -> classService.getAllClassInfo());
    }
}

//public class ClassServiceTest {
//}
