package org.radianite.prg3javafxsistemmarketingperumahan.Controller.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.util.StringConverter;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Perumahan;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Roles;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class updateUser extends Library implements Initializable {
    @FXML
    private TextField txtUsn,txtPass,txtName,txtEmail,txtAge;
    @FXML
    private TextArea txtAddress;
    @FXML private Label validationLabel;
    @FXML private Label validationpass;
    @FXML private Label validationEmail;
    @FXML
    private ComboBox<Perumahan> cbResidence;
    @FXML
    private ComboBox<Roles> cbRole;
    @FXML
    private RadioButton rbMale,rbFemale;
    private ObservableList<Perumahan> listPerum = FXCollections.observableArrayList();
    private ObservableList<User> listUser = FXCollections.observableArrayList();
    private ObservableList<Roles> listRole = FXCollections.observableArrayList();
    @FXML
    private Button btnFile;
    @FXML
    private Label LabFile;
    private File file = null;
    private Image image;
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

        txtUsn.textProperty().addListener((observable, oldValue, newValue) -> validateUsername(newValue));
        txtPass.textProperty().addListener((observable, oldValue, newValue) -> validatePassword(newValue));
        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> validateEmail(newValue));
        txtName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s]*")) {
                txtName.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });
        txtAge.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtAge.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 0 && Integer.parseInt(newValue) < 18) {
                warningBox(btnFile, "Umur harus di atas 18 tahun.");
            }
        });
    }

    private int validateEmail(String email) {
        validationEmail.setOpacity(1);
        if (email.isEmpty()) {
            validationEmail.setText("");
            return 1;
        }

        if (!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            validationEmail.setText("Invalid email format.");
            validationEmail.setStyle("-fx-text-fill: red; -fx-font-size: 12;");
            return 0;
        } else {
            validationEmail.setText("Email is valid.");
            validationEmail.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
            return 1;
        }

    }

    private int validateUsername(String username) {
        validationLabel.setOpacity(1);
        if (username.isEmpty()) {
            validationLabel.setText("");
            return 1;
        }

        if (username.length() < 8) {
            validationLabel.setText("Username must be at least 8 characters.");
            validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12;");
            return 0;
        }

        boolean isTaken = false;
        for (User user : listUser) {
            if (user.getUsn().equals(username)) {
                isTaken = true;
                break;
            }
        }
        if (isTaken) {
            validationLabel.setText("Username is already taken.");
            validationLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12;");
            return 0;
        } else {
            validationLabel.setText("Username is available.");
            validationLabel.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
            return 1;
        }
    }

    private int validatePassword(String password) {
        validationpass.setOpacity(1);
        if (password.isEmpty()) {
            validationpass.setText("");
            return 1;
        }else
        if (password.length() < 8) {
            validationpass.setText("Password must be at least 8 characters.");
            validationpass.setStyle("-fx-text-fill: red; -fx-font-size: 12;");

            return 0;

        } else {
            validationpass.setText("Password is valid.");
            validationpass.setStyle("-fx-text-fill: green; -fx-font-size: 12;");
            return 1;
        }
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
        image = data.getFoto();
    }

    private void setPSelectedCbBox(String data){
        for (Perumahan item : cbResidence.getItems()){
            if (item.getId().equals(data)){
                cbResidence.getSelectionModel().select(item);
            }
        }
    }

    private void setRSelectedCbBox(String data){
        for (Roles item : cbRole.getItems()){
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

    private void loadRole(){
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

    public void onActionUpdate(ActionEvent actionEvent) {
        if (isEmpty())
        {
            fillBox(btnFile,"Please fill all the fields");
            return;
        }
        Toggle selected = group.getSelectedToggle();
        RadioButton val = (RadioButton) selected;
        try{
            Database connect = new Database();
            String query = "EXEC sp_updateUser ?,?,?,?,?,?,?,?,?,?";
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
            connect.pstat.setBytes(10,file != null ? imageToByte(file) : convertImageToBytes(image));
            connect.pstat.executeUpdate();
            connect.pstat.close();
            successBox(btnFile,"User has been updated");
            loadPage(actionEvent,"viewUser");
        }catch (SQLException | IOException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public boolean isEmpty()
    {
        if (txtUsn.getText().isEmpty() || txtPass.getText().isEmpty() || txtName.getText().isEmpty() || txtEmail.getText().isEmpty() || txtAddress.getText().isEmpty() || !rbMale.isSelected() && !rbFemale.isSelected() || txtAge.getText().isEmpty()){
            return true;
        }
        return false;
    }

    public void onActionFile(ActionEvent actionEvent) {
        file = imageChooser(btnFile);
        LabFile.setText(file.getName());
    }
}
