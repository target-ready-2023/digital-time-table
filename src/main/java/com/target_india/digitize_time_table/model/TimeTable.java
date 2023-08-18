package com.target_india.digitize_time_table.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeTable {
    private String week;
    private String slot;
    private int classId;
    private int className;
    private String section;
    private int courseId;
    private String courseName;
    private int instructorId;
    private String instructorName;
    private String location;
}
