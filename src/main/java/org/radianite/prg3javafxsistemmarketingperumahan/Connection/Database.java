package org.radianite.prg3javafxsistemmarketingperumahan.Connection;

import java.sql.*;
public class Database {
    public Connection conn;
    public Statement stat;
    public ResultSet result;
    public PreparedStatement pstat;

    public Database(){
        try{
            String url = "jdbc:sqlserver://localhost;database=Radianite;user=sa;password=12345;";
            conn = DriverManager.getConnection(url);
            stat = conn.createStatement();
        }catch (SQLException e){
            System.out.println("Error: "+e);
        }
    }

    public static void main(String[] args) {
        Database connect = new Database();
    }
}
