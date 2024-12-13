package org.example.gui;

import javax.swing.*;

public class MainApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] options = {"Admin Dashboard", "User Dashboard"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Welcome to Coffee Shop Management!\\nChoose your interface:",
                    "Coffee Shop Management",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) {
                AdminDashboard adminDashboard = new AdminDashboard();
                adminDashboard.setVisible(true);
            } else if (choice == 1) {
                UserDashboard userDashboard = new UserDashboard();
                userDashboard.setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}
