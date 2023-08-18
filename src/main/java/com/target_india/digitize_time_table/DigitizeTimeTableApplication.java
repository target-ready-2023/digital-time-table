package com.target_india.digitize_time_table;

import com.target_india.digitize_time_table.config.DbSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DigitizeTimeTableApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitizeTimeTableApplication.class, args);
	}

}
