package org.radianite.prg3javafxsistemmarketingperumahan.Methods;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;

import java.sql.SQLException;

public class Library {
    String query,imported,base;
    public Library(){

    }

    public String generateID(String tableName,String formatID,String column){
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            query = "SELECT TOP 1 "+column+" FROM "+tableName+" ORDER BY "+column+" DESC";
            connect.result = connect.stat.executeQuery(query);
            if (connect.result.next()){
                imported = connect.result.getString(column);
                connect.result.close();
                connect.stat.close();
                base="";
                for (int i=3;i<imported.length();i++){
                    base+=imported.charAt(i);
                }
                return String.format("%s%03d", formatID, Integer.parseInt(base)+1);
            }
            connect.stat.close();
            connect.result.close();
            return formatID+"001";
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return formatID+"001";
    }

    public void successBox(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText(null);
        alert.setContentText("Data has been stored successfully.");
        alert.showAndWait();
    }

    public void warningBox(){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(null);
        alert.setContentText("You don't have permission!");
        alert.showAndWait();
    }

    public void errorBox(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Unexpected Error.");
        alert.showAndWait();
    }

    public void confirmBox(String sp,String id){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Record");
        alert.setContentText("Are you sure you want to delete this record?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                // User clicked Yes
                try{
                    Database connect = new Database();
                    String query = "EXEC "+sp+" ?";
                    connect.pstat = connect.conn.prepareStatement(query);
                    connect.pstat.setString(1,id);
                    connect.pstat.executeUpdate();
                    connect.pstat.close();
                }catch (SQLException ex){
                    System.out.println("Error: "+ex.getMessage());
                }
            }
        });
    }
}
