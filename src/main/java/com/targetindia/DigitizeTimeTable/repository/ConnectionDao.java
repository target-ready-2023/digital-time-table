package com.targetindia.DigitizeTimeTable.repository;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionDao {


    Connection conn = null;

    String url="jdbc:postgresql://localhost:5433/DigitizeTimeTable";
    String username="postgres";
    String password="postgres@123";

    public Connection getDBConnection() {

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return conn;
    }

}
