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
public class Instructor {
    @Id
    private int instructorId;

    private String instructorName;
    private String instructorContact;
}
