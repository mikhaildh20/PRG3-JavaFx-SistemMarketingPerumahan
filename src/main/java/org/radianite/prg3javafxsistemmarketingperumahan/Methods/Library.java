package org.radianite.prg3javafxsistemmarketingperumahan.Methods;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.Bank.updateBank;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Bank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Library {
    private String query,imported,base;
    public Library(){

    }

    public String generateID(String tableName,String formatID,String column){
        String query,imported,base;
        try{
            Database connect = new Database();
            connect.stat = connect.conn.createStatement();
            query = "SELECT TOP 1 "+column+" FROM "+tableName+" ORDER BY "+column+" DESC";
            connect.result = connect.stat.executeQuery(query);
            if (connect.result.next()){
                imported = connect.result.getString(column);
                connect.result.close();
                connect.stat.close();
                base="";
                for (int i=3;i<imported.length();i++){
                    base+=imported.charAt(i);
                }
                return String.format("%s%03d", formatID, Integer.parseInt(base)+1);
            }
            connect.stat.close();
            connect.result.close();
            return formatID+"001";
        }catch (SQLException ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return formatID+"001";
    }

    public void successBox(Button button, String message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage stage = (Stage) button.getScene().getWindow();
        alert.initOwner(stage);
        alert.showAndWait();
    }

    public void warningBox(Button button, String message){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage stage = (Stage) button.getScene().getWindow();
        alert.initOwner(stage);
        alert.showAndWait();
    }

    public void fillBox(Button button, String message){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage stage = (Stage) button.getScene().getWindow();
        alert.initOwner(stage);
        alert.showAndWait();
    }

    public void errorBox(Button button, String message){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage stage = (Stage) button.getScene().getWindow();
        alert.initOwner(stage);
        alert.showAndWait();
    }

    public void confirmBox(String sp,String id,Button button){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Record");
        alert.setContentText("Are you sure you want to delete this record?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Stage stage = (Stage) button.getScene().getWindow();
        alert.initOwner(stage);
        /*alert.showAndWait();*/

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                // User clicked Yes
                try{
                    Database connect = new Database();
                    String query = "EXEC "+sp+" ?";
                    connect.pstat = connect.conn.prepareStatement(query);
                    connect.pstat.setString(1,id);
                    connect.pstat.executeUpdate();
                    connect.pstat.close();
                }catch (SQLException ex){
                    System.out.println("Error: "+ex.getMessage());
                }
            }
        });
    }

    public void loadPage(ActionEvent event, String page){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/"+page+".fxml"));
        }catch (IOException ex){
            ex.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteData(String sp, String id) {
        confirmBox(sp, id, null);
    }

    public File imageChooser(Button button){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images Files","*.png","*.jpg","*.jpeg"));

        Stage stage = (Stage) button.getScene().getWindow();

        return fileChooser.showOpenDialog(stage);
    }

    public byte[] imageToByte(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    public Image convertToImage(byte[] imageData) {
        if (imageData != null) {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            return new Image(bis);
        } else {
            return null;
        }
    }

    public byte[] convertImageToBytes(Image image) throws IOException {
        PixelReader pixelReader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = pixelReader.getArgb(x, y);
                bufferedImage.setRGB(x, y, argb);
            }
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", bos);
        return bos.toByteArray();
    }

    public String convertDoubleString(Double value){
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.GERMAN);
        symbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        return formatter.format(value);
    }

    public Double convertStringDouble(String value){
        value = value.replace(".", "");
        return Double.parseDouble(value);
    }

    protected void handleLetterKey(KeyEvent event) {
        String character = event.getCharacter();
        if (!character.matches("[a-zA-Z ]") && !character.equals("\b")) {
            event.consume(); // Ignore this key event
        }
    }

    protected void handleNumberKey(KeyEvent event) {
        String character = event.getCharacter();

        // Allow only numeric input
        if (!character.matches("[0-9]")) {
            event.consume();
        }
    }

    protected void setPane(String fxml, Bank data, AnchorPane GroupMenu) {
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