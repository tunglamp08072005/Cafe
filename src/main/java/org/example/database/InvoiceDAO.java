package org.example.database;

import org.example.coffeeshop.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    public void addInvoice(Invoice invoice) {
        String query = "INSERT INTO Invoices (customerName, totalAmount, status) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, invoice.getCustomerName());
            preparedStatement.setDouble(2, invoice.getTotalAmount());
            preparedStatement.setBoolean(3, invoice.isPaid());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    invoice.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInvoiceStatus(int id, boolean status) {
        String query = "UPDATE Invoices SET status = ? WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, status);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT * FROM Invoices";
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Invoice invoice = new Invoice(
                        resultSet.getInt("id"),
                        resultSet.getString("customerName"),
                        resultSet.getDouble("totalAmount"),
                        resultSet.getBoolean("status")
                );
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    public Invoice getInvoiceById(int id) {
        String query = "SELECT * FROM Invoices WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Invoice(
                            resultSet.getInt("id"),
                            resultSet.getString("customerName"),
                            null,
                            resultSet.getDouble("totalAmount"),
                            resultSet.getBoolean("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
