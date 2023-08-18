//package com.target_india.digitize_time_table.repository;
//
//import com.target_india.digitize_time_table.model.Admin;
//import com.target_india.digitize_time_table.model.Instructor;
//import com.target_india.digitize_time_table.model.Student;
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
//class LoginDaoTest {
//
//
//    AdminDao adminDao;
//    InstructorDao instructorDao;
//    StudentDao studentDao;
//    LoginDao logindao;
//    Student student;
//    Instructor inst;
//    Admin adm;
//
//    Connection conn;
//
//
//    @BeforeEach
//    void setUp() throws SQLException {
//        String dbUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
//        String username = "sa";
//        String password = "sa";
//        conn = DriverManager.getConnection(dbUrl, username, password);
//        adminDao = new AdminDao(conn);
//        studentDao = new StudentDao(conn);
//        instructorDao = new InstructorDao(conn);
//        logindao = new LoginDao(conn);
//
//
//    }
//    @AfterEach
//    void tearDown() throws SQLException {
//        conn.close();
//    }
//
//    @SneakyThrows
//    @Test
//    void testCheckInstructorById() {
//        String createInstructorTable = "Create table instructor_table(instructor_id int primary key, instructor_name text, contact long)";
//        try (PreparedStatement stmt = conn.prepareStatement(createInstructorTable)) {
//            stmt.executeUpdate();
//        }
//
//        inst = new Instructor(1,"Priya","901023457");
//        instructorDao.addInstructor(inst.getInstructorName(),inst.getInstructorContact());
//        String result = String.valueOf(instructorDao.findInstructorByContact(inst.getInstructorContact()));
//        assertThat(result.equals(inst.getInstructorContact())).isTrue();
//    }
//
////    @SneakyThrows
////    @Test
////    void testCheckStudentById() {
////        String createStudentTable = "Create table student_table(student_name text, class_id int, student_id int primary key)";
////        try (PreparedStatement stmt = conn.prepareStatement(createStudentTable)) {
////            stmt.executeUpdate();
////        }
////        System.out.println("1");
////        student = new Student("Bindu",3,72);
////        System.out.println("3");
////        dao.addStudent(student);
////        System.out.println("4");
////        int result = logindao.checkStudentById(student.getStudent_id());
////        assertThat(result==1).isTrue();
////    }
////
////    @SneakyThrows
////    @Test
////    void testCheckAdminById() {
////        String createAdminTable = "Create table admin_table(admin_id int primary key, admin_name text, admin_contact long)";
////        try (PreparedStatement stmt = conn.prepareStatement(createAdminTable)) {
////            stmt.executeUpdate();
////        }
////        adm = new Admin(5,"Sri Ram",723231552);
////        System.out.println("5");
////        dao.addAdmin(adm);
////        System.out.println("6");
////        int result = logindao.checkAdminById(adm.getAdmin_id());
////        assertThat(result==1).isTrue();
////    }
//
//
//}