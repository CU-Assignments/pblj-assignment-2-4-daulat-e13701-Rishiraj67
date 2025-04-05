package dao;

import model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/your_db_name";
    private static final String USER = "your_username";
    private static final String PASS = "your_password";

    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            String query = "SELECT * FROM Employee";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                list.add(new Employee(id, name, salary));
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
