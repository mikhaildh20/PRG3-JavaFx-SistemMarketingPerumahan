package org.radianite.prg3javafxsistemmarketingperumahan.Connection;

import java.sql.*;

public class Database {
    public Connection conn;
    public Statement stat;
    public ResultSet result;
    public PreparedStatement pstat;

    public Database() {
        try {
            String url = "jdbc:sqlserver://localhost;database=Radianite;user=sa;password=12345;";
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
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlserver://localhost;database=Radianite;user=sa;password=12345;");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getDataRumah(String idper) {
        ResultSet resultSet = null;
        try {
            Connection conn = getConnection();
            if (conn != null) {
                String query = "SELECT R.id_rumah, T.nama_perumahan, R.foto_rumah, R.blok, R.daya_listrik, R.interior, R.thn_bangun, R.harga, R.jml_kmr_mdn, R.descrption, R.jml_kmr_tdr, TR.nama_tipe " +
                               "FROM ms_rumah R " +
                               "JOIN ms_perumahan T ON R.id_perumahan = T.id_perumahan " +
                               "JOIN ms_tipe_rumah TR ON R.id_tipe = TR.id_tipe " +
                               "WHERE R.status = 1 AND R.id_perumahan = ? AND R.ketersediaan = 1";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, idper);
                resultSet = pstmt.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static ResultSet getDataRuko(String idper) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            return stmt.executeQuery("SELECT R.id_ruko,P.nama_perumahan,R.foto_ruko,R.blok,R.daya_listrik,R.jml_kmr_mdn,R.descrption,R.harga_sewa" +
                    " FROM ms_ruko R " +
                    " JOIN ms_perumahan P ON R.id_perumahan = P.id_perumahan " +
                    "WHERE R.status = 1 AND r.id_perumahan ='"+idper+"'"+" AND R.ketersediaan = 1");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Contoh penggunaan dalam main method
    public static void main(String[] args) {
        Database connect = new Database();

        // Contoh penggunaan: eksekusi query
        /*try {
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
        }*/
    }
}
