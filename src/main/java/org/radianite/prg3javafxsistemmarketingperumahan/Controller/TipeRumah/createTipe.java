package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TipeRumah;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class createTipe implements Initializable {
    @FXML
    private TextField txtId,txtName;
    Library lib = new Library();
    Database connect = new Database();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setText(lib.generateID("ms_tipe_rumah","TRM","id_tipe"));
        txtId.setDisable(true);
    }

    public void onClickSave(MouseEvent mouseEvent) {
        try{
            String query="EXEC sp_inputTipeRumah ?,?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,txtId.getText());
            connect.pstat.setString(2,txtName.getText());
            connect.pstat.executeUpdate();
            connect.pstat.close();
            connect.conn.close();
            lib.successBox();
            clear();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public void clear(){
        txtId.setText(lib.generateID("ms_tipe_rumah","TRM","id_tipe"));
        txtId.setDisable(true);
        txtName.setText("");
    }
}
