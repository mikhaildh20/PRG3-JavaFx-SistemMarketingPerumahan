package org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainDashboardController {
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

    @FXML private Text txtJumlahDev;
    @FXML private Text txtJumlahHousing;
    @FXML private Text txtJumlahRole;
    @FXML private Text txtJumlahUser;
    @FXML private Text txtJumlahShopHouse;
    @FXML private Text txtJumlahHouseType;
    @FXML private Text txtJumlahBank;
    @FXML private Text txtJumlahHouse;
    @FXML private AnchorPane cardDev, cardHousing, cardRole, cardUser, cardRuko, cardHouseType, cardBank, cardHouse,cardType;


    private Database connection;

    public void initialize() {
        connection = new Database();
        startClock();
        txtJumlahDev.setText(String.valueOf(callUDFDev()));
        txtJumlahHouseType.setText(String.valueOf(callUDFCountMsTipeRumah()));
        txtJumlahHousing.setText(String.valueOf(callUDFCountMsPerumahan()));
        txtJumlahHouse.setText(String.valueOf(callUDFCountMsRumah()));
        txtJumlahRole.setText(String.valueOf(callUDFCountMsRole()));
        txtJumlahUser.setText(String.valueOf(callUDFCountMsUser()));
        txtJumlahShopHouse.setText(String.valueOf(callUDFCountMsRuko()));
        txtJumlahBank.setText(String.valueOf(callUDFCountMsBank()));
    }
    public void setDataList(User data) {
        userList.add(data);
        if (!userList.isEmpty()) {
            txtHalo.setText("Halo, " + userList.get(0).getName());
        }
    }



    private int callUDFDev() {
        int count = 0;
        try {
            String udfQuery = "{? = CALL udfCountMsDeveloper()}";
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

    private int callUDFCountMsTipeRumah() {
        int count = 0;
        try {
            String udfQuery = "{? = CALL dbo.udfCountMsTipeRumah()}";
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

// Tambahkan metode lainnya sesuai dengan kebutuhan UDF yang ingin Anda panggil
private int callUDFCountMsRole() {
    int count = 0;
    try {
        String udfQuery = "{? = CALL dbo.udfCountMsRole()}";
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

    private int callUDFCountMsUser() {
        int count = 0;
        try {
            String udfQuery = "{? = CALL dbo.udfCountMsUser()}";
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

    private int callUDFCountMsRumah() {
        int count = 0;
        try {
            String udfQuery = "{? = CALL dbo.udfCountMsRumah()}";
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

    private int callUDFCountMsRuko() {
        int count = 0;
        try {
            String udfQuery = "{? = CALL dbo.udfCountMsRuko()}";
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

    private int callUDFCountMsBank() {
        int count = 0;
        try {
            String udfQuery = "{? = CALL dbo.udfCountMsBank()}";
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
