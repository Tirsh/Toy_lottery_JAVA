package com.tirsh.toy_lottery.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static final String DB_PATH = "jdbc:sqlite:toys.sqlite";
    private final Connection connection;

    public DBConnection() {
        try {
            connection = DriverManager.getConnection(DB_PATH);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
