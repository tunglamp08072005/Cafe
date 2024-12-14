package org.example.database;

import org.example.coffeeshop.Invoice;
import org.example.coffeeshop.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Data Access Object for Invoices. */
public class InvoiceDAO {

    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT * FROM invoices"; // Giả sử bạn có bảng invoices

        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String customerName = resultSet.getString("customer_name");
                boolean paid = resultSet.getBoolean("paid");

                // Lấy danh sách các món trong hóa đơn (orderList)
                List<MenuItem> orderList = getOrderListForInvoice(id);

                // Tạo đối tượng Invoice với danh sách MenuItem
                invoices.add(new Invoice(id, customerName, orderList, paid));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoices;
    }

    private List<MenuItem> getOrderListForInvoice(int invoiceId) {
        List<MenuItem> orderList = new ArrayList<>();
        String query = "SELECT m.id, m.name, m.price, m.description, m.is_available " +
                "FROM menu_items m " +
                "JOIN invoice_items i ON m.id = i.menu_item_id " +
                "WHERE i.invoice_id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, invoiceId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int menuItemId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    String description = resultSet.getString("description"); // Lấy mô tả món
                    boolean isAvailable = resultSet.getBoolean("is_available"); // Lấy trạng thái có sẵn

                    // Tạo MenuItem với đầy đủ tham số
                    MenuItem menuItem = new MenuItem(menuItemId, name, price, description, isAvailable);
                    orderList.add(menuItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    public void addInvoice(Invoice invoice) throws SQLException {
        String insertInvoiceQuery = "INSERT INTO invoices (id, customer_name, paid) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertInvoiceQuery)) {

            // Thêm hóa đơn vào bảng invoices
            preparedStatement.setInt(1, invoice.getId());
            preparedStatement.setString(2, invoice.getCustomerName());
            preparedStatement.setBoolean(3, invoice.isPaid());

            int rowsAffected = preparedStatement.executeUpdate();

            // Sau khi thêm hóa đơn vào bảng invoices, thêm các món trong hóa đơn vào bảng invoice_items
            if (rowsAffected > 0) {
                addInvoiceItems(invoice);
            }
        }
    }

    private void addInvoiceItems(Invoice invoice) throws SQLException {
        String insertInvoiceItemsQuery = "INSERT INTO invoice_items (invoice_id, menu_item_id) VALUES (?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertInvoiceItemsQuery)) {

            // Lặp qua các món trong hóa đơn và thêm chúng vào bảng invoice_items
            for (MenuItem menuItem : invoice.getOrderList()) {
                preparedStatement.setInt(1, invoice.getId());
                preparedStatement.setInt(2, menuItem.getId());
                preparedStatement.addBatch();
            }

            // Thực thi batch chèn các món vào bảng invoice_items
            preparedStatement.executeBatch();
        }
    }

    public void updateInvoicePayment(int invoiceId, boolean isPaid) throws SQLException {
        String updateQuery = "UPDATE invoices SET paid = ? WHERE id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            // Thiết lập giá trị cho các tham số trong câu lệnh SQL
            preparedStatement.setBoolean(1, isPaid);
            preparedStatement.setInt(2, invoiceId);

            // Thực thi câu lệnh cập nhật
            int rowsAffected = preparedStatement.executeUpdate();

            // Kiểm tra nếu không có dòng nào bị ảnh hưởng
            if (rowsAffected > 0) {
                System.out.println("Trạng thái thanh toán của hóa đơn đã được cập nhật.");
            } else {
                System.out.println("Không tìm thấy hóa đơn với ID " + invoiceId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Lỗi khi cập nhật trạng thái thanh toán hóa đơn.", e);
        }
    }

    public void deleteInvoice(int invoiceId) throws SQLException {
        String deleteQuery = "DELETE FROM invoices WHERE id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            // Thiết lập giá trị cho tham số trong câu lệnh SQL
            preparedStatement.setInt(1, invoiceId);

            // Thực thi câu lệnh xóa
            int rowsAffected = preparedStatement.executeUpdate();

            // Kiểm tra nếu không có dòng nào bị ảnh hưởng
            if (rowsAffected > 0) {
                System.out.println("Hóa đơn với ID " + invoiceId + " đã được xóa.");
            } else {
                System.out.println("Không tìm thấy hóa đơn với ID " + invoiceId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Lỗi khi xóa hóa đơn.", e);
        }
    }


}
