package org.example.database;

import org.example.coffeeshop.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Data Access Object for Employees. */
public class EmployeeDAO {
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";

        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String position = resultSet.getString("position");
                double salary = resultSet.getDouble("salary");

                // Tạo đối tượng Employee với 4 tham số
                employees.add(new Employee(id, name, position, salary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public void addEmployee(Employee employee) {
        String query = "SELECT MAX(id) FROM employees"; // Lấy ID lớn nhất

        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            int nextId = 1; // Nếu bảng trống, bắt đầu từ 1

            if (resultSet.next()) {
                nextId = resultSet.getInt(1) + 1; // Tăng ID lớn nhất lên 1
            }

            String insertQuery = "INSERT INTO employees (id, name, position, salary) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, nextId); // Sử dụng ID đã tính toán
                insertStatement.setString(2, employee.getName());
                insertStatement.setString(3, employee.getPosition());
                insertStatement.setDouble(4, employee.getSalary());

                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int id) {
        String query = "DELETE FROM employees WHERE id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
