import java.sql.*;
import java.util.Scanner;

public class ProductCRUDApp {
    static final String URL = "jdbc:mysql://localhost:3306/your_db";
    static final String USER = "your_user";
    static final String PASS = "your_password";

    static Connection conn;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            conn.setAutoCommit(false); // Enable transaction handling

            while (true) {
                System.out.println("\n=== Product CRUD Menu ===");
                System.out.println("1. Create Product");
                System.out.println("2. Read All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Choose option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: createProduct(); break;
                    case 2: readProducts(); break;
                    case 3: updateProduct(); break;
                    case 4: deleteProduct(); break;
                    case 5: conn.close(); System.exit(0);
                    default: System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CREATE
    static void createProduct() {
        try {
            System.out.print("Enter Product ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.print("Enter Product Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Price: ");
            double price = scanner.nextDouble();

            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();

            String sql = "INSERT INTO Product VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setInt(4, quantity);

            ps.executeUpdate();
            conn.commit(); // Commit transaction
            System.out.println("✅ Product added successfully.");
        } catch (Exception e) {
            rollback("❌ Error adding product.");
        }
    }

    // READ
    static void readProducts() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product");

            System.out.println("\n--- Product List ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Price: %.2f | Qty: %d\n",
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"));
            }
        } catch (Exception e) {
            System.out.println("❌ Error reading products.");
        }
    }

    // UPDATE
    static void updateProduct() {
        try {
            System.out.print("Enter Product ID to update: ");
            int id = scanner.nextInt();

            System.out.print("Enter new Price: ");
            double price = scanner.nextDouble();

            System.out.print("Enter new Quantity: ");
            int qty = scanner.nextInt();

            String sql = "UPDATE Product SET Price = ?, Quantity = ? WHERE ProductID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, price);
            ps.setInt(2, qty);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("✅ Product updated.");
            } else {
                System.out.println("❌ Product not found.");
            }
        } catch (Exception e) {
            rollback("❌ Error updating product.");
        }
    }

    // DELETE
    static void deleteProduct() {
        try {
            System.out.print("Enter Product ID to delete: ");
            int id = scanner.nextInt();

            String sql = "DELETE FROM Product WHERE ProductID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("✅ Product deleted.");
            } else {
                System.out.println("❌ Product not found.");
            }
        } catch (Exception e) {
            rollback("❌ Error deleting product.");
        }
    }

    // Transaction rollback helper
    static void rollback(String errorMessage) {
        try {
            conn.rollback();
            System.out.println(errorMessage);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
