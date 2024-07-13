package org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.App.HelloApplication;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DashbordAdminController {
    @FXML
    Text text;
    @FXML
    private AnchorPane Group1;
    @FXML
    private AnchorPane GroupMenu;
    @FXML
    private AnchorPane btnDashboard;
    @FXML
    private AnchorPane btnHouse;
    @FXML
    private AnchorPane btnShopHouse;
    @FXML
    private AnchorPane btnPlot;
    @FXML
    private AnchorPane btnsignout;
    @FXML
    private AnchorPane btnUser;
    @FXML
    private AnchorPane btnDeveloper;
    @FXML
    private AnchorPane btnHousingArea;
    @FXML
    private AnchorPane btnRole;
    @FXML
    private AnchorPane btnHouseType;
    @FXML private Circle imageCircle;
    @FXML private Text txtLastOnline;
    @FXML private Text textNama;
    private ArrayList<User> userList = new ArrayList<>();
    @FXML
    private Text tglLabel;
    Database connection = new Database();


    public void Animasi(){
        Group1.setTranslateX(-100);
        Group1.setOpacity(0.0);
        btnDeveloper.setOpacity(0.0);
        btnDeveloper.setTranslateY(-100);
        btnHousingArea.setOpacity(0.0);
        btnHousingArea.setTranslateY(-200);
        btnRole.setOpacity(0.0);
        btnRole.setTranslateY(-300);
        btnHouseType.setOpacity(0.0);
        btnHouseType.setTranslateY(-400);
        btnHouse.setOpacity(0.0);
        btnHouse.setTranslateY(-500);
        btnShopHouse.setOpacity(0.0);
        btnShopHouse.setTranslateY(-600);
        btnPlot.setOpacity(0.0);
        btnPlot.setTranslateY(-700);
        btnUser.setOpacity(0.0);
        btnUser.setTranslateY(-800);
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
        SwipeButton2.setNode(btnHouse);
        SwipeButton2.setDuration(Duration.seconds(1));
        SwipeButton2.setToY(0);

        TranslateTransition SwipeButton3 = new TranslateTransition();
        SwipeButton3.setNode(btnShopHouse);
        SwipeButton3.setDuration(Duration.seconds(1));
        SwipeButton3.setToY(0);

        TranslateTransition SwipeButton4 = new TranslateTransition();
        SwipeButton4.setNode(btnPlot);
        SwipeButton4.setDuration(Duration.seconds(1));
        SwipeButton4.setToY(0);

        TranslateTransition SwipeButton5 = new TranslateTransition();
        SwipeButton5.setNode(btnsignout);
        SwipeButton5.setDuration(Duration.seconds(1));
        SwipeButton5.setToY(0);

        TranslateTransition SwipeButton6 = new TranslateTransition();
        SwipeButton6.setNode(btnUser);
        SwipeButton6.setDuration(Duration.seconds(1));
        SwipeButton6.setToY(0);

        TranslateTransition SwipeButton7 = new TranslateTransition();
        SwipeButton7.setNode(btnDeveloper);
        SwipeButton7.setDuration(Duration.seconds(1));
        SwipeButton7.setToY(0);

        TranslateTransition SwipeButton8 = new TranslateTransition();
        SwipeButton8.setNode(btnHousingArea);
        SwipeButton8.setDuration(Duration.seconds(1));
        SwipeButton8.setToY(0);

        TranslateTransition SwipeButton9 = new TranslateTransition();
        SwipeButton9.setNode(btnRole);
        SwipeButton9.setDuration(Duration.seconds(1));
        SwipeButton9.setToY(0);

        TranslateTransition SwipeButton10 = new TranslateTransition();
        SwipeButton10.setNode(btnHouseType);
        SwipeButton10.setDuration(Duration.seconds(1));
        SwipeButton10.setToY(0);

        FadeTransition FadeButton2 = new FadeTransition();
        FadeButton2.setNode(btnHouse);
        FadeButton2.setDuration(Duration.seconds(2));
        FadeButton2.setFromValue(0.0);
        FadeButton2.setToValue(1.0);

        FadeTransition FadeButton3 = new FadeTransition();
        FadeButton3.setNode(btnShopHouse);
        FadeButton3.setDuration(Duration.seconds(2));
        FadeButton3.setFromValue(0.0);
        FadeButton3.setToValue(1.0);

        FadeTransition FadeButton4 = new FadeTransition();
        FadeButton4.setNode(btnPlot);
        FadeButton4.setDuration(Duration.seconds(2));
        FadeButton4.setFromValue(0.0);
        FadeButton4.setToValue(1.0);

        FadeTransition FadeButton5 = new FadeTransition();
        FadeButton5.setNode(btnsignout);
        FadeButton5.setDuration(Duration.seconds(2));
        FadeButton5.setFromValue(0.0);
        FadeButton5.setToValue(1.0);

        FadeTransition FadeButton6 = new FadeTransition();
        FadeButton6.setNode(btnUser);
        FadeButton6.setDuration(Duration.seconds(2));
        FadeButton6.setFromValue(0.0);
        FadeButton6.setToValue(1.0);

        FadeTransition FadeButton7 = new FadeTransition();
        FadeButton7.setNode(btnDeveloper);
        FadeButton7.setDuration(Duration.seconds(2));
        FadeButton7.setFromValue(0.0);
        FadeButton7.setToValue(1.0);

        FadeTransition FadeButton8 = new FadeTransition();
        FadeButton8.setNode(btnHousingArea);
        FadeButton8.setDuration(Duration.seconds(2));
        FadeButton8.setFromValue(0.0);
        FadeButton8.setToValue(1.0);

        FadeTransition FadeButton9 = new FadeTransition();
        FadeButton9.setNode(btnRole);
        FadeButton9.setDuration(Duration.seconds(2));
        FadeButton9.setFromValue(0.0);
        FadeButton9.setToValue(1.0);

        FadeTransition FadeButton10 = new FadeTransition();
        FadeButton10.setNode(btnHouseType);
        FadeButton10.setDuration(Duration.seconds(2));
        FadeButton10.setFromValue(0.0);
        FadeButton10.setToValue(1.0);

        ParallelTransition parallelButton = new ParallelTransition(SwipeButton2,SwipeButton3,SwipeButton4,SwipeButton5,SwipeButton6,SwipeButton7,SwipeButton8,SwipeButton9,SwipeButton10,FadeButton2,FadeButton3,FadeButton4,FadeButton5,FadeButton6,FadeButton7,FadeButton8,FadeButton9,FadeButton10);
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
    protected void btnDeveloperclick() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Developer/DasboardManageDeveloper.fxml");
        btnDashboard.getStyleClass().removeAll("buttonDashboard-on");
        btnDashboard.getStyleClass().add("buttonDashboard-off");

        btnDeveloper.getStyleClass().removeAll("buttonDashboard-off");
        btnDeveloper.getStyleClass().add("buttonDashboard-on");

        btnRole.getStyleClass().removeAll("buttonDashboard-on");
        btnRole.getStyleClass().add("buttonDashboard-off");

        btnHouseType.getStyleClass().removeAll("buttonDashboard-on");
        btnHouseType.getStyleClass().add("buttonDashboard-off");

        btnHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnHouse.getStyleClass().add("buttonDashboard-off");

        btnUser.getStyleClass().removeAll("buttonDashboard-on");
        btnUser.getStyleClass().add("buttonDashboard-off");

        btnPlot.getStyleClass().removeAll("buttonDashboard-on");
        btnPlot.getStyleClass().add("buttonDashboard-off");

        btnHousingArea.getStyleClass().removeAll("buttonDashboard-on");
        btnHousingArea.getStyleClass().add("buttonDashboard-off");

        btnShopHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnShopHouse.getStyleClass().add("buttonDashboard-off");
    }
    @FXML
    protected void btnDashboardclick() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/MainDashboard.fxml");
        btnDashboard.getStyleClass().removeAll("buttonDashboard-off");
        btnDashboard.getStyleClass().add("buttonDashboard-on");

        btnDeveloper.getStyleClass().removeAll("buttonDashboard-on");
        btnDeveloper.getStyleClass().add("buttonDashboard-off");

        btnRole.getStyleClass().removeAll("buttonDashboard-on");
        btnRole.getStyleClass().add("buttonDashboard-off");

        btnHouseType.getStyleClass().removeAll("buttonDashboard-on");
        btnHouseType.getStyleClass().add("buttonDashboard-off");

        btnHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnHouse.getStyleClass().add("buttonDashboard-off");

        btnUser.getStyleClass().removeAll("buttonDashboard-on");
        btnUser.getStyleClass().add("buttonDashboard-off");

        btnPlot.getStyleClass().removeAll("buttonDashboard-on");
        btnPlot.getStyleClass().add("buttonDashboard-off");

        btnHousingArea.getStyleClass().removeAll("buttonDashboard-on");
        btnHousingArea.getStyleClass().add("buttonDashboard-off");

        btnShopHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnShopHouse.getStyleClass().add("buttonDashboard-off");
    }
    @FXML
    public void btnHousingAreaClick() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Perumahan/DasboardManageHousingArea.fxml");
        btnHousingArea.getStyleClass().removeAll("buttonDashboard-off");
        btnHousingArea.getStyleClass().add("buttonDashboard-on");

        btnDeveloper.getStyleClass().removeAll("buttonDashboard-on");
        btnDeveloper.getStyleClass().add("buttonDashboard-off");

        btnRole.getStyleClass().removeAll("buttonDashboard-on");
        btnRole.getStyleClass().add("buttonDashboard-off");

        btnHouseType.getStyleClass().removeAll("buttonDashboard-on");
        btnHouseType.getStyleClass().add("buttonDashboard-off");

        btnHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnHouse.getStyleClass().add("buttonDashboard-off");

        btnUser.getStyleClass().removeAll("buttonDashboard-on");
        btnUser.getStyleClass().add("buttonDashboard-off");

        btnPlot.getStyleClass().removeAll("buttonDashboard-on");
        btnPlot.getStyleClass().add("buttonDashboard-off");

        btnDashboard.getStyleClass().removeAll("buttonDashboard-on");
        btnDashboard.getStyleClass().add("buttonDashboard-off");

        btnShopHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnShopHouse.getStyleClass().add("buttonDashboard-off");
    }
    @FXML
    public void btnUserClick() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/User/DasboardManageUser.fxml");
        btnUser.getStyleClass().removeAll("buttonDashboard-off");
        btnUser.getStyleClass().add("buttonDashboard-on");

        btnDeveloper.getStyleClass().removeAll("buttonDashboard-on");
        btnDeveloper.getStyleClass().add("buttonDashboard-off");

        btnRole.getStyleClass().removeAll("buttonDashboard-on");
        btnRole.getStyleClass().add("buttonDashboard-off");

        btnHouseType.getStyleClass().removeAll("buttonDashboard-on");
        btnHouseType.getStyleClass().add("buttonDashboard-off");

        btnHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnHouse.getStyleClass().add("buttonDashboard-off");

        btnPlot.getStyleClass().removeAll("buttonDashboard-on");
        btnPlot.getStyleClass().add("buttonDashboard-off");

        btnDashboard.getStyleClass().removeAll("buttonDashboard-on");
        btnDashboard.getStyleClass().add("buttonDashboard-off");

        btnHousingArea.getStyleClass().removeAll("buttonDashboard-on");
        btnHousingArea.getStyleClass().add("buttonDashboard-off");

        btnShopHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnShopHouse.getStyleClass().add("buttonDashboard-off");
    }
    @FXML
    public void btnShopHouseClick() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Ruko/DasboardManageShopHouse.fxml");
        btnShopHouse.getStyleClass().removeAll("buttonDashboard-off");
        btnShopHouse.getStyleClass().add("buttonDashboard-on");

        btnDeveloper.getStyleClass().removeAll("buttonDashboard-on");
        btnDeveloper.getStyleClass().add("buttonDashboard-off");

        btnRole.getStyleClass().removeAll("buttonDashboard-on");
        btnRole.getStyleClass().add("buttonDashboard-off");

        btnHouseType.getStyleClass().removeAll("buttonDashboard-on");
        btnHouseType.getStyleClass().add("buttonDashboard-off");

        btnHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnHouse.getStyleClass().add("buttonDashboard-off");

        btnUser.getStyleClass().removeAll("buttonDashboard-on");
        btnUser.getStyleClass().add("buttonDashboard-off");

        btnPlot.getStyleClass().removeAll("buttonDashboard-on");
        btnPlot.getStyleClass().add("buttonDashboard-off");

        btnDashboard.getStyleClass().removeAll("buttonDashboard-on");
        btnDashboard.getStyleClass().add("buttonDashboard-off");

        btnHousingArea.getStyleClass().removeAll("buttonDashboard-on");
        btnHousingArea.getStyleClass().add("buttonDashboard-off");
    }
    @FXML
    public void btnRoleClick() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Role/DasboardManageRole.fxml");
        btnRole.getStyleClass().removeAll("buttonDashboard-off");
        btnRole.getStyleClass().add("buttonDashboard-on");

        btnDeveloper.getStyleClass().removeAll("buttonDashboard-on");
        btnDeveloper.getStyleClass().add("buttonDashboard-off");

        btnHouseType.getStyleClass().removeAll("buttonDashboard-on");
        btnHouseType.getStyleClass().add("buttonDashboard-off");

        btnHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnHouse.getStyleClass().add("buttonDashboard-off");

        btnUser.getStyleClass().removeAll("buttonDashboard-on");
        btnUser.getStyleClass().add("buttonDashboard-off");

        btnPlot.getStyleClass().removeAll("buttonDashboard-on");
        btnPlot.getStyleClass().add("buttonDashboard-off");

        btnDashboard.getStyleClass().removeAll("buttonDashboard-on");
        btnDashboard.getStyleClass().add("buttonDashboard-off");

        btnHousingArea.getStyleClass().removeAll("buttonDashboard-on");
        btnHousingArea.getStyleClass().add("buttonDashboard-off");

        btnShopHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnShopHouse.getStyleClass().add("buttonDashboard-off");
    }

    @FXML
    public void btnHouseTypeClick(){
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/TipeRumah/DasboardManageHouseType.fxml");
        btnRole.getStyleClass().removeAll("buttonDashboard-on");
        btnRole.getStyleClass().add("buttonDashboard-off");

        btnDeveloper.getStyleClass().removeAll("buttonDashboard-on");
        btnDeveloper.getStyleClass().add("buttonDashboard-off");

        btnHouseType.getStyleClass().removeAll("buttonDashboard-off");
        btnHouseType.getStyleClass().add("buttonDashboard-on");

        btnHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnHouse.getStyleClass().add("buttonDashboard-off");

        btnUser.getStyleClass().removeAll("buttonDashboard-on");
        btnUser.getStyleClass().add("buttonDashboard-off");

        btnPlot.getStyleClass().removeAll("buttonDashboard-on");
        btnPlot.getStyleClass().add("buttonDashboard-off");

        btnDashboard.getStyleClass().removeAll("buttonDashboard-on");
        btnDashboard.getStyleClass().add("buttonDashboard-off");

        btnHousingArea.getStyleClass().removeAll("buttonDashboard-on");
        btnHousingArea.getStyleClass().add("buttonDashboard-off");

        btnShopHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnShopHouse.getStyleClass().add("buttonDashboard-off");
    }
    @FXML
    public void btnHouseClick(){
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Rumah/DasboardManageHouse.fxml");
        btnRole.getStyleClass().removeAll("buttonDashboard-on");
        btnRole.getStyleClass().add("buttonDashboard-off");

        btnDeveloper.getStyleClass().removeAll("buttonDashboard-on");
        btnDeveloper.getStyleClass().add("buttonDashboard-off");

        btnHouseType.getStyleClass().removeAll("buttonDashboard-on");
        btnHouseType.getStyleClass().add("buttonDashboard-off");

        btnHouse.getStyleClass().removeAll("buttonDashboard-off");
        btnHouse.getStyleClass().add("buttonDashboard-on");

        btnUser.getStyleClass().removeAll("buttonDashboard-on");
        btnUser.getStyleClass().add("buttonDashboard-off");

        btnPlot.getStyleClass().removeAll("buttonDashboard-on");
        btnPlot.getStyleClass().add("buttonDashboard-off");

        btnDashboard.getStyleClass().removeAll("buttonDashboard-on");
        btnDashboard.getStyleClass().add("buttonDashboard-off");

        btnHousingArea.getStyleClass().removeAll("buttonDashboard-on");
        btnHousingArea.getStyleClass().add("buttonDashboard-off");

        btnShopHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnShopHouse.getStyleClass().add("buttonDashboard-off");
    }

    @FXML
    protected void btnBankclick() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Bank/DasboardManageBank.fxml");
        btnDashboard.getStyleClass().removeAll("buttonDashboard-on");
        btnDashboard.getStyleClass().add("buttonDashboard-off");

        btnDeveloper.getStyleClass().removeAll("buttonDashboard-on");
        btnDeveloper.getStyleClass().add("buttonDashboard-off");

        btnRole.getStyleClass().removeAll("buttonDashboard-on");
        btnRole.getStyleClass().add("buttonDashboard-off");

        btnHouseType.getStyleClass().removeAll("buttonDashboard-on");
        btnHouseType.getStyleClass().add("buttonDashboard-off");

        btnHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnHouse.getStyleClass().add("buttonDashboard-off");

        btnUser.getStyleClass().removeAll("buttonDashboard-on");
        btnUser.getStyleClass().add("buttonDashboard-off");

        btnPlot.getStyleClass().removeAll("buttonDashboard-off");
        btnPlot.getStyleClass().add("buttonDashboard-on");

        btnHousingArea.getStyleClass().removeAll("buttonDashboard-on");
        btnHousingArea.getStyleClass().add("buttonDashboard-off");

        btnShopHouse.getStyleClass().removeAll("buttonDashboard-on");
        btnShopHouse.getStyleClass().add("buttonDashboard-off");
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void initialize() {
        Animasi();
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/MainDashboard.fxml");
        btnDashboard.getStyleClass().removeAll("buttonDashboard-off");
        btnDashboard.getStyleClass().add("buttonDashboard-on");

    }

    @FXML
    public void btnlogout() {
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
    private void setPane(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent pane = loader.load();
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), GroupMenu);
            GroupMenu.setOpacity(0.0);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(eventFadeOut -> {
                GroupMenu.getChildren().setAll(pane);
                if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/MainDashboard.fxml")) {
                    MainDashboardController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                }else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Developer/DasboardManageDeveloper.fxml")) {
                    DashboardManageDeveloperController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                }else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Perumahan/DasboardManageHousingArea.fxml")) {
                    DashboardManageHousingAreaController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                }else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/TipeRumah/DasboardManageHouseType.fxml")) {
                    DashboardManageHouseTypeController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                }else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/User/DasboardManageUser.fxml")) {
                    DashboardManageUserController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                }else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Role/DasboardManageRole.fxml")) {
                    DashboardManageRoleController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                }else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Rumah/DasboardManageHouse.fxml")) {
                    DashboardManageHouseController controller = loader.getController();
                    controller.setDataList(userList.get(0));
                }else if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Ruko/DasboardManageShopHouse.fxml")) {
                    DashboardManageShopHouseController controller = loader.getController();
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





}
