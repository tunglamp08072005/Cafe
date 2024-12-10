package org.example.gui;

import org.example.coffeeshop.Invoice;
import org.example.database.InvoiceDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class InvoicePanel extends JPanel {
    private JTable invoiceTable;
    private DefaultTableModel tableModel;
    private InvoiceDAO invoiceDAO;

    public InvoicePanel() {
        invoiceDAO = new InvoiceDAO();
        setLayout(new BorderLayout());
        initializeUI();
        loadInvoices();
    }

    private void initializeUI() {
        String[] columnNames = {"ID", "Customer Name", "Total Amount", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        invoiceTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(invoiceTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton updateStatusButton = new JButton("Mark as Paid");
        updateStatusButton.addActionListener(e -> updateInvoiceStatus());
        add(updateStatusButton, BorderLayout.SOUTH);
    }

    private void loadInvoices() {
        try {
            List<Invoice> invoices = invoiceDAO.getAllInvoices();
            for(Invoice invoice : invoices) {
                tableModel.addRow(new Object[]{
                        invoice.getId(),
                        invoice.getCustomerName(),
                        invoice.getTotalAmount(),
                        invoice.isPaid() ? "Paid" : "Unpaid"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading invoices: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateInvoiceStatus() {
        int selectedRow = invoiceTable.getSelectedRow();
        if(selectedRow >= 0) {
            int invoiceId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                invoiceDAO.updateInvoiceStatus(invoiceId, true);
                tableModel.setValueAt("Paid", selectedRow, 3);
                JOptionPane.showMessageDialog(this, "Invoice marked as paid successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error updating invoice status: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an invoice to update.");
        }
    }
}
