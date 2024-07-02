package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Bank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class createBank extends Library implements Initializable {
    @FXML
    private TextField txtId,txtNama,txtBunga;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setDisable(true);
        txtId.setText(generateID("ms_bank","BNK","id_bank"));

        txtNama.addEventFilter(KeyEvent.KEY_TYPED, super::handleLetterKey);
        txtBunga.addEventFilter(KeyEvent.KEY_TYPED, super::handleNumberKey);
    }

    public void clear()
    {
        txtId.setText("");
        txtNama.setText("");
        txtBunga.setText("");
    }

    public boolean isEmpty()
    {
        if (txtNama.getText().isEmpty() || txtBunga.getText().isEmpty())
        {
            return true;
        }
        return false;
    }

    public void onActionSave(ActionEvent actionEvent) {
        if (isEmpty())
        {
            fillBox();
            return;
        }
        try{
            Database connect = new Database();
            String query = "EXEC sp_inputBank ?,?,?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,txtId.getText());
            connect.pstat.setString(2,txtNama.getText());
            connect.pstat.setInt(3,Integer.parseInt(txtBunga.getText()));
            connect.pstat.executeUpdate();
            successBox();
            clear();
            txtId.setText(generateID("ms_bank","BNK","id_bank"));
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
}
