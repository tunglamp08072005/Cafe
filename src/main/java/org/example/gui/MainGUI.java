package org.example.gui;

import javax.swing.*;
import java.awt.*;

/** Main GUI class to navigate between different panels. */
public class MainGUI extends JFrame {
    public MainGUI() {
        setTitle("Coffee Shop Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add panels
        tabbedPane.addTab("Menu Management", new MenuPanel());
        tabbedPane.addTab("Employee Management", new EmployeePanel());  // Thêm EmployeePanel vào tab
        tabbedPane.addTab("Invoice Management", new InvoicePanel());    // Thêm InvoicePanel vào tab

        // Add tabbed pane to frame
        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI mainGUI = new MainGUI();
            mainGUI.setVisible(true);
        });
    }
}
