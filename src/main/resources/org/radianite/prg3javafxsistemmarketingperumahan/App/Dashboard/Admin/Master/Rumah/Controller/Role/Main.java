package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Role;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/Master/Role/View.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Input Obat");
        stage.setScene(scene);

        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }


}