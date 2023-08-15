package com.target_india.digitize_time_table.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfo {
    @Id
    private int courseId;
    private String courseName;
    private int classId;
    private String instructorName;
}
