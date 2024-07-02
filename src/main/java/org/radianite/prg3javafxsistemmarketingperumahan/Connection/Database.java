package org.radianite.prg3javafxsistemmarketingperumahan.Connection;

import java.sql.*;

public class Database {
    public Connection conn;
    public Statement stat;
    public ResultSet result;
    public PreparedStatement pstat;

    public Database() {
        try {
            String url = "jdbc:sqlserver://localhost;database=Radianite;user=sa;password=123;";
            conn = DriverManager.getConnection(url);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    // Method untuk menutup semua sumber daya terkait database
    public void close() {
        try {
            if (result != null) {
                result.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (pstat != null) {
                pstat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing database resources: " + e.getMessage());
        }
    }

    // Contoh penggunaan dalam main method
    public static void main(String[] args) {
        Database connect = new Database();

        // Contoh penggunaan: eksekusi query
        try {
            String query = "SELECT * FROM TableName";
            ResultSet rs = connect.stat.executeQuery(query);
            while (rs.next()) {
                // Proses hasil query
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        } finally {
            connect.close(); // Pastikan untuk menutup koneksi setelah selesai menggunakan
        }
    }
}
