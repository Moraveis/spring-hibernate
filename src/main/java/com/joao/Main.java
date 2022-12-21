package com.joao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String jdbc = "jdbc:h2:mem:testdb";
        String user = "sa", password = "";

        try (Connection c = DriverManager.getConnection(jdbc, user, password)) {
            System.out.println("Connected successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}