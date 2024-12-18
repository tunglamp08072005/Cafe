package org.example.database;

import org.example.coffeeshop.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Data Access Object for Menu Items. */
public class MenuItemDAO {
    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        String query = "SELECT * FROM menu_items ORDER BY id ASC"; // Sắp xếp theo id tăng dần

        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                boolean available = resultSet.getBoolean("is_available");

                menuItems.add(new MenuItem(id, name, (int) price, description, available));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItems;
    }

    public void addMenuItem(MenuItem menuItem) {
        String query = "INSERT INTO menu_items (name, price, description, is_available) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, menuItem.getName());
            statement.setDouble(2, menuItem.getPrice());
            statement.setString(3, menuItem.getDescription());
            statement.setBoolean(4, menuItem.isAvailable());

            statement.executeUpdate();

            // Lấy ID được tạo tự động từ MySQL
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    menuItem.setId(generatedId);  // Cập nhật ID vào MenuItem
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMenuItem(MenuItem menuItem) {
        String query = "UPDATE menu_items SET name = ?, price = ?, description = ?, is_available = ? WHERE id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, menuItem.getName());
            statement.setDouble(2, menuItem.getPrice());
            statement.setString(3, menuItem.getDescription());
            statement.setBoolean(4, menuItem.isAvailable());
            statement.setInt(5, menuItem.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMenuItem(int id) {
        String query = "DELETE FROM menu_items WHERE id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
