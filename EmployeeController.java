package controller;

import dao.EmployeeDAO;
import model.Employee;
import java.util.List;

public class EmployeeController {
    private EmployeeDAO dao = new EmployeeDAO();

    public void showAllEmployees() {
        List<Employee> list = dao.getAllEmployees();
        for (Employee e : list) {
            System.out.println(e);
        }
    }
}
