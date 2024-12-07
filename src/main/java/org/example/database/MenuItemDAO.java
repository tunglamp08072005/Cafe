package org.example.database;

import org.example.coffeeshop.MenuItem;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

    public void addMenuItem(MenuItem menuItem) {
        String query = "INSERT INTO MenuItems (name, type, price, description, availability) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, menuItem.getName());
            preparedStatement.setString(2, menuItem.getType());
            preparedStatement.setDouble(3, menuItem.getPrice());
            preparedStatement.setString(4, menuItem.getDescription());
            preparedStatement.setBoolean(5, menuItem.isAvailable());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMenuItem(MenuItem menuItem) {
        String query = "UPDATE MenuItems SET name = ?, type = ?, price = ?, description = ?, availability = ? WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, menuItem.getName());
                preparedStatement.setString(2, menuItem.getType());
                preparedStatement.setDouble(3, menuItem.getPrice());
                preparedStatement.setString(4, menuItem.getDescription());
                preparedStatement.setBoolean(5, menuItem.isAvailable());
                preparedStatement.setInt(6, menuItem.getId());
                preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMenuItem(int id) {
        String query = "DELETE FROM MenuItems WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        String query = "SELECT * FROM MenuItems";
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                MenuItem item = new MenuItem(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("type"),
                        resultSet.getDouble("price"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("availability")
                );
                menuItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

}
