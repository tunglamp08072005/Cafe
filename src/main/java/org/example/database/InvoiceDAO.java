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

            // Tải tất cả hóa đơn với thông tin cơ bản
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String customerName = resultSet.getString("customer_name");
                double totalAmount = resultSet.getDouble("total_amount"); // Lấy totalAmount từ cơ sở dữ liệu
                boolean paid = resultSet.getBoolean("is_paid");

                // Tạo một đối tượng Invoice tạm thời với orderList trống
                invoices.add(new Invoice(id, customerName, new ArrayList<>(), paid));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tải danh sách món cho từng hóa đơn sau khi vòng lặp kết thúc
        for (Invoice invoice : invoices) {
            invoice.setOrderList(getOrderListForInvoice(invoice.getId()));
        }

        return invoices;
    }

    private double calculateTotalAmountForInvoice(int invoiceId) {
        double total = 0.0;
        String query = "SELECT m.price, i.quantity FROM invoice_items i " +
                "JOIN menu_items m ON i.menu_item_id = m.id " +
                "WHERE i.invoice_id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, invoiceId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    double price = resultSet.getDouble("price");
                    int quantity = resultSet.getInt("quantity");
                    total += price * quantity; // Cộng tổng giá trị món ăn (có tính quantity)
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }


    public List<MenuItem> getOrderListForInvoice(int invoiceId) {
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
        // Kiểm tra danh sách món ăn trước khi thêm hóa đơn
        if (invoice.getOrderList() == null || invoice.getOrderList().isEmpty()) {
            System.out.println("Vui lòng thêm món ăn vào hóa đơn trước khi thêm.");
            return;  // Dừng phương thức nếu danh sách món ăn trống
        }

        String insertInvoiceQuery = "INSERT INTO invoices (id, customer_name, total_amount, is_paid) VALUES (?, ?, ?, ?)";
        double totalAmount = calculateTotalAmount(invoice);

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertInvoiceQuery)) {

            preparedStatement.setInt(1, invoice.getId());
            preparedStatement.setString(2, invoice.getCustomerName());
            preparedStatement.setDouble(3, totalAmount);
            preparedStatement.setBoolean(4, invoice.isPaid());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Hóa đơn đã được thêm thành công.");
                addInvoiceItems(invoice); // Thêm các món vào bảng invoice_items
            } else {
                System.out.println("Không thể thêm hóa đơn.");
            }
        }
    }

    private void addInvoiceItems(Invoice invoice) throws SQLException {
        String insertInvoiceItemsQuery = "INSERT INTO invoice_items (invoice_id, menu_item_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertInvoiceItemsQuery)) {

            // Lặp qua các món trong hóa đơn và thêm chúng vào bảng invoice_items
            for (MenuItem menuItem : invoice.getOrderList()) {
                preparedStatement.setInt(1, invoice.getId());
                preparedStatement.setInt(2, menuItem.getId());
                preparedStatement.setInt(3, 1); // Giả sử mỗi món có quantity = 1
                preparedStatement.addBatch();
            }

            // Thực thi batch chèn các món vào bảng invoice_items
            preparedStatement.executeBatch();
        }
    }

    private double calculateTotalAmount(Invoice invoice) {
        double total = 0.0;
        if (invoice.getOrderList() == null || invoice.getOrderList().isEmpty()) {
            System.out.println("Danh sách món ăn trống!");
        } else {
            for (MenuItem item : invoice.getOrderList()) {
                total += item.getPrice();
            }
        }
        return total;
    }

    public void updateInvoicePayment(int invoiceId, boolean isPaid) throws SQLException {
        String updateQuery = "UPDATE invoices SET is_paid = ? WHERE id = ?";  // Sửa 'paid' thành 'is_paid'

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
        // Xóa các mục trong bảng invoice_items trước
        String deleteItemsQuery = "DELETE FROM invoice_items WHERE invoice_id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteItemsQuery)) {
            preparedStatement.setInt(1, invoiceId);
            preparedStatement.executeUpdate();
        }

        // Sau khi đã xóa các mục, mới xóa hóa đơn
        String deleteQuery = "DELETE FROM invoices WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, invoiceId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Hóa đơn với ID " + invoiceId + " đã được xóa.");
            } else {
                System.out.println("Không tìm thấy hóa đơn với ID " + invoiceId);
            }
        }
    }

    public List<MenuItem> getOrderListByInvoiceId(int invoiceId) throws SQLException {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT m.id, m.name, m.price, i.quantity " +
                "FROM invoice_items i " +
                "JOIN menu_items m ON i.menu_item_id = m.id " +
                "WHERE i.invoice_id = ?";

        // Mở kết nối mới thay vì sử dụng kết nối tĩnh
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, invoiceId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                String description = ""; // hoặc null nếu cần
                boolean isAvailable = true; // giá trị mặc định
                MenuItem menuItem = new MenuItem(id, name, price, description, isAvailable);
                menuItems.add(menuItem);
            }
        }
        return menuItems;
    }

    public List<MenuItem> getMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        // Thực hiện truy vấn SQL để lấy các món ăn từ cơ sở dữ liệu
        try (Connection connection = DatabaseConnector.getConnection()) {  // Thay đổi ở đây
            String query = "SELECT * FROM menu_items";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    menuItems.add(new MenuItem(id, name, price));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

}
