package com.targetindia.DigitizeTimeTable.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClassInfo {
    @Id
    private int classId;
    private String section;
    private int classNumber;
    private int numberOfStudents;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }


    public String getSection(){return section;}
    public void setSection(String section){this.section=section;}
    public int getClassNumber(){return classNumber;}
    public void setClassNumber(int classNumber){this.classNumber=classNumber;}
    public void setNumberOfStudents(int numberOfStudents){this.numberOfStudents=numberOfStudents;}
    public int getNumberOfStudents(){return numberOfStudents;}

}