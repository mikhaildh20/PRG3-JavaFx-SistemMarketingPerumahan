package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Bank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Bank;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class updateBank extends Library implements Initializable {
    @FXML
    private TextField txtId,txtNama,txtBunga;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setDisable(true);
    }

    public void setDataList(Bank data){
        txtId.setText(data.getId());
        txtNama.setText(data.getName());
        txtBunga.setText(String.valueOf(data.getBunga()));
    }

    public boolean isEmpty()
    {
        if (txtNama.getText().isEmpty() || txtBunga.getText().isEmpty())
        {
            return true;
        }
        return false;
    }

    public void onActionUpdate(ActionEvent actionEvent) {
        if (isEmpty())
        {
/*            fillBox();*/
            return;
        }
        try{
            Database connect = new Database();
            String query = "EXEC sp_updateBank ?,?,?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,txtId.getText());
            connect.pstat.setString(2,txtNama.getText());
            connect.pstat.setInt(3,Integer.parseInt(txtBunga.getText()));
            connect.pstat.executeUpdate();
            successBox();
            loadPage(actionEvent,"viewBank");
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
}
