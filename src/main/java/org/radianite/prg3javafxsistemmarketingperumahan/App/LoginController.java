package org.radianite.prg3javafxsistemmarketingperumahan.App;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane GroupWelcome;
    @FXML
    private AnchorPane groupLoginFild;
    @FXML
    private Label labelLogin;
    @FXML
    private ImageView Image1;
    @FXML
    private ImageView Image2;
    @FXML
    private ImageView Image3;
    @FXML
    private ImageView Image4;
    @FXML
    private AnchorPane TextLogin;
    @FXML
    private Text imageLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set initial positions and opacities
        GroupWelcome.setTranslateX(-500);
        groupLoginFild.setTranslateY(-100);
        Image2.setOpacity(0.0);
        Image3.setOpacity(0.0);
        Image4.setOpacity(0.0);

        // Create TranslateTransition for GroupWelcome
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(GroupWelcome);
        translateTransition.setDuration(Duration.seconds(2));
        translateTransition.setToX(0);

        // Create FadeTransition for GroupWelcome
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(GroupWelcome);
        fadeTransition.setDuration(Duration.seconds(2));
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        // Combine TranslateTransition and FadeTransition using ParallelTransition
        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);

        // Create TranslateTransition for groupLoginFild
        TranslateTransition translateGroupLogin = new TranslateTransition();
        translateGroupLogin.setNode(groupLoginFild);
        translateGroupLogin.setDuration(Duration.seconds(2));
        translateGroupLogin.setToY(0);

        // Create FadeTransition for groupLoginFild
        FadeTransition fadeGroupLogin = new FadeTransition();
        fadeGroupLogin.setNode(groupLoginFild);
        fadeGroupLogin.setDuration(Duration.seconds(2));
        fadeGroupLogin.setFromValue(0.0);
        fadeGroupLogin.setToValue(1.0);

        // Create FadeTransition for labelLogin
        FadeTransition fadeLabelLogin = new FadeTransition();
        fadeLabelLogin.setNode(TextLogin);
        fadeLabelLogin.setDuration(Duration.seconds(2));
        fadeLabelLogin.setFromValue(0.0);
        fadeLabelLogin.setToValue(1.0);

        // Combine Translate and Fade transitions for GroupWelcome and groupLoginFild
        ParallelTransition parallelTransitionGroup = new ParallelTransition(
                parallelTransition, translateGroupLogin, fadeGroupLogin, fadeLabelLogin);
        // Create FadeTransitions for images
        FadeTransition fadeGambar1 = new FadeTransition();
        fadeGambar1.setNode(Image1);
        fadeGambar1.setDuration(Duration.seconds(5));
        fadeGambar1.setFromValue(0.0);
        fadeGambar1.setToValue(1.0);

        FadeTransition fadeGambar2 = new FadeTransition();
        fadeGambar2.setNode(Image2);
        fadeGambar2.setDuration(Duration.seconds(5));
        fadeGambar2.setFromValue(0.0);
        fadeGambar2.setToValue(1.0);

        FadeTransition fadeGambar3 = new FadeTransition();
        fadeGambar3.setNode(Image3);
        fadeGambar3.setDuration(Duration.seconds(5));
        fadeGambar3.setFromValue(0.0);
        fadeGambar3.setToValue(1.0);

        FadeTransition fadeGambar4 = new FadeTransition();
        fadeGambar4.setNode(Image4);
        fadeGambar4.setDuration(Duration.seconds(5));
        fadeGambar4.setFromValue(0.0);
        fadeGambar4.setToValue(1.0);

        // Create FadeTransitions for fade-out
        FadeTransition fadeOut1 = new FadeTransition();
        fadeOut1.setNode(Image1);
        fadeOut1.setDuration(Duration.seconds(3));
        fadeOut1.setFromValue(1.0);
        fadeOut1.setToValue(0.0);

        FadeTransition fadeOut2 = new FadeTransition();
        fadeOut2.setNode(Image2);
        fadeOut2.setDuration(Duration.seconds(5));
        fadeOut2.setFromValue(1.0);
        fadeOut2.setToValue(0.0);

        FadeTransition fadeOut3 = new FadeTransition();
        fadeOut3.setNode(Image3);
        fadeOut3.setDuration(Duration.seconds(5));
        fadeOut3.setFromValue(1.0);
        fadeOut3.setToValue(0.0);

        FadeTransition fadeOut4 = new FadeTransition();
        fadeOut4.setNode(Image4);
        fadeOut4.setDuration(Duration.seconds(5));
        fadeOut4.setFromValue(1.0);
        fadeOut4.setToValue(0.0);

        // Combine all image fade-in and fade-out transitions using SequentialTransition
        SequentialTransition sequentialTransition = new SequentialTransition(
                fadeGambar3, fadeGambar4, fadeOut4, fadeOut3
        );
        sequentialTransition.setCycleCount(SequentialTransition.INDEFINITE);

        // Create PauseTransition for delay
        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        // Set event handler to start sequentialTransition and other events after pause
        parallelTransitionGroup.setOnFinished(event -> pause.play());

        pause.setOnFinished(event -> {
            fadeGambar2.play();
            sequentialTransition.play();
            fadeOut1.play();
            // Tambahkan event kedua di sini
        });

        // Start transitions
        parallelTransitionGroup.play();
    }
}
