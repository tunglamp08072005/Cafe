package org.example.gui;

import org.example.database.EmployeeDAO;
import org.example.coffeeshop.Employee;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeePanel extends JPanel {
    private final EmployeeDAO employeeDAO;
    private final JTextField idField;
    private final JTextField nameField;
    private final JTextField positionField;
    private final JTextField salaryField;
    private final JTextArea employeeListArea;

    public EmployeePanel() {
        employeeDAO = new EmployeeDAO();  // Tạo đối tượng EmployeeDAO để quản lý nhân viên
        setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Tên:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Chức vụ:"));
        positionField = new JTextField();
        inputPanel.add(positionField);

        inputPanel.add(new JLabel("Lương:"));
        salaryField = new JTextField();
        inputPanel.add(salaryField);

        // Các nút hành động
        JPanel buttonPanel = getjPanel();

        // Khu vực hiển thị danh sách nhân viên
        employeeListArea = new JTextArea(10, 40);
        employeeListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(employeeListArea);

        // Thêm các thành phần vào JPanel chính
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private JPanel getjPanel() {
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Thêm nhân viên");
        JButton listButton = new JButton("Hiển thị danh sách nhân viên");
        JButton deleteButton = new JButton("Xóa nhân viên");

        addButton.addActionListener(e -> addEmployee());
        listButton.addActionListener(e -> listEmployees());
        deleteButton.addActionListener(e -> deleteEmployee());

        buttonPanel.add(addButton);
        buttonPanel.add(listButton);
        buttonPanel.add(deleteButton);
        return buttonPanel;
    }

    private void addEmployee() {
        try {
            // Kiểm tra xem trường ID có trống không
            String idText = idField.getText();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID không được để trống.");
                return;
            }
            int id = Integer.parseInt(idText);

            // Kiểm tra các trường tên và chức vụ
            String name = nameField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên không được để trống.");
                return;
            }

            String position = positionField.getText();
            if (position.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chức vụ không được để trống.");
                return;
            }

            // Kiểm tra trường lương
            String salaryText = salaryField.getText();
            if (salaryText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lương không được để trống.");
                return;
            }

            double salary = Double.parseDouble(salaryText);

            // Thêm nhân viên vào cơ sở dữ liệu
            Employee employee = new Employee(id, name, position, salary);
            employeeDAO.addEmployee(employee);
            JOptionPane.showMessageDialog(this, "Nhân viên đã được thêm.");
            clearInputFields();
        } catch (NumberFormatException ex) {
            // Nếu có lỗi khi chuyển đổi kiểu dữ liệu
            JOptionPane.showMessageDialog(this, "Lương và ID phải là số hợp lệ.");
        }
    }

    // Hiển thị danh sách tất cả nhân viên
    private void listEmployees() {
        List<Employee> employees = employeeDAO.getAllEmployees();
        employeeListArea.setText("");  // Xóa nội dung cũ
        for (Employee employee : employees) {
            employeeListArea.append(employee.toString() + "\n");
        }
    }

    private void deleteEmployee() {
        String idText = idField.getText();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID không được để trống.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            employeeDAO.deleteEmployee(id);
            JOptionPane.showMessageDialog(this, "Nhân viên đã được xóa.");
            clearInputFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID phải là một số hợp lệ.");
        }
    }


    // Xóa các trường nhập liệu
    private void clearInputFields() {
        idField.setText("");
        nameField.setText("");
        positionField.setText("");
        salaryField.setText("");
    }
}
