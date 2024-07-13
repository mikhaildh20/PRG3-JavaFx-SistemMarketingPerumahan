package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Perumahan;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developers;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Perumahan;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class createPerumahan extends Library implements Initializable {
    @FXML
    private TextField txtId,txtNama;
    @FXML private Button btnSave;
    @FXML
    private ComboBox<Developers> cbDeveloper;
    private ObservableList<Developers> listDev = FXCollections.observableArrayList();
    private ObservableList<Perumahan> listResidence = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDev();
        txtId.setEditable(false);
        txtId.setText(generateID("ms_perumahan","PRM","id_perumahan"));

        cbDeveloper.setCellFactory(param->new javafx.scene.control.ListCell<Developers>(){
            protected void updateItem(Developers item,boolean empty){
                super.updateItem(item,empty);
                if(item == null || empty){
                    setText(null);
                }else{
                    setText(item.getName());
                }
            }
        });

        cbDeveloper.setConverter(new StringConverter<Developers>() {
            @Override
            public String toString(Developers developers) {
                return developers == null ? null : developers.getName();
            }

            @Override
            public Developers fromString(String s) {
                return null;
            }
        });
        txtNama.addEventFilter(KeyEvent.KEY_TYPED, super::handleLetterKey);
    }

    public void loadDev(){
        listDev.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM ms_developer";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()){
                if (connect.result.getInt("status") == 1){
                    listDev.add(new Developers(connect.result.getString("id_developer"),
                            connect.result.getString("nama_developer")));
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        cbDeveloper.setItems(listDev);
    }

    public void loadData(){
        listResidence.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "EXEC sp_viewPerumahan";
            connect.result = connect.stat.executeQuery(query);
            while (connect.result.next()){
                listResidence.add(new Perumahan(connect.result.getString("id_perumahan"),
                        connect.result.getString("id_developer"),
                        connect.result.getString("nama_perumahan"),
                        connect.result.getString("nama_developer"),
                        connect.result.getInt("status"))); // Added status
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public void clear(){
        txtId.setText("");
        txtNama.setText("");
    }

    public void onActionSave(ActionEvent actionEvent) {
        if (txtNama.getText().isEmpty()){
            fillBox(btnSave,"Please fill all the field");
            return;
        }

        for (int i=0;i<listResidence.size();i++)
        {
            if (txtNama.getText().equals(listResidence.get(i).getName()) && !txtId.getText().equals(listResidence.get(i).getId()))
            {
                errorBox(btnSave,"Residence Already Register");
                return;
            }
        }

        try{
            Database connect = new Database();
            String query = "EXEC sp_inputPerumahan ?,?,?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,txtId.getText());
            connect.pstat.setString(2,cbDeveloper.getSelectionModel().getSelectedItem().getId());
            connect.pstat.setString(3,txtNama.getText());
            connect.pstat.executeUpdate();
            connect.pstat.close();
            successBox(btnSave,"Data Successfully Saved");
            clear();
            txtId.setText(generateID("ms_perumahan","PRM","id_perumahan"));
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
}
