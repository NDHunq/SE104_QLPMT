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
            String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=QLPK;user=sa;password=123456789;encrypt=true;trustServerCertificate=true";

            connection = DriverManager.getConnection(connectionUrl);

        } catch (ClassNotFoundException | java.sql.SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return connection;
    }
}
