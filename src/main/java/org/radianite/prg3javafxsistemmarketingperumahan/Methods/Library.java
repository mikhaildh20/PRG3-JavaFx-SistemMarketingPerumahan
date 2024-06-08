package org.radianite.prg3javafxsistemmarketingperumahan.Methods;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;

import java.sql.SQLException;

public class Library {
    Database connect = new Database();
    String imported,base;
    public Library(){

    }

    public String generateID(String tableName,String formatID,String column){
        try{
            String query = "SELECT TOP 1 "+column+" FROM "+tableName+" ORDER BY "+column+" DESC";
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
            ex.printStackTrace();
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
}
