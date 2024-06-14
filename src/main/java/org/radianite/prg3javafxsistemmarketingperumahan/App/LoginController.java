package org.radianite.prg3javafxsistemmarketingperumahan.App;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Admin.DashbordAdminController;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Agen.DashbordAgenController;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Manager.DashbordManagerController;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;

    Database conection = new Database();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnimasiLogin();
    }

    private void AnimasiLogin() {
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

    @FXML
    public void labelloginclick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/radianite/prg3javafxsistemmarketingperumahan/viewRuko.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("App");
            stage.setScene(scene);
            stage.show();
            imageLogin.getScene().getWindow().hide();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void tombolLoginClick() throws SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String query = "SELECT * FROM ms_user WHERE username = ? AND password = ?";

        // ArrayList untuk menyimpan data pengguna
        ArrayList<User> userList = new ArrayList<>();

        try {
            // Menggunakan PreparedStatement untuk mencegah SQL injection
            conection.pstat = conection.conn.prepareStatement(query);
            conection.pstat.setString(1, username);
            conection.pstat.setString(2, password);
            conection.result = conection.pstat.executeQuery();

            // Memeriksa apakah result set kosong
            if (!conection.result.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Login Failed");
                alert.setContentText("Username or Password is incorrect");
                alert.showAndWait();
                return;
            } else {
                String idr = "";
                // Iterasi melalui result set dan menambahkan data ke ArrayList
                while (conection.result.next()) {
                    String usn = conection.result.getString("username");
                    String pass = conection.result.getString("password");
                    String idp = conection.result.getString("id_perumahan");
                    idr = conection.result.getString("id_role");
                    String name = conection.result.getString("nama_lengkap");
                    String email = conection.result.getString("email");
                    String address = conection.result.getString("alamat");
                    String gender = conection.result.getString("jenis_kelamin");
                    int age = conection.result.getInt("umur");

                    userList.add(new User(usn, pass, idp, idr, name, email, address, gender, age));
                }

                String queryRole = "SELECT nama_role FROM ms_role WHERE id_role = ?";
                conection.pstat = conection.conn.prepareStatement(queryRole);
                conection.pstat.setString(1, idr);
                conection.result = conection.pstat.executeQuery();
                conection.result.next();
                String roleName = conection.result.getString("nama_role");

                // Mengatur tahap berikutnya setelah menyimpan data di ArrayList
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/" + roleName + "/Dasbord.fxml"));
                    Parent root = fxmlLoader.load();

                    if (roleName.equals("Admin")) {
                        DashbordAdminController controller = fxmlLoader.getController();
                        controller.setUsersAdmin(userList); // Mengirim data user ke controller
                    } else if (roleName.equals("Manager")) {
                        DashbordManagerController controller = fxmlLoader.getController();
                        controller.setUsersManager(userList); // Mengirim data user ke controller
                    } else if (roleName.equals("Agen")) {
                        DashbordAgenController controller = fxmlLoader.getController();
                        controller.setUsersAgen(userList); // Mengirim data user ke controller
                    }


                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("App");
                    stage.setScene(scene);
                    stage.show();

                    usernameField.getScene().getWindow().hide();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Menutup resources
            if (conection.result != null) {
                try {
                    conection.result.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (conection.pstat != null) {
                try {
                    conection.pstat.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}


