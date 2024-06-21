package org.radianite.prg3javafxsistemmarketingperumahan.Methods;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;

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

    public void successBox(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText(null);
        alert.setContentText("Operation has been done successfully.");
        alert.showAndWait();
    }

    public void warningBox(){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(null);
        alert.setContentText("You don't have permission!");
        alert.showAndWait();
    }

    public void fillBox(){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(null);
        alert.setContentText("Please fill all the data!");
        alert.showAndWait();
    }

    public void errorBox(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Unexpected Error.");
        alert.showAndWait();
    }

    public void confirmBox(String sp,String id){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Record");
        alert.setContentText("Are you sure you want to delete this record?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

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
        confirmBox(sp, id);
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

    public int cutPeriode(String value)
    {
        int val=0;
        try{
            if (value.length() == 6)
            {
                return Integer.parseInt(value.substring(0,0));
            }
        }catch (Exception ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
        return Integer.parseInt(value.substring(0,1));
    }

    public Double setPersentase(Integer bunga){
        if (bunga < 10)
        {
            String persentase = "0.0"+bunga;
            return Double.parseDouble(persentase);
        }
        String persentase = "0."+bunga;
        return Double.parseDouble(persentase);
    }
}