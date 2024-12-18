package org.example.gui;

import org.example.coffeeshop.Invoice;
import org.example.coffeeshop.MenuItem;
import org.example.database.MenuItemDAO;
import org.example.api.MenuAPI;
import org.example.api.PaymentAPI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** Panel for managing menu items. */
public class MenuPanel extends JPanel {
    private final JTable menuTable;
    private final DefaultTableModel tableModel;
    private List<MenuItem> menuItems;
    private final MenuItemDAO menuItemDAO;
    private final MenuAPI menuAPI; // Khởi tạo đối tượng MenuAPI
    private final PaymentAPI paymentAPI; // Khai báo PaymentAPI

    public MenuPanel() {
        setLayout(new BorderLayout());

        // Initialize DAO và API
        menuItemDAO = new MenuItemDAO();
        menuAPI = new MenuAPI(); // Khởi tạo MenuAPI
        paymentAPI = new PaymentAPI(); // Khởi tạo PaymentAPI

        // Create table model
        String[] columnNames = {"ID", "Tên", "Giá", "Sự miêu tả", "Có sẵn"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Create table
        menuTable = new JTable(tableModel);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(menuTable);

        // Add components
        add(scrollPane, BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        // Load menu items from database
        loadMenuItemsFromDatabase();
    }

    private void loadMenuItemsFromDatabase() {
        menuItems = menuItemDAO.getAllMenuItems();  // Load data from DB
        loadMenuItems();  // Update the table with menu items from DB
    }

    private void loadMenuItems() {
        tableModel.setRowCount(0); // Clear table
        for (MenuItem item : menuItems) {
            tableModel.addRow(new Object[]{
                    item.getId(),
                    item.getName(),
                    (int) item.getPrice(), // Chuyển đổi giá từ double sang int
                    item.getDescription(),
                    item.isAvailable() ? "Có" : "Không"
            });
        }
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Thêm đồ uống");
        JButton editButton = new JButton("Sửa đồ uống");
        JButton deleteButton = new JButton("Xóa đồ uống");
        JButton fetchSpecialButton = new JButton("Nhận các mục menu");
        JButton processPaymentButton = new JButton("Process Payment");

        addButton.addActionListener(e -> addMenuItem());
        editButton.addActionListener(e -> editMenuItem());
        deleteButton.addActionListener(e -> deleteMenuItem());
        fetchSpecialButton.addActionListener(e -> fetchSpecialMenuItems());
        processPaymentButton.addActionListener(e -> processPayment());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(fetchSpecialButton);
        buttonPanel.add(processPaymentButton);

        return buttonPanel;
    }

    private void addMenuItem() {
        String name = JOptionPane.showInputDialog(this, "Nhập tên đồ uống:");
        if (name == null || name.trim().isEmpty()) return;

        String priceStr = JOptionPane.showInputDialog(this, "Nhập giá đồ uống:");
        if (priceStr == null || priceStr.trim().isEmpty()) return;

        double price;
        try {
            price = Double.parseDouble(priceStr); // Convert to double
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Giá không thể âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá nhập không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String description = JOptionPane.showInputDialog(this, "Nhập miêu tả đồ uống:");
        if (description == null || description.trim().isEmpty()) return;

        boolean isAvailable = JOptionPane.showConfirmDialog(this, "Đồ uống này có sẵn không?", "Có sẵn", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

        MenuItem newItem = new MenuItem(0, name, (int) price, description, isAvailable);  // ID = 0, để MySQL tự tạo

        menuItemDAO.addMenuItem(newItem);  // Thêm món vào DB và lấy ID mới
        loadMenuItemsFromDatabase();  // Tải lại menu từ DB và cập nhật bảng
        JOptionPane.showMessageDialog(this, "Đã thêm đồ uống thành công!");
    }

    private void editMenuItem() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow != -1) {
            MenuItem selectedItem = menuItems.get(selectedRow);

            String newName = JOptionPane.showInputDialog(this, "Chỉnh sửa tên đồ uống:", selectedItem.getName());
            if (newName == null || newName.trim().isEmpty()) return;

            String newPriceStr = JOptionPane.showInputDialog(this, "Chỉnh sửa giá đồ uống:", selectedItem.getPrice());
            if (newPriceStr == null || newPriceStr.trim().isEmpty()) return;

            double newPrice;
            try {
                newPrice = Double.parseDouble(newPriceStr); // Chuyển đổi sang số thực
                if (newPrice < 0) {
                    JOptionPane.showMessageDialog(this, "Giá không thể âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá nhập không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String newDescription = JOptionPane.showInputDialog(this, "Chỉnh sửa mô tả  đồ uống:", selectedItem.getDescription());
            if (newDescription == null || newDescription.trim().isEmpty()) return;

            boolean newIsAvailable = JOptionPane.showConfirmDialog(this, "Đồ uống này có sẵn không?", "Có sẵn", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

            selectedItem.setName(newName);
            selectedItem.setPrice((int) newPrice);
            selectedItem.setDescription(newDescription);
            selectedItem.setAvailable(newIsAvailable);

            menuItemDAO.updateMenuItem(selectedItem); // Cập nhật món ăn trong DB
            loadMenuItemsFromDatabase(); // Tải lại menu từ DB và cập nhật bảng
            JOptionPane.showMessageDialog(this, "Đã cập nhật đồ uống thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Không có đồ uống nào được chọn!");
        }
    }

    private void deleteMenuItem() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow != -1) {
            MenuItem selectedItem = menuItems.get(selectedRow);
            int confirmation = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa đồ uống này không?", "Xóa đồ uống", JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                menuItemDAO.deleteMenuItem(selectedItem.getId());
                loadMenuItemsFromDatabase();
                JOptionPane.showMessageDialog(this, "Đã xóa đồ uống thành công!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có đồ uống nào được chọn!");
        }
    }

    private void fetchSpecialMenuItems() {
        List<MenuItem> specialMenuItems = menuAPI.fetchSpecialMenuItems();

        for (MenuItem item : specialMenuItems) {
            // Nếu món không có id thì đặt id mặc định là 0 để MySQL tự động tạo id mới
            item.setId(0);  // Đặt id = 0 để MySQL tự động tăng id
            item.setPrice((int) item.getPrice()); // Chuyển giá từ double sang int
            menuItemDAO.addMenuItem(item);
        }

        // Sau khi thêm, tải lại toàn bộ danh sách từ cơ sở dữ liệu
        loadMenuItemsFromDatabase();
        JOptionPane.showMessageDialog(this, "Đã tải xuống và thêm các mục menu!");
    }

    private void processPayment() {
        int[] selectedRows = menuTable.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "No items selected for payment.");
            return;
        }

        List<MenuItem> selectedItems = new ArrayList<>();
        for (int rowIndex : selectedRows) {
            int itemId = (int) menuTable.getValueAt(rowIndex, 0);
            for (MenuItem item : menuItems) {
                if (item.getId() == itemId) {
                    selectedItems.add(item);
                    break;
                }
            }
        }

        double totalAmount = 0;
        for (MenuItem item : selectedItems) {
            totalAmount += item.getPrice();
        }

        String totalAmountStr = String.format("%.2f", totalAmount);
        Invoice invoice = new Invoice(1, totalAmountStr, selectedItems, false);
        String qrCode = paymentAPI.generateQRCode(invoice);
        boolean paymentStatus = paymentAPI.processPayment(invoice);

        if (paymentStatus) {
            JOptionPane.showMessageDialog(this, "Payment successful. QR Code: " + qrCode);
        } else {
            JOptionPane.showMessageDialog(this, "Payment already processed.");
        }
    }
}
