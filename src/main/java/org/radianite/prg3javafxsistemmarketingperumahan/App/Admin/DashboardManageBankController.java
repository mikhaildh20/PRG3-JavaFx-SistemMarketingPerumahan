package org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;

import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardManageBankController {

    @FXML
    private TextField txtIdDeveloper;
    @FXML
    private TextField txtNamaDeveloper;
    @FXML
    private TableView tblDeveloper;
    @FXML
    private Text textNama;

    Database connection = new Database();

    @FXML
    private TableColumn<Developer, String> colIdDeveloper;
    @FXML
    private TableColumn<Developer, String> colNamaDeveloper;
    @FXML
    private TableColumn<Developer, Integer> colStatus;
    @FXML
    private TableColumn<Developer, Void> colAction;
    ArrayList<User> userList = new ArrayList<>();
    public void setDataList(User data){
        userList.add(data);
        textNama.setText(data.getName());
    }
    @FXML
    private void initialize() {

    }


}
