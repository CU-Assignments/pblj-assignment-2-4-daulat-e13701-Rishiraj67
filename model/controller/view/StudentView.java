package view;

import controller.StudentController;
import model.Student;

import java.util.List;
import java.util.Scanner;

public class StudentView {
    StudentController controller = new StudentController();
    Scanner sc = new Scanner(System.in);

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Student Management ===");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Marks");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewStudents(); break;
                case 3: updateMarks(); break;
                case 4: deleteStudent(); break;
                case 5: return;
                default: System.out.println("❌ Invalid choice.");
            }
        }
    }

    private void addStudent() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        Student s = new Student(id, name, dept, marks);
        boolean result = controller.addStudent(s);
        System.out.println(result ? "✅ Student added." : "❌ Failed to add.");
    }

    private void viewStudents() {
        List<Student> list = controller.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("⚠️ No students found.");
        } else {
            System.out.println("\n--- Students ---");
            for (Student s : list) {
                System.out.println(s);
            }
        }
    }

    private void updateMarks() {
        System.out.print("Enter Student ID to update: ");
        int id = sc.nextInt();
        System.out.print("Enter new Marks: ");
        double marks = sc.nextDouble();

        boolean result = controller.updateStudentMarks(id, marks);
        System.out.println(result ? "✅ Updated." : "❌ Not found.");
    }

    private void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        int id = sc.nextInt();

        boolean result = controller.deleteStudent(id);
        System.out.println(result ? "✅ Deleted." : "❌ Not found.");
    }
}
