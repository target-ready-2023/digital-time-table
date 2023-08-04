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
public class Course {
    @Id
    private int course_id;
    private String course_name;
    private int instructor_id;
}
// i dont have the full access you need to generate the getter and setter there too