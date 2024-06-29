package com.example.qlpmt;

import java.sql.Connection;

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
//<<<<<<< HEAD
//
//            String url = "jdbc:sqlserver://DESKTOP-MR3E6H4\\SQLEXPRESS:1433;databaseName=QLPK";
//            String user = "sa"; // replace with your username
//            String password = "hhhhhhhh444"; // replace with your password
//=======
            String url = "jdbc:sqlserver://LAPTOP-CL3NH660:1433;databaseName=QLPK";
            String user = "sa"; // replace with your username
            String password = "phamkhaihung123"; // replace with your password

            connection = (Connection) DriverManager.getConnection(url, user, password);


        } catch (ClassNotFoundException | java.sql.SQLException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return connection;
    }
}
