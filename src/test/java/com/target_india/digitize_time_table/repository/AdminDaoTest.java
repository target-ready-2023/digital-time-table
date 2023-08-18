//package com.targetindia.DigitizeTimeTable.repository;
//
//import com.target_india.digitize_time_table.repository.AdminDao;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.sql.*;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@DataJpaTest
//class AdminDaoTest {
//
//    AdminDao dao;
//    Instructor inst;
//    Connection conn;
//
//
//    @BeforeEach
//    void setUp() throws SQLException {
//        String dbUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
//        String username = "sa";
//        String password = "sa";
//        conn = DriverManager.getConnection(dbUrl, username, password);
//        dao = new AdminDao(conn);
//
//        String createInstructorTable = "Create table instructor_table(instructor_id int primary key, instructor_name text, contact long)";
//        try (PreparedStatement stmt = conn.prepareStatement(createInstructorTable)) {
//            stmt.executeUpdate();
//        }
//
//    }
//    @AfterEach
//    void tearDown() throws SQLException {
//        inst=null;
//        conn.close();
//        //dao.deleteAllStudents();
//    }
//
//    @SneakyThrows
//    @Test
//    void findInstructorById() {
//        inst = new Instructor(1,"Priya",901023457);
//        dao.addInstructor(inst);
//        int id = 0;
//        ResultSet rs = dao.findInstructorById(1);
//        if(rs != null){
//            rs.next();
//            id = rs.getInt(1);
//        }
//        assertThat(id == 1).isTrue();
//    }
//}