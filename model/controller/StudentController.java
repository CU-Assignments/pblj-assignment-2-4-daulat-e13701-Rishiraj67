package controller;

import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    private static final String URL = "jdbc:mysql://localhost:3306/your_db";
    private static final String USER = "your_user";
    private static final String PASS = "your_pass";

    private Connection conn;

    public StudentController() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addStudent(Student s) {
        try {
            String sql = "INSERT INTO Student VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, s.getStudentId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getDepartment());
            ps.setDouble(4, s.getMarks());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
            return false;
        }
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Student");

            while (rs.next()) {
                list.add(new Student(
                    rs.getInt("StudentID"),
                    rs.getString("Name"),
                    rs.getString("Department"),
                    rs.getDouble("Marks")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStudentMarks(int studentId, double newMarks) {
        try {
            String sql = "UPDATE Student SET Marks = ? WHERE StudentID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, newMarks);
            ps.setInt(2, studentId);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteStudent(int studentId) {
        try {
            String sql = "DELETE FROM Student WHERE StudentID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
            return false;
        }
    }
}
