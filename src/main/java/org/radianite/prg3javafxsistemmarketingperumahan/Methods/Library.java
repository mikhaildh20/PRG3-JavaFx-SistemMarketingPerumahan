package org.radianite.prg3javafxsistemmarketingperumahan.Methods;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;

import java.io.IOException;
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
        alert.setContentText("Operation has been done successfully.");
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

    public void loadPage(ActionEvent event, String page){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/"+page+".fxml"));
        }catch (IOException ex){
            ex.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.show();
    }
}
