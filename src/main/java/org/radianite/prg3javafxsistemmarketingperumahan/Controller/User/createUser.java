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
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.awt.event.KeyEvent;
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
    @FXML private Label validationLabel;
    @FXML private Label validationpass;
    @FXML private Label validationEmail;
    private ObservableList<User> listUser = FXCollections.observableArrayList();
    File file;

    private ObservableList<Perumahan> listPerum = FXCollections.observableArrayList();
    private ObservableList<Roles> listRole = FXCollections.observableArrayList();
    ToggleGroup group = new ToggleGroup();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validationLabel.setOpacity(0);
        validationpass.setOpacity(0);
        validationEmail.setOpacity(0);
        loadPerumahan();
        loadRoles();
        loadData();
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
    public void loadData(){
        listUser.clear();
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            String query = "EXEC sp_viewUser";
            connect.result = connect.stat.executeQuery(query);
            while(connect.result.next()){
                listUser.add(new User(connect.result.getString("username"),
                        connect.result.getString("password"),
                        connect.result.getString("id_perumahan"),
                        connect.result.getString("id_role"),
                        connect.result.getString("nama_lengkap"),
                        connect.result.getString("email"),
                        connect.result.getString("alamat"),
                        connect.result.getString("jenis_kelamin"),
                        connect.result.getInt("umur"),
                        convertToImage(connect.result.getBytes("photo")),
                        connect.result.getString("nama_perumahan"),
                        connect.result.getString("nama_role"),
                        connect.result.getInt("status") // Tambahkan status
                ));
            }
            connect.result.close();
            connect.stat.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public void onActionSave(ActionEvent actionEvent) {
        String username = txtUsn.getText();
        String password = txtPass.getText();

        if (isEmpty())
        {
            fillBox(btnFile,"Please fill all the fields");
            return;
        }else
        if (validateUsername(username) != 1) {
            fillBox(btnFile, "Invalid username. Ensure it is between 6-12 characters long.");
            return;
        }else
        if (validatePassword(password) != 1) {
            fillBox(btnFile, "Invalid password. Ensure it is at least 8 characters long and contains uppercase letters, lowercase letters, and numbers.");
            return;
        }else
        if (validateEmail(txtEmail.getText()) != 1) {
            fillBox(btnFile, "Invalid email.");
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
            successBox(btnFile,"Success");
            clear();
        }catch (SQLException | IOException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public boolean isEmpty()
    {
        if (txtUsn.getText().isEmpty() && txtPass.getText().isEmpty() && txtName.getText().isEmpty() && txtEmail.getText().isEmpty() && txtAddress.getText().isEmpty() && !rbMale.isSelected() && !rbFemale.isSelected() && txtAge.getText().isEmpty() && file == null){
            return true;
        }
        return false;
    }

    public void onActionFile(ActionEvent actionEvent) {
        file = imageChooser(btnFile);
        LabFile.setText(file.getName());
    }
}
