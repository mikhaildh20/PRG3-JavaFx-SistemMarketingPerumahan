package org.radianite.prg3javafxsistemmarketingperumahan.App.Agent;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.App.HelloApplication;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Cicilan;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DashbordAgenController {
    ArrayList<User> userList = new ArrayList<>();
    @FXML
    private AnchorPane Group1;
    @FXML
    private AnchorPane GroupMenu;
    @FXML
    private AnchorPane btnDashboard;
    @FXML
    private AnchorPane btnsignout;
    @FXML
    private AnchorPane btntrRumah;
    @FXML
    private AnchorPane btntrRuko;
    @FXML
    private AnchorPane btntrCicilan;

    @FXML private Circle imageCircle;
    @FXML private Text txtLastOnline;
    @FXML private Text textNama;
    Database connection = new Database();

    public void Animasi(){
        Group1.setTranslateX(-100);
        Group1.setOpacity(0.0);
        btntrRumah.setOpacity(0.0);
        btntrRumah.setTranslateY(-100);
        btntrRuko.setOpacity(0.0);
        btntrRuko.setTranslateY(-200);
        btntrCicilan.setOpacity(0.0);
        btntrCicilan.setTranslateY(-300);
        btnsignout.setOpacity(0.0);

        // Create TranslateTransition for GroupWelcome
        TranslateTransition SwipeGroup1 = new TranslateTransition();
        SwipeGroup1.setNode(Group1);
        SwipeGroup1.setDuration(Duration.seconds(1));
        SwipeGroup1.setToX(0);

        // Create FadeTransition for GroupWelcome
        FadeTransition FadeGroup1 = new FadeTransition();
        FadeGroup1.setNode(Group1);
        FadeGroup1.setDuration(Duration.seconds(1));
        FadeGroup1.setFromValue(0.0);
        FadeGroup1.setToValue(1.0);

        TranslateTransition SwipeButton2 = new TranslateTransition();
        SwipeButton2.setNode(btntrRumah);
        SwipeButton2.setDuration(Duration.seconds(1));
        SwipeButton2.setToY(0);

        TranslateTransition SwipeButton3 = new TranslateTransition();
        SwipeButton3.setNode(btntrRuko);
        SwipeButton3.setDuration(Duration.seconds(1));
        SwipeButton3.setToY(0);

        TranslateTransition SwipeButton4 = new TranslateTransition();
        SwipeButton4.setNode(btntrCicilan);
        SwipeButton4.setDuration(Duration.seconds(1));
        SwipeButton4.setToY(0);

        TranslateTransition SwipeButton5 = new TranslateTransition();
        SwipeButton5.setNode(btnsignout);
        SwipeButton5.setDuration(Duration.seconds(1));
        SwipeButton5.setToY(0);
        ;



        FadeTransition FadeButton2 = new FadeTransition();
        FadeButton2.setNode(btntrRumah);
        FadeButton2.setDuration(Duration.seconds(2));
        FadeButton2.setFromValue(0.0);
        FadeButton2.setToValue(1.0);

        FadeTransition FadeButton3 = new FadeTransition();
        FadeButton3.setNode(btntrRuko);
        FadeButton3.setDuration(Duration.seconds(2));
        FadeButton3.setFromValue(0.0);
        FadeButton3.setToValue(1.0);

        FadeTransition FadeButton4 = new FadeTransition();
        FadeButton4.setNode(btntrCicilan);
        FadeButton4.setDuration(Duration.seconds(2));
        FadeButton4.setFromValue(0.0);
        FadeButton4.setToValue(1.0);

        FadeTransition FadeButton5 = new FadeTransition();
        FadeButton5.setNode(btnsignout);
        FadeButton5.setDuration(Duration.seconds(2));
        FadeButton5.setFromValue(0.0);
        FadeButton5.setToValue(1.0);



        ParallelTransition parallelButton = new ParallelTransition(SwipeButton2,SwipeButton3,SwipeButton4,SwipeButton5,FadeButton2,FadeButton3,FadeButton4,FadeButton5);
        parallelButton.play();

        ParallelTransition parallelTransition = new ParallelTransition(SwipeGroup1, FadeGroup1);
        parallelTransition.play();

    }

    public void setDataList(User data) {
        userList.add(data);
        if (!userList.isEmpty()) {
            imageCircle.setFill(new ImagePattern(userList.get(0).getFoto()));
            textNama.setText(userList.get(0).getName());
        }
        try {
            String queryRole = "SELECT logDate FROM LoginLog WHERE username = ? ORDER BY logDate DESC";
            connection.pstat = connection.conn.prepareStatement(queryRole);
            connection.pstat.setString(1, userList.get(0).getUsn());
            connection.result = connection.pstat.executeQuery();
            connection.result.next();
            String logDate = connection.result.getString("logDate");
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm ");
            LocalDateTime dateTime = LocalDateTime.parse(logDate, inputFormatter);
            txtLastOnline.setText(dateTime.format(outputFormatter));
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        try{
            Database connect = new Database();
            String queryinputlog = "EXEC sp_inputLogin ?";
            connect.pstat = connect.conn.prepareStatement(queryinputlog);
            connect.pstat.setString(1, data.getUsn());
            connect.pstat.executeUpdate();
            connect.pstat.close();
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    @FXML
    public void initialize() {
/*
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agent/MainDashboard.fxml");
*/      Animasi();
        btnDashboard();
    }
    private void setPane(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent pane = loader.load();
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), GroupMenu);
            GroupMenu.setOpacity(1.0);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(eventFadeOut -> {
                GroupMenu.getChildren().setAll(pane);
                if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agent/MainDashboard.fxml")) {
                    MainDashboardControllerAgen controller = loader.getController();
                    controller.setDataList(userList.get(0));
                } else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agent/TransaksiRumah.fxml")) {
                    TransaksiRumahController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                } else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agent/TransaksiRuko.fxml")) {
                    TransaksiRukoController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                } else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agent/viewCicilan.fxml")) {
                    /*Cicilan controller = loader.getController();
                    controller.setDataList(userList.get(0));*/
                }
                GroupMenu.setTranslateX(-50);
                TranslateTransition translate = new TranslateTransition(Duration.seconds(0.5), GroupMenu);
                translate.setToX(0);
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), GroupMenu);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                ParallelTransition parallelTransition = new ParallelTransition(translate, fadeIn);
                parallelTransition.play();
            });
            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void btntransaksiRumah() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agent/TransaksiRumah.fxml");
        btnDashboard.getStyleClass().removeAll("buttonDashboard-on");
        btnDashboard.getStyleClass().add("buttonDashboard-off");

        btntrRumah.getStyleClass().removeAll("buttonDashboard-off");
        btntrRumah.getStyleClass().add("buttonDashboard-on");

        btntrRuko.getStyleClass().removeAll("buttonDashboard-on");
        btntrRuko.getStyleClass().add("buttonDashboard-off");

        btntrCicilan.getStyleClass().removeAll("buttonDashboard-on");
        btntrCicilan.getStyleClass().add("buttonDashboard-off");
    }
    @FXML
    public void btnDashboard() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agent/MainDashboard.fxml");
        btnDashboard.getStyleClass().removeAll("buttonDashboard-0ff");
        btnDashboard.getStyleClass().add("buttonDashboard-on");

        btntrRumah.getStyleClass().removeAll("buttonDashboard-on");
        btntrRumah.getStyleClass().add("buttonDashboard-off");

        btntrRuko.getStyleClass().removeAll("buttonDashboard-on");
        btntrRuko.getStyleClass().add("buttonDashboard-off");

        btntrCicilan.getStyleClass().removeAll("buttonDashboard-on");
        btntrCicilan.getStyleClass().add("buttonDashboard-off");
    }
    @FXML
    public void btntransaksiRuko() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agent/TransaksiRuko.fxml");
        btnDashboard.getStyleClass().removeAll("buttonDashboard-on");
        btnDashboard.getStyleClass().add("buttonDashboard-off");

        btntrRumah.getStyleClass().removeAll("buttonDashboard-on");
        btntrRumah.getStyleClass().add("buttonDashboard-off");

        btntrRuko.getStyleClass().removeAll("buttonDashboard-off");
        btntrRuko.getStyleClass().add("buttonDashboard-on");

        btntrCicilan.getStyleClass().removeAll("buttonDashboard-on");
        btntrCicilan.getStyleClass().add("buttonDashboard-off");
    }
    public void btntransaksiCicilan() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agent/viewCicilan.fxml");
        btnDashboard.getStyleClass().removeAll("buttonDashboard-on");
        btnDashboard.getStyleClass().add("buttonDashboard-off");

        btntrRumah.getStyleClass().removeAll("buttonDashboard-on");
        btntrRumah.getStyleClass().add("buttonDashboard-off");

        btntrRuko.getStyleClass().removeAll("buttonDashboard-on");
        btntrRuko.getStyleClass().add("buttonDashboard-off");

        btntrCicilan.getStyleClass().removeAll("buttonDashboard-off");
        btntrCicilan.getStyleClass().add("buttonDashboard-on");
    }
    public void btnlog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/radianite/prg3javafxsistemmarketingperumahan/App/Login/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("App");
            stage.setScene(scene);
            stage.show();
            btnsignout.getScene().getWindow().hide();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
