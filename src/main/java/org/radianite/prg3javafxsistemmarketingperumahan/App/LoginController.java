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
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private Database connection = new Database();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        animasiLogin();
    }

    private void animasiLogin() {
        // Inisialisasi posisi dan opasitas awal dari elemen UI
        GroupWelcome.setTranslateX(-500);
        groupLoginFild.setTranslateY(-100);
        Image2.setOpacity(0.0);
        Image3.setOpacity(0.0);
        Image4.setOpacity(0.0);

        // Animasi translasi dan fade untuk GroupWelcome
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), GroupWelcome);
        translateTransition.setToX(0);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), GroupWelcome);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);

        // Animasi translasi dan fade untuk groupLoginFild
        TranslateTransition translateGroupLogin = new TranslateTransition(Duration.seconds(2), groupLoginFild);
        translateGroupLogin.setToY(0);

        FadeTransition fadeGroupLogin = new FadeTransition(Duration.seconds(2), groupLoginFild);
        fadeGroupLogin.setFromValue(0.0);
        fadeGroupLogin.setToValue(1.0);

        FadeTransition fadeLabelLogin = new FadeTransition(Duration.seconds(2), TextLogin);
        fadeLabelLogin.setFromValue(0.0);
        fadeLabelLogin.setToValue(1.0);

        // Kombinasi animasi untuk GroupWelcome dan groupLoginFild
        ParallelTransition parallelTransitionGroup = new ParallelTransition(parallelTransition, translateGroupLogin, fadeGroupLogin, fadeLabelLogin);

        // Animasi fade-in untuk images
        FadeTransition fadeGambar1 = new FadeTransition(Duration.seconds(5), Image1);
        fadeGambar1.setFromValue(0.0);
        fadeGambar1.setToValue(1.0);

        FadeTransition fadeGambar2 = new FadeTransition(Duration.seconds(5), Image2);
        fadeGambar2.setFromValue(0.0);
        fadeGambar2.setToValue(1.0);

        FadeTransition fadeGambar3 = new FadeTransition(Duration.seconds(5), Image3);
        fadeGambar3.setFromValue(0.0);
        fadeGambar3.setToValue(1.0);

        FadeTransition fadeGambar4 = new FadeTransition(Duration.seconds(5), Image4);
        fadeGambar4.setFromValue(0.0);
        fadeGambar4.setToValue(1.0);

        // Animasi fade-out untuk images
        FadeTransition fadeOut1 = new FadeTransition(Duration.seconds(3), Image1);
        fadeOut1.setFromValue(1.0);
        fadeOut1.setToValue(0.0);

        FadeTransition fadeOut2 = new FadeTransition(Duration.seconds(5), Image2);
        fadeOut2.setFromValue(1.0);
        fadeOut2.setToValue(0.0);

        FadeTransition fadeOut3 = new FadeTransition(Duration.seconds(5), Image3);
        fadeOut3.setFromValue(1.0);
        fadeOut3.setToValue(0.0);

        FadeTransition fadeOut4 = new FadeTransition(Duration.seconds(5), Image4);
        fadeOut4.setFromValue(1.0);
        fadeOut4.setToValue(0.0);

        // Kombinasi semua animasi fade-in dan fade-out menggunakan SequentialTransition
        SequentialTransition sequentialTransition = new SequentialTransition(fadeGambar3, fadeGambar4, fadeOut4, fadeOut3);
        sequentialTransition.setCycleCount(SequentialTransition.INDEFINITE);

        // Transisi untuk delay
        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        // Event handler untuk memulai sequentialTransition dan event lainnya setelah delay
        parallelTransitionGroup.setOnFinished(event -> pause.play());

        pause.setOnFinished(event -> {
            fadeGambar2.play();
            sequentialTransition.play();
            fadeOut1.play();
        });

        // Memulai animasi
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
        String query = "SELECT * FROM ms_user WHERE username = ?  AND password = ?";

        ArrayList<User> userList = new ArrayList<>();

        try {
            // Menggunakan PreparedStatement untuk mencegah SQL injection
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setString(1, username);
            connection.pstat.setString(2, password);
            connection.result = connection.pstat.executeQuery();

            // Memeriksa apakah result set kosong
            if (!connection.result.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Login Failed");
                alert.setContentText("Username or Password is incorrect");
                alert.showAndWait();
                return;
            } else {
                String idr = "";
                // Iterasi melalui result set dan menambahkan data ke ArrayList
                while (connection.result.next()) {
                    String usn = connection.result.getString("username");
                    String pass = connection.result.getString("password");
                    String idp = connection.result.getString("id_perumahan");
                    idr = connection.result.getString("id_role");
                    String name = connection.result.getString("nama_lengkap");
                    String email = connection.result.getString("email");
                    String address = connection.result.getString("alamat");
                    String gender = connection.result.getString("jenis_kelamin");
                    int age = connection.result.getInt("umur");

                    userList.add(new User(usn, pass, idp, idr, name, email, address, gender, age));
                }

                String queryRole = "SELECT nama_role FROM ms_role WHERE id_role = ?";
                connection.pstat = connection.conn.prepareStatement(queryRole);
                connection.pstat.setString(1, idr);
                connection.result = connection.pstat.executeQuery();
                connection.result.next();
                String roleName = connection.result.getString("nama_role");

                // Mengatur tahap berikutnya setelah menyimpan data di ArrayList
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/" + roleName + "/Dasbord.fxml"));
                    Parent root = fxmlLoader.load();

                    // Mengirim data user ke controller berdasarkan role
                    if (roleName.equals("Admin")) {
                        DashbordAdminController controller = fxmlLoader.getController();
                        controller.setUsersAdmin(userList);
                    } else if (roleName.equals("Manager")) {
                        DashbordManagerController controller = fxmlLoader.getController();
                        controller.setUsersManager(userList);
                    } else if (roleName.equals("Agen")) {
                        DashbordAgenController controller = fxmlLoader.getController();
                        controller.setUsersAgen(userList);
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
            if (connection.result != null) {
                try {
                    connection.result.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (connection.pstat != null) {
                try {
                    connection.pstat.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
