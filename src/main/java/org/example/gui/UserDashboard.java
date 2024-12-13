package org.example.gui;

import org.example.database.MenuItemDAO;
import org.example.coffeeshop.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class UserDashboard extends JFrame {

    private JTable menuTable;
    private DefaultTableModel tableModel;
    private MenuItemDAO menuItemDAO;

    public UserDashboard() {
        menuItemDAO = new MenuItemDAO();
        setTitle("User Dashboard - Coffee Shop");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeUI();
    }

    private void initializeUI() {
        JLabel welcomeLabel = new JLabel("Welcome to Coffee Shop!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Name", "Type", "Price", "Availability"};
        tableModel = new DefaultTableModel(columnNames, 0);
        menuTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(menuTable);

        add(scrollPane, BorderLayout.CENTER);

        JButton orderButton = new JButton("Order Selected Item");
        orderButton.addActionListener(e -> placeOrder());
        add(orderButton, BorderLayout.SOUTH);

        loadMenuItems();
    }

    private void loadMenuItems() {
        try {
            List<MenuItem> menuItems = menuItemDAO.getAllMenuItems();
            for(MenuItem menuItem : menuItems) {
                tableModel.addRow(new Object[]{menuItem.getId(),
                        menuItem.getName(),
                        menuItem.getType(),
                        menuItem.getPrice(),
                        menuItem.isAvailable() ? "Available" : "Unavailable"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading menu items: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void placeOrder() {
        int selectedRow = menuTable.getSelectedRow();
        if(selectedRow >= 0) {
            String itemName = (String) tableModel.getValueAt(selectedRow, 1);
            double itemPrice = (double) tableModel.getValueAt(selectedRow, 3);
            JOptionPane.showMessageDialog(this, "You ordered: " + itemName + " ($\" + itemPrice + \")");
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to order.");
        }
    }
}
