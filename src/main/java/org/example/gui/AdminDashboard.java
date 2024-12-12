package org.example.gui;

import java.awt.*;
import javax.swing.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard - Coffee Shop Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeUI();
    }

    private void initializeUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        InvoicePanel invoicePanel = new InvoicePanel();
        tabbedPane.addTab("Manage Invoices", invoicePanel);

        EmployeePanel employeePanel = new EmployeePanel();
        tabbedPane.addTab("Manage Employees", employeePanel);

        JPanel dashboardPanel = createDashboardPanel();
        tabbedPane.addTab("DashBoard", dashboardPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel totalRevenueLabel = new JLabel("Total Revenue: ");
        JLabel dailyInvoicesLabel = new JLabel("Invoices Today: ");

        JLabel totalRevenueValue = new JLabel("$10,000");
        JLabel dailyInvoicesValue = new JLabel("25");

        panel.add(totalRevenueLabel);
        panel.add(totalRevenueValue);
        panel.add(dailyInvoicesLabel);
        panel.add(dailyInvoicesValue);

        return panel;
    }
}
