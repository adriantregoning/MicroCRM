package com.at.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.at.utils.ConfigUtil;

public class DbConnect {

    public Connection connectionMethod() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("connectionMethod: Connecting to the database.....");
            
            String url = ConfigUtil.getProperty("db.url");
            String username = ConfigUtil.getProperty("db.username");
            String password = ConfigUtil.getProperty("db.password");
            
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("connectionMethod: Connection established successfully!");
            System.out.println("");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return conn;
    }
    
    public void connectionClose(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("***connection closed***");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}