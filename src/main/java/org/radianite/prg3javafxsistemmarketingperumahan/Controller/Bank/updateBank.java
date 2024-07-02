package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Bank;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Bank;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class updateBank extends Library implements Initializable {
    @FXML
    private TextField txtId,txtNama,txtBunga;
    private ObservableList<Bank> listBank = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setDisable(true);

        txtNama.addEventFilter(KeyEvent.KEY_TYPED, super::handleLetterKey);
        txtBunga.addEventFilter(KeyEvent.KEY_TYPED, super::handleNumberKey);

        loadData();
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
            fillBox();
            return;
        }

        for (int i=0;i<listBank.size();i++)
        {
            if (txtNama.getText().equals(listBank.get(i).getName()) && !txtId.getText().equals(listBank.get(i).getId())){
                errorBox();
                return;
            }
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

    public void loadData()
    {
        listBank.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM ms_bank";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next())
            {
                if (connect.result.getInt("status") == 1){
                    listBank.add(new Bank(
                            connect.result.getString("id_bank"),
                            connect.result.getString("nama_bank"),
                            connect.result.getInt("suku_bunga")
                    ));
                }
            }
            connect.result.close();
            connect.stat.close();
        }catch (SQLException ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
    }
}
