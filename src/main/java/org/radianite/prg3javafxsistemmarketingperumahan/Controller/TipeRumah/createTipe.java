package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TipeRumah;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class createTipe extends Library implements Initializable {
    @FXML
    private TextField txtId,txtName;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setText(generateID("ms_tipe_rumah","TRM","id_tipe"));
        txtId.setDisable(true);
    }

    public void onClickSave(MouseEvent mouseEvent) {
        try{
            Database connect = new Database();
            String query="EXEC sp_inputTipeRumah ?,?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,txtId.getText());
            connect.pstat.setString(2,txtName.getText());
            connect.pstat.executeUpdate();
            connect.pstat.close();
            connect.conn.close();
            successBox();
            clear();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }

    }

    public void clear(){
        txtId.setText(generateID("ms_tipe_rumah","TRM","id_tipe"));
        txtName.setText("");
    }
}
