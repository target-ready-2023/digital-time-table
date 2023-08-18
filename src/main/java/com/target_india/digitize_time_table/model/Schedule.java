package com.target_india.digitize_time_table.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
@Data
public class Schedule {
    @Id
    private int classId;
    private String week;
    private String slot;
    private int courseId;
    private int instructorId;


}
