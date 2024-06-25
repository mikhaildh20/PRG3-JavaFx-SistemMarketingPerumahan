package org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Ruko;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainDashboardController {

    private List<User> users;

    @FXML
    private Text textNama;
    @FXML
    private Text DasbordNama;
    @FXML
    private AnchorPane Group2;
    @FXML
    private AnchorPane Group3;
    @FXML
    private AnchorPane Group4;
    @FXML
    private AnchorPane Group5;
    @FXML
    private AnchorPane Group6;
    @FXML
    private AnchorPane Group7;
    @FXML
    private AnchorPane GroupMenu;
    @FXML
    private Text tglLabel;
    @FXML
    private Text txtHalo;
    Database connection = new Database();
    ArrayList<User> userList = new ArrayList<>();
    public void initialize() {
        startClock();
    }
    public void setDataList(User data){
        userList.add(data);
        txtHalo.setText("Halo, "+userList.get(0).getName());
        textNama.setText(userList.get(0).getName());
    }

    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            tglLabel.setText(LocalDateTime.now().format(formatter));
        }),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }
}
