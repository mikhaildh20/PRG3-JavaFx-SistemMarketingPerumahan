package org.radianite.prg3javafxsistemmarketingperumahan.Controller.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Perumahan;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Roles;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class createUser extends Library implements Initializable {
    @FXML
    private TextField txtUsn,txtPass,txtName,txtEmail,txtAge;
    @FXML
    private TextArea txtAddress;
    @FXML
    private ComboBox<Perumahan> cbResidence;
    @FXML
    private ComboBox<Roles> cbRole;
    @FXML
    private RadioButton rbMale,rbFemale;
    @FXML
    private Button btnFile;
    @FXML
    private Label LabFile;
    File file;

    private ObservableList<Perumahan> listPerum = FXCollections.observableArrayList();
    private ObservableList<Roles> listRole = FXCollections.observableArrayList();
    ToggleGroup group = new ToggleGroup();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPerumahan();
        loadRoles();
        rbMale.setToggleGroup(group);
        rbFemale.setToggleGroup(group);
        cbResidence.setCellFactory(param->new javafx.scene.control.ListCell<Perumahan>(){
            protected void updateItem(Perumahan item,boolean empty){
                super.updateItem(item,empty);
                if (item == null || empty){
                    setText(null);
                }else{
                    setText(item.getName());
                }
            }
        });
        cbRole.setCellFactory(param->new javafx.scene.control.ListCell<Roles>(){
            protected void updateItem(Roles item,boolean empty){
                super.updateItem(item,empty);
                if(item == null || empty){
                    setText(null);
                }else{
                    setText(item.getName());
                }
            }
        });

        cbResidence.setConverter(new StringConverter<Perumahan>() {
            @Override
            public String toString(Perumahan perumahan) {
                return perumahan == null ? null : perumahan.getName();
            }

            @Override
            public Perumahan fromString(String s) {
                return null;
            }
        });

        cbRole.setConverter(new StringConverter<Roles>() {
            @Override
            public String toString(Roles role) {
                return role == null ? null : role.getName();
            }

            @Override
            public Roles fromString(String s) {
                return null;
            }
        });
    }

    public void clear(){
        txtUsn.setText("");
        txtPass.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        group.selectToggle(null);
        txtAge.setText("");
    }

    public void loadRoles(){
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM ms_role";
            connect.result = connect.stat.executeQuery(query);
            while(connect.result.next()){
                if (connect.result.getInt("status")==1){
                    listRole.add(new Roles(connect.result.getString("id_role"),connect.result.getString("nama_role")));
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        cbRole.setItems(listRole);
    }

    public void loadPerumahan(){
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM ms_perumahan";
            connect.result = connect.stat.executeQuery(query);
            while(connect.result.next()){
                if (connect.result.getInt("status")==1){
                    listPerum.add(new Perumahan(connect.result.getString("id_perumahan"),connect.result.getString("nama_perumahan")));
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        cbResidence.setItems(listPerum);
    }

    public void onActionSave(ActionEvent actionEvent) {
        if (isEmpty())
        {
            fillBox();
            return;
        }
        Toggle selected = group.getSelectedToggle();
        RadioButton val = (RadioButton) selected;
        try{
            Database connect = new Database();
            String query = "EXEC sp_inputUser ?,?,?,?,?,?,?,?,?,?";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.pstat.setString(1,txtUsn.getText());
            connect.pstat.setString(2,txtPass.getText());
            connect.pstat.setString(3,cbResidence.getSelectionModel().getSelectedItem().getId());
            connect.pstat.setString(4,cbRole.getSelectionModel().getSelectedItem().getId());
            connect.pstat.setString(5,txtName.getText());
            connect.pstat.setString(6,txtEmail.getText());
            connect.pstat.setString(7,txtAddress.getText());
            connect.pstat.setString(8,val.getText());
            connect.pstat.setString(9,txtAge.getText());
            connect.pstat.setBytes(10,imageToByte(file));
            connect.pstat.executeUpdate();
            connect.pstat.close();
            successBox();
            clear();
        }catch (SQLException | IOException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public boolean isEmpty()
    {
        if (txtUsn.getText().isEmpty() || txtPass.getText().isEmpty() || txtName.getText().isEmpty() || txtEmail.getText().isEmpty() || txtAddress.getText().isEmpty() || !rbMale.isSelected() && !rbFemale.isSelected() || txtAge.getText().isEmpty() || file == null){
            return true;
        }
        return false;
    }

    public void onActionFile(ActionEvent actionEvent) {
        file = imageChooser(btnFile);
        LabFile.setText(file.getName());
    }
}
