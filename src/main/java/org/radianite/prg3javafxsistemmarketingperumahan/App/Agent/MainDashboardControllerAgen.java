package org.radianite.prg3javafxsistemmarketingperumahan.App.Agent;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainDashboardControllerAgen {
    private List<User> users;
    private ArrayList<User> userList = new ArrayList<>();

    @FXML private Text textNama;
    @FXML private Text DasbordNama;
    @FXML private AnchorPane Group2;
    @FXML private AnchorPane Group3;
    @FXML private AnchorPane Group4;
    @FXML private AnchorPane Group5;
    @FXML private AnchorPane Group6;
    @FXML private AnchorPane Group7;
    @FXML private AnchorPane GroupMenu;
    @FXML private Text tglLabel;
    @FXML private Text txtHalo;
    @FXML private Circle imageCircle;

    @FXML private Text txtTransaction;
    @FXML private Text txtIncome;


    private Database connection;

    public void initialize() {
        connection = new Database();
        startClock();



    }
    public void setDataList(User data) {
        userList.add(data);
            txtHalo.setText("Halo, " + userList.get(0).getName());
            txtTransaction.setText(String.valueOf(callUDFTotal(data.getUsn())));

    }

    private int callUDFTotal(String usn) {
        int count = 0;
        try {
            String udfQuery = "{? = CALL  fn_CountTotalRumahDanRukoByUsername(?)}";
            CallableStatement callableStatement = connection.conn.prepareCall(udfQuery);
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.setString(2, usn);
            callableStatement.execute();
            count = callableStatement.getInt(1);
            callableStatement.close();
        } catch (SQLException e) {
            showAlert("Error", "Error calling UDF", e.getMessage());
        }
        return count;
    }
    private int callUDFCountMsPerumahan() {
        int count = 0;
        try {
            String udfQuery = "{? = CALL dbo.udfCountMsPerumahan()}";
            CallableStatement callableStatement = connection.conn.prepareCall(udfQuery);
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();
            count = callableStatement.getInt(1);
            callableStatement.close();
        } catch (SQLException e) {
            showAlert("Error", "Error calling UDF", e.getMessage());
        }
        return count;
    }


    private void startClock() {
        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    tglLabel.setText(LocalDateTime.now().format(formatter));
                }),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }
    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
