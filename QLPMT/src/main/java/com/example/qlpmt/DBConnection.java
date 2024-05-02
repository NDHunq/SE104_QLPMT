package com.example.qlpmt;

import com.almasb.fxgl.net.Connection;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    public static Connection getConnection () {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // ten database
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QLPK";

            connection= (Connection) DriverManager.getConnection(url);

        } catch (ClassNotFoundException | java.sql.SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return connection;
    }
}
