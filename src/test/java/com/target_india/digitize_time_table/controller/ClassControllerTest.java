package com.target_india.digitize_time_table.controller;

import com.target_india.digitize_time_table.model.ClassInfo;
import com.target_india.digitize_time_table.service.ClassService;
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

@SpringBootTest(classes = {ClassControllerTest.class})
public class ClassControllerTest {

    @Mock
    private ClassService classService;

    @InjectMocks
    private ClassController classController;

    @Test
    public void testGetAllClassInfo() throws Exception {
        ClassInfo class1 = new ClassInfo(1, 10, "A", 30);
        ClassInfo class2 = new ClassInfo(2, 10, "B", 25);
        List<ClassInfo> classes = new ArrayList<>();
        classes.add(class1);
        classes.add(class2);

        when(classService.getAllClassInfo()).thenReturn(classes);

        ResponseEntity<List<ClassInfo>> response = classController.getAllClassInfo();

        List<ClassInfo> responseClasses = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(2, responseClasses.size());
        assertEquals(class1.getClassId(), responseClasses.get(0).getClassId());
        assertEquals(class1.getClassName(), responseClasses.get(0).getClassName());
        assertEquals(class1.getClassSection(), responseClasses.get(0).getClassSection());
        assertEquals(class1.getClassStrength(), responseClasses.get(0).getClassStrength());

        assertEquals(class2.getClassId(), responseClasses.get(1).getClassId());
        assertEquals(class2.getClassName(), responseClasses.get(1).getClassName());
        assertEquals(class2.getClassSection(), responseClasses.get(1).getClassSection());
        assertEquals(class2.getClassStrength(), responseClasses.get(1).getClassStrength());
    }

    @Test
    public void testGetClassById() {
        int classId = 1;
        ClassInfo classInfo = new ClassInfo(classId, 10, "A", 30);

        when(classService.getClassById(classId)).thenReturn(classInfo);

        ResponseEntity<ClassInfo> response = classController.getClassById(classId);

        ClassInfo responseClass = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(classInfo.getClassId(), responseClass.getClassId());
        assertEquals(classInfo.getClassName(), responseClass.getClassName());
        assertEquals(classInfo.getClassSection(), responseClass.getClassSection());
        assertEquals(classInfo.getClassStrength(), responseClass.getClassStrength());
    }

    @Test
    public void testAddClass() {
        int className = 1;
        String section = "A";
        int strength = 35;
        when(classService.addClass(className,section,strength)).thenReturn("Successfully added class");

        ResponseEntity<String> response = classController.addClass(1, "A",30);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    public void testUpdateClassStrength() {
        int classId = 1;
        int newStrength = 35;

        when(classService.updateClassStrength(classId,newStrength)).thenReturn("Successfully updated class data whose id = "+classId);

        ResponseEntity<String> response = classController.updateClassStrength(classId, newStrength);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(),"Successfully updated class data whose id = 1");
    }

    @Test
    public void testDeleteClassById() {
        int classId = 1;

        when(classService.deleteClassById(classId)).thenReturn("Successfully deleted class data whose id = " + classId);

        ResponseEntity<String> response = classController.deleteClassById(classId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(),"Successfully deleted class data whose id = 1");
    }
}
