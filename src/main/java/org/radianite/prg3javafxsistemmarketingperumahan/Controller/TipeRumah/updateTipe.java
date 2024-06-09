package org.radianite.prg3javafxsistemmarketingperumahan.Controller.TipeRumah;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.TipeRumah;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class updateTipe extends Library implements Initializable {
    @FXML
    private TextField txtId,txtName;

    public void setDataList(TipeRumah data){
        txtId.setText(data.getId());
        txtName.setText(data.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setDisable(true);
    }

    public void onActionUpdate(ActionEvent event) {
        try{
            Database connect = new Database();
            String query = "EXEC sp_updateTipeRumah ?,?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,txtId.getText());
            connect.pstat.setString(2,txtName.getText());
            connect.pstat.executeUpdate();
            connect.pstat.close();
            successBox();
            loadPage(event,"viewTipe");
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
}
