package org.example.gui;

import org.example.database.InvoiceDAO;
import org.example.coffeeshop.Invoice;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class InvoicePanel extends JPanel {
    private final InvoiceDAO invoiceDAO;
    private final JTextField idField;
    private final JTextField customerNameField;
    private final JTextField totalAmountField;
    private final JTextArea invoiceListArea;

    public InvoicePanel() {
        invoiceDAO = new InvoiceDAO();  // Tạo đối tượng InvoiceDAO để quản lý hóa đơn
        setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        inputPanel.add(new JLabel("ID Hóa Đơn:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Tên Khách Hàng:"));
        customerNameField = new JTextField();
        inputPanel.add(customerNameField);

        inputPanel.add(new JLabel("Tổng Số Tiền:"));
        totalAmountField = new JTextField();
        inputPanel.add(totalAmountField);

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
        try {
            if (isInteger) {
                Integer.parseInt(str);  // Kiểm tra ID là số nguyên
            } else {
                Double.parseDouble(str);  // Kiểm tra Tổng Số Tiền là số thực
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



    private void addInvoice() {
        String idText = idField.getText();
        String totalAmountText = totalAmountField.getText();

        try {
            // Kiểm tra xem các trường nhập liệu có rỗng không
            if (idText.isEmpty() || totalAmountText.isEmpty() || customerNameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }
            // Kiểm tra định dạng ID và Tổng Số Tiền
            if (!isNumeric(idText, true) || !isNumeric(totalAmountText, false)) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho ID và Tổng số tiền.");
                return;
            }


            int id = Integer.parseInt(idText);
            double totalAmount = Double.parseDouble(totalAmountText);

            Invoice invoice = new Invoice(id, customerNameField.getText(), null, false);
            invoice.setTotalAmount(totalAmount);
            invoiceDAO.addInvoice(invoice);

            JOptionPane.showMessageDialog(this, "Hóa đơn đã được thêm.");
            clearInputFields();

        } catch (SQLException ex) {
            ex.printStackTrace();  // In ra chi tiết lỗi
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm hóa đơn: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho ID và Tổng số tiền.");
        }
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

            // Kiểm tra xem ID có rỗng không
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ID hóa đơn.");
                return;
            }

            int id = Integer.parseInt(idText);
            boolean isPaid = true;  // Cập nhật trạng thái thanh toán
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

            // Kiểm tra xem ID có rỗng không
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

    // Xóa các trường nhập liệu
    private void clearInputFields() {
        idField.setText("");
        customerNameField.setText("");
        totalAmountField.setText("");
    }
}
