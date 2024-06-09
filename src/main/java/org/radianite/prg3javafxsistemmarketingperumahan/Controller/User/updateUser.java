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
import org.radianite.prg3javafxsistemmarketingperumahan.Models.TipeRumah;
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
        txtName.setText(data.getName());
        txtEmail.setText(data.getEmail());
        txtAddress.setText(data.getAddress());
        toggleRadio(data.getGender());
        txtAge.setText(data.getAge().toString());
    }

    public void toggleRadio(String val){
        for (Toggle toggle : group.getToggles()) {
            RadioButton radioButton = (RadioButton) toggle;
            if (radioButton.getText().equals(val)) {
                radioButton.setSelected(true);
                break;
            }
        }
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

    public void loadRole(){
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

    public void onActionUpdate(ActionEvent event) {
    }
}
