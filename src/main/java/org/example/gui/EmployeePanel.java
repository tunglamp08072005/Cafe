package org.example.gui;

import org.example.database.EmployeeDAO;
import org.example.coffeeshop.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeePanel extends JPanel {

    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private EmployeeDAO employeeDAO;

    public EmployeePanel() {
        employeeDAO = new EmployeeDAO();
        setLayout(new BorderLayout());
        initializeUI();
        loadEmployees();
    }

    private void initializeUI() {
        String[] columnNames = {"ID", "Name", "Position", "Salary"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add Employee");
        addButton.addActionListener(e -> addEmployee());
        add(addButton, BorderLayout.SOUTH);
    }

    private void loadEmployees() {
        try {
            List<Employee> employees = employeeDAO.getAllEmployees();
            for (Employee employee : employees) {
                tableModel.addRow(new Object[]{employee.getId(),
                        employee.getName(),
                        employee.getPosition(),
                        employee.getSalary()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading employees: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEmployee() {
        String name = JOptionPane.showInputDialog(this, "Enter employee name:");
        String position = JOptionPane.showInputDialog(this, "Enter employee position:");
        String salaryStr = JOptionPane.showInputDialog(this, "Enter employee salary");

        if(name != null && position != null && salaryStr != null) {
            try {
                double salary = Double.parseDouble(salaryStr);
                Employee newEmployee = new Employee(0, name, position, salary);
                employeeDAO.addEmployee(newEmployee);
                tableModel.addRow(new Object[]{
                        newEmployee.getId(),
                        newEmployee.getName(),
                        newEmployee.getPosition(),
                        newEmployee.getSalary()
                });
                JOptionPane.showMessageDialog(this, "Employee added successfully");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid salary format!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding employee: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
