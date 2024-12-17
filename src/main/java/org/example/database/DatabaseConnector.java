package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Manages database connection. */
public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3307/coffee_shop";
    private static final String USER = "root"; // Thay bằng username của bạn
    private static final String PASSWORD = "A3_lamsoaica"; // Thay bằng mật khẩu của bạn

    static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

}
