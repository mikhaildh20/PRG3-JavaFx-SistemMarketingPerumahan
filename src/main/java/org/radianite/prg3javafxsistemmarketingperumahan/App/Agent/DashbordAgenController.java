package org.radianite.prg3javafxsistemmarketingperumahan.App.Agent;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Admin.*;
import org.radianite.prg3javafxsistemmarketingperumahan.App.HelloApplication;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.util.ArrayList;

public class DashbordAgenController {
    ArrayList<User> userList = new ArrayList<>();
    @FXML
    private AnchorPane GroupMenu;
    @FXML
   private AnchorPane btnsignout;

    public void setDataList(User data) {
        userList.add(data);
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
                } else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Developer/DasboardManageDeveloper.fxml")) {
                    DashboardManageDeveloperController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                } else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Perumahan/DasboardManageHousingArea.fxml")) {
                    DashboardManageHousingAreaController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                } else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/TipeRumah/DasboardManageHouseType.fxml")) {
                    DashboardManageHouseTypeController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                }
                GroupMenu.setScaleX(0.5);
                GroupMenu.setScaleY(0.5);

                ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), GroupMenu);
                scaleTransition.setToX(1.0);
                scaleTransition.setToY(1.0);
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), GroupMenu);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeIn);
                parallelTransition.setInterpolator(Interpolator.EASE_BOTH);
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
