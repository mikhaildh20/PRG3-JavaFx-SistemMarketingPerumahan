package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah.DetailRumah;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah.updateRumah;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Developer;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;

import java.sql.SQLException;

public class Update extends Library {

    @FXML
    private TextField txtIdDeveloper; // Ganti dari idRoleField menjadi idDeveloperField
    @FXML
    private TextField txtNamaDeveloper; // Ganti dari namaField menjadi namaDeveloperField
    @FXML private Button btnSave;
    @FXML
    private AnchorPane GroupMenu;

    private Developer developer; // Ganti dari Role menjadi Developer
    private Database connection = new Database();

    private ObservableList<Developer> developerList = FXCollections.observableArrayList();


    public void loadData()
    {
        try {
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM ms_developer"; // Ganti dari ms_role menjadi ms_developer
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                developerList.add(new Developer(connection.result.getString("id_developer"), // Ganti dari id_role menjadi id_developer
                        connection.result.getString("nama_developer"), // Ganti dari nama_role menjadi nama_developer
                        connection.result.getInt("status")));
            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception ex) {
            System.out.println("Terjadi error saat load data: " + ex);
        }
    }

    public void setDeveloper(Developer developer) { // Ganti dari setRole menjadi setDeveloper
        this.developer = developer;
        txtIdDeveloper.setText(developer.getIdDeveloper()); // Ganti dari getIdRole menjadi getIdDeveloper
        txtNamaDeveloper.setText(developer.getNamaDeveloper()); // Ganti dari getNamaRole menjadi getNamaDeveloper
        txtNamaDeveloper.addEventFilter(KeyEvent.KEY_TYPED, super::handleLetterKey);

        loadData();
    }

    @FXML
    private void handleUpdateAction() {

        if (txtNamaDeveloper.getText().isEmpty())
        {
            fillBox(btnSave,"Please fill in the field");
            return;
        }
        for (int i=0;i<developerList.size();i++)
        {
            if (txtNamaDeveloper.getText().equals(developerList.get(i).getNamaDeveloper()) && !txtIdDeveloper.getText().equals(developerList.get(i).getIdDeveloper())){
                errorBox(btnSave,"Name already exist");
                return;
            }
        }
        successBox(btnSave,"Update Success");
        developer.setNamaDeveloper(txtNamaDeveloper.getText());
        txtIdDeveloper.setText("");
        txtNamaDeveloper.setText("");

        try {
            updateDataInDatabase(developer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void back(){
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/Master/Developer/View.fxml");
    }

    private void updateDataInDatabase(Developer developer) throws SQLException {
        try {
            String query = "{call sp_updateDeveloper(?, ?)}"; // Ganti dari sp_updateRole menjadi sp_updateDeveloper
            connection.pstat = connection.conn.prepareCall(query);
            connection.pstat.setString(1, developer.getIdDeveloper()); // Ganti dari getIdRole menjadi getIdDeveloper
            connection.pstat.setString(2, developer.getNamaDeveloper()); // Ganti dari getNamaRole menjadi getNamaDeveloper

            connection.pstat.executeUpdate();
            connection.pstat.close();

        } catch (SQLException ex) {
            System.out.println("Terjadi error saat Update data developer: " + ex);
        }
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
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), GroupMenu);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                ParallelTransition parallelTransition = new ParallelTransition(fadeIn);
                parallelTransition.play();
            });
            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
