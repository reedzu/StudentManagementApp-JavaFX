package com.studentmanager.studentmanagementgui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import io.github.cdimascio.dotenv.Dotenv;

public class StudentManager {
    private Connection connection;

    public StudentManager() {
        Dotenv dotenv = Dotenv.load();
        String dbUrl = dotenv.get("DB_URL");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");

        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            System.out.println("System: Securely connected to Supabase Database!\n");
        } catch (SQLException e) {
            System.out.println("System: Database connection failed. Check your .env file and internet connection.");
            e.printStackTrace();
        }
    }

    // UPDATED: Now returns 'boolean' (true/false) instead of 'void'
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (student_id, name, program, year_level) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getProgram());
            pstmt.setInt(4, student.getYearLevel());

            pstmt.executeUpdate();
            System.out.println("System: Successfully saved " + student.getName() + " to the cloud database.");
            return true; // Tells the UI it was a success

        } catch (SQLException e) {
            System.out.println("System: Failed to save student. Likely a duplicate ID.");
            e.printStackTrace();
            return false; // Tells the UI it failed
        }
    }

    public void displayAllStudents() {
        String sql = "SELECT * FROM students";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Current Enrolled Students ---");
            boolean hasStudents = false;

            while (rs.next()) {
                hasStudents = true;
                String id = rs.getString("student_id");
                String name = rs.getString("name");
                String program = rs.getString("program");
                int year = rs.getInt("year_level");

                System.out.println("[" + id + "] " + name + " | " + program + " - Year " + year);
            }

            if (!hasStudents) {
                System.out.println("System: No students enrolled yet.");
            }
            System.out.println("---------------------------------\n");

        } catch (SQLException e) {
            System.out.println("System: Failed to retrieve students.");
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudentsForGUI() {
        List<Student> guiList = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("student_id");
                String name = rs.getString("name");
                String program = rs.getString("program");
                int year = rs.getInt("year_level");

                guiList.add(new Student(id, name, program, year));
            }
        } catch (SQLException e) {
            System.out.println("System: GUI failed to retrieve students.");
            e.printStackTrace();
        }
        return guiList;
    }
}