package org.example.gui;

import org.example.coffeeshop.Invoice;
import org.example.coffeeshop.MenuItem;
import org.example.database.MenuItemDAO;
import org.example.api.MenuAPI;
import org.example.api.SeasonalMenuAPI; // Thêm import SeasonalMenuAPI
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
    private final SeasonalMenuAPI seasonalMenuAPI; // Khởi tạo đối tượng SeasonalMenuAPI
    private final PaymentAPI paymentAPI; // Khai báo PaymentAPI


    public MenuPanel() {
        setLayout(new BorderLayout());

        // Initialize DAO và API
        menuItemDAO = new MenuItemDAO();
        menuAPI = new MenuAPI(); // Khởi tạo MenuAPI
        seasonalMenuAPI = new SeasonalMenuAPI(); // Khởi tạo SeasonalMenuAPI
        paymentAPI = new PaymentAPI(); // Khởi tạo PaymentAPI


        // Create table model
        String[] columnNames = {"ID", "Name", "Price", "Description", "Available"};
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
                    item.getPrice(),
                    item.getDescription(),
                    item.isAvailable() ? "Yes" : "No"
            });
        }
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Add Item");
        JButton editButton = new JButton("Edit Item");
        JButton deleteButton = new JButton("Delete Item");
        JButton fetchSpecialButton = new JButton("Fetch Special Menu Items");
        JButton fetchSeasonalButton = new JButton("Fetch Seasonal Menu"); // Button for seasonal menu
        JButton processPaymentButton = new JButton("Process Payment");

        addButton.addActionListener(e -> addMenuItem());
        editButton.addActionListener(e -> editMenuItem());
        deleteButton.addActionListener(e -> deleteMenuItem());
        fetchSpecialButton.addActionListener(e -> fetchSpecialMenuItems());
        fetchSeasonalButton.addActionListener(e -> fetchSeasonalMenuItems()); // Action for seasonal menu
        processPaymentButton.addActionListener(e -> processPayment());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(fetchSpecialButton);
        buttonPanel.add(fetchSeasonalButton); // Add seasonal menu button
        buttonPanel.add(processPaymentButton);

        return buttonPanel;
    }

    private void addMenuItem() {
        String name = JOptionPane.showInputDialog(this, "Enter item name:");
        if (name == null || name.trim().isEmpty()) return;

        String priceStr = JOptionPane.showInputDialog(this, "Enter item price:");
        double price = Double.parseDouble(priceStr); // Convert to double

        String description = JOptionPane.showInputDialog(this, "Enter item description:");

        boolean isAvailable = JOptionPane.showConfirmDialog(this, "Is this item available?", "Availability", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

        MenuItem newItem = new MenuItem(0, name, price, description, isAvailable);

        menuItemDAO.addMenuItem(newItem);
        loadMenuItemsFromDatabase();
        JOptionPane.showMessageDialog(this, "Item added successfully!");
    }

    private void editMenuItem() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow != -1) {
            MenuItem selectedItem = menuItems.get(selectedRow);

            String newName = JOptionPane.showInputDialog(this, "Edit item name:", selectedItem.getName());
            String newPriceStr = JOptionPane.showInputDialog(this, "Edit item price:", selectedItem.getPrice());
            double newPrice = Double.parseDouble(newPriceStr); // Convert to double

            String newDescription = JOptionPane.showInputDialog(this, "Edit item description:", selectedItem.getDescription());

            boolean newIsAvailable = JOptionPane.showConfirmDialog(this, "Is this item available?", "Availability", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

            selectedItem.setName(newName);
            selectedItem.setPrice(newPrice);
            selectedItem.setDescription(newDescription);
            selectedItem.setAvailable(newIsAvailable);

            menuAPI.updateDrinkPrice(selectedItem, newPrice);
            menuItemDAO.updateMenuItem(selectedItem);
            loadMenuItemsFromDatabase();
            JOptionPane.showMessageDialog(this, "Item updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "No item selected!");
        }
    }

    private void deleteMenuItem() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow != -1) {
            MenuItem selectedItem = menuItems.get(selectedRow);
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?", "Delete Item", JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                menuItemDAO.deleteMenuItem(selectedItem.getId());
                loadMenuItemsFromDatabase();
                JOptionPane.showMessageDialog(this, "Item deleted successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No item selected!");
        }
    }

    private void fetchSpecialMenuItems() {
        List<MenuItem> specialMenuItems = menuAPI.fetchSpecialMenuItems();
        for (MenuItem item : specialMenuItems) {
            menuItemDAO.addMenuItem(item);
        }
        loadMenuItemsFromDatabase();
        JOptionPane.showMessageDialog(this, "Special menu items fetched and added!");
    }

    private void fetchSeasonalMenuItems() {
        String season = JOptionPane.showInputDialog(this, "Enter season (summer/winter/other):");
        if (season != null && !season.trim().isEmpty()) {
            List<MenuItem> seasonalItems = seasonalMenuAPI.fetchSeasonalMenuItems(season);

            for (MenuItem item : seasonalItems) {
                menuItemDAO.addMenuItem(item);  // Optional: Add seasonal items to the database
            }

            loadMenuItemsFromDatabase();  // Update table with seasonal items
            JOptionPane.showMessageDialog(this, "Seasonal menu items fetched and added for " + season + " season!");
        } else {
            JOptionPane.showMessageDialog(this, "Season input is invalid!");
        }
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
