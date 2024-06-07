package org.radianite.prg3javafxsistemmarketingperumahan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Helloo!");
        stage.setScene(scene);
        stage.show();
        System.out.println("Test");
    }

    public static void main(String[] args) {
        launch();
    }
}