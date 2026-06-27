package com.quanlytapphoa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ketnoi {
    public static final String DB_NAME = "QuanLyTapHoa";

    private static String url(String db) {
        return "jdbc:sqlserver://localhost:1433;"
                + "databaseName=" + db + ";"
                + "integratedSecurity=true;"
                + "encrypt=true;"
                + "trustServerCertificate=true;";
    }

    public static Connection getConnection() throws SQLException {
        napDriver();
        return DriverManager.getConnection(url(DB_NAME));
    }

    private static void napDriver() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Chua co SQL Server JDBC Driver trong classpath", ex);
        }
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Ket noi database QuanLyTapHoa thanh cong");
        } catch (SQLException e) {
            System.out.println("Ket noi that bai");
            e.printStackTrace();
        }
    }
}
