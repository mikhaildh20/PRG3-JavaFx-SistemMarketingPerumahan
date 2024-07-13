package org.radianite.prg3javafxsistemmarketingperumahan.App.Agent;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Admin.*;
import org.radianite.prg3javafxsistemmarketingperumahan.App.HelloApplication;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DashbordAgenController {
    ArrayList<User> userList = new ArrayList<>();
    @FXML
    private AnchorPane GroupMenu;
    @FXML
   private AnchorPane btnsignout;
    @FXML private Circle imageCircle;
    @FXML private Text txtLastOnline;
    @FXML private Text textNama;
    Database connection = new Database();

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
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agent/TransaksiRumah.fxml");
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
                if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/MainDashboard.fxml")) {
                    MainDashboardController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                } else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agent/TransaksiRumah.fxml")) {
                    TransaksiRumahController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                } else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agent/TransaksiRuko.fxml")) {
                    TransaksiRukoController controller = loader.getController();
                    controller.setDataList(userList.get(0));
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
