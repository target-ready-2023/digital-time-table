package com.target_india.digitize_time_table.model;

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
    private int courseId;
    private String courseName;
    private int classId;
    private int instructorId;
}
