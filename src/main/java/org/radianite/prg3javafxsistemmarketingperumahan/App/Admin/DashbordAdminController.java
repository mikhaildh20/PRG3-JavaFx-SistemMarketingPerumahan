package org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.util.List;

public class DashbordAdminController {
    private List<User> users;

    @FXML
    private AnchorPane Group1;

    @FXML
    private AnchorPane GroupMenu;
    @FXML
    private ImageView Button1;
    @FXML
    private AnchorPane btnDashboard;
    @FXML
    private AnchorPane btnHouse;
    @FXML
    private AnchorPane btnShophouse;
    @FXML
    private AnchorPane btnplot;
    @FXML
    private AnchorPane btnsignout;

    private AnchorPane loadFXMLWithController(String fxmlPath, List<User> users) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane pane = loader.load();


            // Mendapatkan controller yang terkait dengan FXML
            AncoreDasboardUtama controller = loader.getController();

            // Mengirimkan data ke controller
            if (controller != null) {
                controller.setUsersAdmin(users);
            } else {
                System.err.println("Controller is null!");
            }

            return pane;
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the FXML file: " + fxmlPath);
            return null;
        }
    }


    public void setUsersAdmin(List<User> users) {
        this.users = users;
    }

    public void Animasi(){
        Group1.setTranslateX(-100);
        Group1.setOpacity(0.0);
        Button1.setOpacity(0.0);
        Button1.setTranslateY(-100);
        btnHouse.setOpacity(0.0);
        btnHouse.setTranslateY(-100);
        btnShophouse.setOpacity(0.0);
        btnShophouse.setTranslateY(-200);
        btnplot.setOpacity(0.0);
        btnplot.setTranslateY(-300);
        btnsignout.setOpacity(0.0);
/*        groupLoginFild.setTranslateY(-100);
        Image2.setOpacity(0.0);
        Image3.setOpacity(0.0);
        Image4.setOpacity(0.0);*/

        // Create TranslateTransition for GroupWelcome
        TranslateTransition SwipeGroup1 = new TranslateTransition();
        SwipeGroup1.setNode(Group1);
        SwipeGroup1.setDuration(Duration.seconds(1));
        SwipeGroup1.setToX(0);

        // Create FadeTransition for GroupWelcome
        FadeTransition FadeGroup1 = new FadeTransition();
        FadeGroup1.setNode(Group1);
        FadeGroup1.setDuration(Duration.seconds(2));
        FadeGroup1.setFromValue(0.0);
        FadeGroup1.setToValue(1.0);

        TranslateTransition SwipeButton1 = new TranslateTransition();
        SwipeButton1.setNode(Button1);
        SwipeButton1.setDuration(Duration.seconds(1.5));
        SwipeButton1.setToY(0);

        TranslateTransition SwipeButton2 = new TranslateTransition();
        SwipeButton2.setNode(btnHouse);
        SwipeButton2.setDuration(Duration.seconds(1.5));
        SwipeButton2.setToY(0);

        TranslateTransition SwipeButton3 = new TranslateTransition();
        SwipeButton3.setNode(btnShophouse);
        SwipeButton3.setDuration(Duration.seconds(1.5));
        SwipeButton3.setToY(0);

        TranslateTransition SwipeButton4 = new TranslateTransition();
        SwipeButton4.setNode(btnplot);
        SwipeButton4.setDuration(Duration.seconds(1.5));
        SwipeButton4.setToY(0);

        FadeTransition FadeButton1 = new FadeTransition();
        FadeButton1.setNode(Button1);
        FadeButton1.setDuration(Duration.seconds(2));
        FadeButton1.setFromValue(0.0);
        FadeButton1.setToValue(1.0);

        FadeTransition FadeButton2 = new FadeTransition();
        FadeButton2.setNode(btnHouse);
        FadeButton2.setDuration(Duration.seconds(2));
        FadeButton2.setFromValue(0.0);
        FadeButton2.setToValue(1.0);

        FadeTransition FadeButton3 = new FadeTransition();
        FadeButton3.setNode(btnShophouse);
        FadeButton3.setDuration(Duration.seconds(2));
        FadeButton3.setFromValue(0.0);
        FadeButton3.setToValue(1.0);

        FadeTransition FadeButton4 = new FadeTransition();
        FadeButton4.setNode(btnplot);
        FadeButton4.setDuration(Duration.seconds(2));
        FadeButton4.setFromValue(0.0);
        FadeButton4.setToValue(1.0);

        FadeTransition FadeButton5 = new FadeTransition();
        FadeButton5.setNode(btnsignout);
        FadeButton5.setDuration(Duration.seconds(2));
        FadeButton5.setFromValue(0.0);
        FadeButton5.setToValue(1.0);

     /*   ParallelTransition parallelButton1 = new ParallelTransition(SwipeButton1,FadeButton1);
        ParallelTransition parallelButton2 = new ParallelTransition(SwipeButton2,FadeButton2);
        ParallelTransition parallelButton3 = new ParallelTransition(SwipeButton3,FadeButton3);
        ParallelTransition parallelButton4 = new ParallelTransition(SwipeButton4,FadeButton4);

        SequentialTransition SequentialButton = new SequentialTransition(parallelButton1,parallelButton2,parallelButton3,parallelButton4);
        SequentialButton.play();*/

        ParallelTransition parallelButton = new ParallelTransition(SwipeButton1,FadeButton1,SwipeButton2,FadeButton2,SwipeButton3,FadeButton3,SwipeButton4,FadeButton4,FadeButton5);
        parallelButton.play();

        ParallelTransition parallelTransition = new ParallelTransition(SwipeGroup1, FadeGroup1);
        parallelTransition.play();

    }
    @FXML
    public void button1Clicked() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/InputDeveloper.fxml");
    }

    @FXML
    public void btnDashboardclick() {
        GroupMenu.getChildren().setAll(loadFXMLWithController("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agen/MenuUtama.fxml",users));
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
        GroupMenu.getChildren().setAll(loadFXMLWithController("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Agen/MenuUtama.fxml",users));
    }

/*    @FXML
    public void logout() {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.close();
    }*/



    private void setPane(String fxml) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(fxml));
            GroupMenu.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
