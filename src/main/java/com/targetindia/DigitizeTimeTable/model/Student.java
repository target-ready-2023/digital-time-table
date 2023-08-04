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
public class Student {
    @Id
    private int student_id;
    private String student_name;
    private int class_id;
    private String student_contact;
}
