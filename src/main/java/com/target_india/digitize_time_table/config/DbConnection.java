package com.target_india.digitize_time_table.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;


@Component
@Slf4j
public class DbConnection {
    Connection connection;


    public Connection getDbConnection() {
        String url ="jdbc:postgresql://localhost:5432/DigitizeTimeTable";
        String username = "postgres";
        String password = "postgres@123";


        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception exception) {
            exception.printStackTrace();
            log.warn(exception.getClass().getName()+": "+exception.getMessage());
            System.exit(0);
        }
        return connection;
    }

}
