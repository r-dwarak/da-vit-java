package com.emp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // Adjust URL/USER/PASS for your DB
    private static final String URL  = "jdbc:mysql://localhost:3306/ems?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // ensure driver loads on older containers
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found in WEB-INF/lib", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
