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
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Role;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class updateUser extends Library implements Initializable {
    @FXML
    private TextField txtUsn,txtPass,txtName,txtEmail,txtAge;
    @FXML
    private TextArea txtAddress;
    @FXML
    private ComboBox<Perumahan> cbResidence;
    @FXML
    private ComboBox<Role> cbRole;
    @FXML
    private RadioButton rbMale,rbFemale;
    private ObservableList<Perumahan> listPerum = FXCollections.observableArrayList();
    private ObservableList<Role> listRole = FXCollections.observableArrayList();
    ToggleGroup group = new ToggleGroup();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPerumahan();
        loadRole();
        txtUsn.setDisable(true);
        txtPass.setDisable(true);
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
        cbRole.setCellFactory(param->new javafx.scene.control.ListCell<Role>(){
            protected void updateItem(Role item,boolean empty){
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

        cbRole.setConverter(new StringConverter<Role>() {
            @Override
            public String toString(Role role) {
                return role == null ? null : role.getName();
            }

            @Override
            public Role fromString(String s) {
                return null;
            }
        });
    }

    public void setDataList(User data){
        txtUsn.setText(data.getUsn());
        txtPass.setText(data.getPass());
        setPSelectedCbBox(data.getIdp());
        setRSelectedCbBox(data.getIdr());
        txtName.setText(data.getName());
        txtEmail.setText(data.getEmail());
        txtAddress.setText(data.getAddress());
        toggleRadio(data.getGender());
        txtAge.setText(data.getAge().toString());
    }

    private void setPSelectedCbBox(String data){
        for (Perumahan item : cbResidence.getItems()){
            if (item.getId().equals(data)){
                cbResidence.getSelectionModel().select(item);
            }
        }
    }

    private void setRSelectedCbBox(String data){
        for (Role item : cbRole.getItems()){
            if (item.getId().equals(data)){
                cbRole.getSelectionModel().select(item);
            }
        }
    }

    private void toggleRadio(String val){
        for (Toggle toggle : group.getToggles()) {
            RadioButton radioButton = (RadioButton) toggle;
            if (radioButton.getText().equals(val)) {
                radioButton.setSelected(true);
                break;
            }
        }
    }


    private void clear(){
        txtUsn.setText("");
        txtPass.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        group.selectToggle(null);
        txtAge.setText("");
    }

    private void loadRole(){
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "SELECT * FROM ms_role";
            connect.result = connect.stat.executeQuery(query);
            while(connect.result.next()){
                if (connect.result.getInt("status")==1){
                    listRole.add(new Role(connect.result.getString("id_role"),connect.result.getString("nama_role")));
                }
            }
            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        cbRole.setItems(listRole);
    }

    private void loadPerumahan(){
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

    public void onActionUpdate(ActionEvent event) {
        Toggle selected = group.getSelectedToggle();
        RadioButton val = (RadioButton) selected;
        try{
            Database connect = new Database();
            String query = "EXEC sp_updateUser ?,?,?,?,?,?,?,?,?";
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
            connect.pstat.executeUpdate();
            connect.pstat.close();
            successBox();
            loadPage(event,"viewUser");
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
}
