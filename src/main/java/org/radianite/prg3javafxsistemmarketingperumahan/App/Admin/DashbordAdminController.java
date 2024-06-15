package org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.util.List;

public class DashbordAdminController {
    private List<User> users;

    @FXML
    private Text texttest;
    @FXML
    private AnchorPane Group1;
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
    private ImageView Button1;
    @FXML
    private ImageView Button2;
    @FXML
    private ImageView Button3;
    @FXML
    private ImageView Button4;


    public void setUsersAdmin(List<User> users) {
        this.users = users;
        if (users != null && !users.isEmpty()) {
            texttest.setText(users.get(0).getName()+" (Admin)"); // Misal menampilkan nama user pertama
        }
    }

    public void Animasi(){
        Group1.setTranslateX(-100);
        Group1.setOpacity(0.0);
        Button1.setOpacity(0.0);
        Button1.setTranslateY(-100);
        Button2.setOpacity(0.0);
        Button2.setTranslateY(-200);
        Button3.setOpacity(0.0);
        Button3.setTranslateY(-300);
        Button4.setOpacity(0.0);
        Button4.setTranslateY(-400);
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

        FadeTransition FadeGroup2 = new FadeTransition();
        FadeGroup2.setNode(Group2);
        FadeGroup2.setDuration(Duration.seconds(2));
        FadeGroup2.setFromValue(0.0);
        FadeGroup2.setToValue(1.0);

        FadeTransition FadeGroup3 = new FadeTransition();
        FadeGroup3.setNode(Group3);
        FadeGroup3.setDuration(Duration.seconds(2));
        FadeGroup3.setFromValue(0.0);
        FadeGroup3.setToValue(1.0);

        FadeTransition FadeGroup4 = new FadeTransition();
        FadeGroup4.setNode(Group4);
        FadeGroup4.setDuration(Duration.seconds(2));
        FadeGroup4.setFromValue(0.0);
        FadeGroup4.setToValue(1.0);

        FadeTransition FadeGroup5 = new FadeTransition();
        FadeGroup5.setNode(Group5);
        FadeGroup5.setDuration(Duration.seconds(2));
        FadeGroup5.setFromValue(0.0);
        FadeGroup5.setToValue(1.0);

        FadeTransition FadeGroup6 = new FadeTransition();
        FadeGroup6.setNode(Group6);
        FadeGroup6.setDuration(Duration.seconds(2));
        FadeGroup6.setFromValue(0.0);
        FadeGroup6.setToValue(1.0);

        FadeTransition FadeGroup7 = new FadeTransition();
        FadeGroup7.setNode(Group7);
        FadeGroup7.setDuration(Duration.seconds(2));
        FadeGroup7.setFromValue(0.0);
        FadeGroup7.setToValue(1.0);

        TranslateTransition SwipeButton1 = new TranslateTransition();
        SwipeButton1.setNode(Button1);
        SwipeButton1.setDuration(Duration.seconds(1.5));
        SwipeButton1.setToY(0);

        TranslateTransition SwipeButton2 = new TranslateTransition();
        SwipeButton2.setNode(Button2);
        SwipeButton2.setDuration(Duration.seconds(1.5));
        SwipeButton2.setToY(0);

        TranslateTransition SwipeButton3 = new TranslateTransition();
        SwipeButton3.setNode(Button3);
        SwipeButton3.setDuration(Duration.seconds(1.5));
        SwipeButton3.setToY(0);

        TranslateTransition SwipeButton4 = new TranslateTransition();
        SwipeButton4.setNode(Button4);
        SwipeButton4.setDuration(Duration.seconds(1.5));
        SwipeButton4.setToY(0);

        FadeTransition FadeButton1 = new FadeTransition();
        FadeButton1.setNode(Button1);
        FadeButton1.setDuration(Duration.seconds(2));
        FadeButton1.setFromValue(0.0);
        FadeButton1.setToValue(1.0);

        FadeTransition FadeButton2 = new FadeTransition();
        FadeButton2.setNode(Button2);
        FadeButton2.setDuration(Duration.seconds(2));
        FadeButton2.setFromValue(0.0);
        FadeButton2.setToValue(1.0);

        FadeTransition FadeButton3 = new FadeTransition();
        FadeButton3.setNode(Button3);
        FadeButton3.setDuration(Duration.seconds(2));
        FadeButton3.setFromValue(0.0);
        FadeButton3.setToValue(1.0);

        FadeTransition FadeButton4 = new FadeTransition();
        FadeButton4.setNode(Button4);
        FadeButton4.setDuration(Duration.seconds(2));
        FadeButton4.setFromValue(0.0);
        FadeButton4.setToValue(1.0);

     /*   ParallelTransition parallelButton1 = new ParallelTransition(SwipeButton1,FadeButton1);
        ParallelTransition parallelButton2 = new ParallelTransition(SwipeButton2,FadeButton2);
        ParallelTransition parallelButton3 = new ParallelTransition(SwipeButton3,FadeButton3);
        ParallelTransition parallelButton4 = new ParallelTransition(SwipeButton4,FadeButton4);

        SequentialTransition SequentialButton = new SequentialTransition(parallelButton1,parallelButton2,parallelButton3,parallelButton4);
        SequentialButton.play();*/

        ParallelTransition parallelButton = new ParallelTransition(SwipeButton1,FadeButton1,SwipeButton2,FadeButton2,SwipeButton3,FadeButton3,SwipeButton4,FadeButton4);
        parallelButton.play();

        ParallelTransition parallelTransition = new ParallelTransition(SwipeGroup1, FadeGroup1,FadeGroup2,FadeGroup3,FadeGroup4,FadeGroup5,FadeGroup6,FadeGroup7);
        parallelTransition.play();
    }

    @FXML
    public void initialize() {
        Animasi();
    }
}
