package com.example.qlpmt;

import java.sql.Connection;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionQuyen {
    public static Connection getConnection () {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // ten database

            String url = "jdbc:sqlserver://DESKTOP-MR3E6H4\\SQLEXPRESS:1433;databaseName=QLPK";
            String user = "sa"; // replace with your username
            String password = "hhhhhhhh444"; // replace with your password

            String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=QLPK;user=sa;password=git;encrypt=true;trustServerCertificate=true";
            connection = (Connection) DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | java.sql.SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return connection;
    }
}
