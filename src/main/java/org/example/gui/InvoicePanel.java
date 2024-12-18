package org.example.gui;

import org.example.database.InvoiceDAO;
import org.example.coffeeshop.MenuItem;
import org.example.coffeeshop.Invoice;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoicePanel extends JPanel {
    private final InvoiceDAO invoiceDAO;
    private final JTextField idField;
    private final JTextField customerNameField;
    private final JTextField totalAmountField;
    private final JTextArea invoiceListArea;
    private JComboBox<MenuItem> menuItemComboBox;

    public InvoicePanel() {
        invoiceDAO = new InvoiceDAO();  // Tạo đối tượng InvoiceDAO để quản lý hóa đơn
        setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2)); // Thêm một dòng cho JComboBox

        inputPanel.add(new JLabel("ID Hóa Đơn:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Tên Khách Hàng:"));
        customerNameField = new JTextField();
        inputPanel.add(customerNameField);

        inputPanel.add(new JLabel("Tổng Số Tiền:"));
        totalAmountField = new JTextField();
        inputPanel.add(totalAmountField);

        inputPanel.add(new JLabel("Chọn Đồ Uống:"));
        menuItemComboBox = new JComboBox<>(new DefaultComboBoxModel<>(getMenuItems().toArray(new MenuItem[0])));
        inputPanel.add(menuItemComboBox);

        // Các nút hành động
        JPanel buttonPanel = getjPanel();

        // Khu vực hiển thị danh sách hóa đơn
        invoiceListArea = new JTextArea(10, 40);
        invoiceListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(invoiceListArea);

        // Thêm các thành phần vào JPanel chính
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private List<MenuItem> getMenuItems() {
        // Lấy các món ăn từ database hoặc nguồn dữ liệu
        return invoiceDAO.getMenuItems();  // Ví dụ: lấy danh sách món ăn từ cơ sở dữ liệu
    }

    private JPanel getjPanel() {
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Thêm Hóa Đơn");
        JButton listButton = new JButton("Hiển Thị Danh Sách Hóa Đơn");
        JButton updateButton = new JButton("Cập Nhật Trạng Thái Thanh Toán");
        JButton deleteButton = new JButton("Xóa Hóa Đơn");

        addButton.addActionListener(e -> addInvoice());
        listButton.addActionListener(e -> listInvoices());
        updateButton.addActionListener(e -> updateInvoicePayment());
        deleteButton.addActionListener(e -> deleteInvoice());

        buttonPanel.add(addButton);
        buttonPanel.add(listButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        return buttonPanel;
    }

    private boolean isNumeric(String str, boolean isInteger) {
        // Kiểm tra nếu chuỗi là số nguyên hoặc số thực hợp lệ
        if (str == null || str.isEmpty()) {
            return false;
        }

        if (isInteger) {
            // Kiểm tra ID là số nguyên
            return str.matches("\\d+");
        } else {
            // Kiểm tra Tổng số tiền là số thực hợp lệ (dấu chấm thay vì dấu phẩy)
            return str.matches("\\d+\\.\\d+|\\d+");
        }
    }

    private void addInvoice() {
        String idText = idField.getText();
        String totalAmountText = totalAmountField.getText();

        try {
            // Kiểm tra các trường nhập liệu
            if (idText.isEmpty() || totalAmountText.isEmpty() || customerNameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            // Kiểm tra định dạng số
            if (!isNumeric(idText, true) || !isNumeric(totalAmountText, false)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho ID và Tổng số tiền.");
                return;
            }

            int id = Integer.parseInt(idText);
            int totalAmount = 0; // Sửa kiểu thành int

            // Lấy danh sách món ăn từ UI và tính tổng tiền
            List<MenuItem> orderList = getOrderListFromUI();
            if (orderList == null || orderList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng thêm đồ uống vào hóa đơn trước khi thêm.");
                return;
            }

            for (MenuItem item : orderList) {
                totalAmount += item.getPrice(); // Cộng giá của mỗi món ăn vào tổng tiền
            }

            // Tạo đối tượng hóa đơn và thêm vào database
            Invoice invoice = new Invoice(id, customerNameField.getText(), orderList, false);
            invoice.setTotalAmount(totalAmount);  // Cập nhật lại tổng tiền

            invoiceDAO.addInvoice(invoice);

            JOptionPane.showMessageDialog(this, "Hóa đơn đã được thêm.");

            // Làm mới danh sách hóa đơn trên giao diện
            listInvoices();
            clearInputFields();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm hóa đơn: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho ID và Tổng số tiền.");
        }
    }

    private List<MenuItem> getOrderListFromUI() {
        // Lấy món ăn từ JComboBox
        List<MenuItem> orderList = new ArrayList<>();
        MenuItem selectedItem = (MenuItem) menuItemComboBox.getSelectedItem();
        if (selectedItem != null) {
            orderList.add(selectedItem);  // Thêm món ăn được chọn vào danh sách
        }
        return orderList;  // Trả về danh sách các món ăn được chọn
    }

    // Hiển thị danh sách tất cả hóa đơn
    private void listInvoices() {
        List<Invoice> invoices = invoiceDAO.getAllInvoices();
        invoiceListArea.setText("");  // Xóa nội dung cũ
        for (Invoice invoice : invoices) {
            invoiceListArea.append(invoice.toString() + "\n");
        }
    }

    private void updateInvoicePayment() {
        try {
            String idText = idField.getText();

            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ID hóa đơn.");
                return;
            }

            int id = Integer.parseInt(idText);
            boolean isPaid = true;
            invoiceDAO.updateInvoicePayment(id, isPaid);
            JOptionPane.showMessageDialog(this, "Trạng thái thanh toán đã được cập nhật.");
            clearInputFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật trạng thái thanh toán: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập một ID hợp lệ.");
        }
    }

    private void deleteInvoice() {
        try {
            String idText = idField.getText();

            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ID hóa đơn.");
                return;
            }

            int id = Integer.parseInt(idText);
            invoiceDAO.deleteInvoice(id);
            JOptionPane.showMessageDialog(this, "Hóa đơn đã được xóa.");
            clearInputFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa hóa đơn: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập một ID hợp lệ.");
        }
    }

    private void clearInputFields() {
        idField.setText("");
        customerNameField.setText("");
        totalAmountField.setText("");
    }
}
