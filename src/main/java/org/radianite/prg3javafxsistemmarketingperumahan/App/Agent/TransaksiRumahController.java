package org.radianite.prg3javafxsistemmarketingperumahan.App.Agent;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Admin.DashboardManageDeveloperController;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Admin.DashboardManageHouseTypeController;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Admin.DashboardManageHousingAreaController;
import org.radianite.prg3javafxsistemmarketingperumahan.App.Admin.MainDashboardController;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Controller.TransaksiRumah.Pembelian;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.Rumah;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.User;

import java.io.ByteArrayInputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TransaksiRumahController {

    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane mainpane;

    @FXML private Text txtBlok;
    @FXML private Text txtSince;
    @FXML private Text txtInterior;
    @FXML private Text txtBedroom;
    @FXML private Text txtBathroom;
    @FXML private Text txtType;
    @FXML private Text txtArea;
    @FXML private Text txtPrice;
    @FXML private TextArea txtDesc;
    @FXML private ImageView imageHouse;
    @FXML private AnchorPane ancorePenutup;
    @FXML private Button btnBuy;
    private String idr;

    ArrayList<Rumah> rumahlist = new ArrayList<>();

    Database db = new Database();

    @FXML
    public void initialize() {
        setCard();
    }

    @FXML
    private void btnBuyClixk() {
        setPane("/org/radianite/prg3javafxsistemmarketingperumahan/inputTrRumah.fxml");
    }

    private void setPane(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent pane = loader.load();
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), mainpane);
            mainpane.setOpacity(0.0);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(eventFadeOut -> {
                mainpane.getChildren().setAll(pane);
                Pembelian pembelian = loader.getController();
                pembelian.setDataList(idr);
                System.out.println(idr);
            /*    if (fxml.equals("/org/radianite/prg3javafxsistemmarketingperumahan/App/Dashboard/Admin/MainDashboard.fxml")) {
                    RumahController controller = loader.getController();
                    controller.setDataList(rumahlist.get(0));
                }*/
                mainpane.setTranslateX(-50);
                TranslateTransition translate = new TranslateTransition(Duration.seconds(0.5), mainpane);
                translate.setToX(0);
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), mainpane);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                ParallelTransition parallelTransition = new ParallelTransition(translate, fadeIn);
                parallelTransition.play();
            });
            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCard() {
        // Inisialisasi mainPane dan set konten mainScrollPane
        mainPane = new AnchorPane();
        mainScrollPane.setContent(mainPane);

        // Dimensi Dimensi dan jarak untuk grid
        double paneWidth = 300;
        double paneHeight = 200;
        double spacing = 10;
        int cols = 3; // Jumlah kolom

        // Ambil data dari database
        ResultSet rs = db.getDataRumah();

        int row = 0;
        int col = 0;

        try {
            while (rs.next()) {
                // Buat child AnchorPane
                AnchorPane childPane = new AnchorPane();
                childPane.setStyle("-fx-background-color: linear-gradient(to bottom, #3573EF, #3ABCF9); -fx-background-radius: 5;"); // Background linier dengan radius 5
                childPane.setPrefSize(paneWidth, paneHeight); // Set ukuran preferensi child pane

                // Ambil data dari ResultSet
                String rumahId = rs.getString("id_rumah");
                String blok = rs.getString("blok");
                String jmlKmrTdr = rs.getString("jml_kmr_tdr");
                String jmlKmrMdn = rs.getString("jml_kmr_mdn");
                String since = rs.getString("thn_bangun");
                String interior = rs.getString("interior");
                String type = rs.getString("id_tipe");
                String area = rs.getString("id_perumahan");
                String price = rs.getString("harga");
                String desc = rs.getString("descrption");
                byte[] fotoRumahBytes = rs.getBytes("foto_rumah"); // Ambil foto dari database

                // Tambahkan event listener untuk klik mouse pada child pane
                childPane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                    ancorePenutup.setOpacity(0.0);
                    ancorePenutup.setDisable(true);
                    System.out.println("Clicked on rumah with ID: " + rumahId);
                    idr = rumahId;

                    txtBlok.setText(": " + blok);
                    txtSince.setText(": " + since);
                    txtInterior.setText(": " + interior);
                    txtBedroom.setText(": " + jmlKmrTdr);
                    txtBathroom.setText(": " + jmlKmrMdn);
                    txtType.setText(": " + type);
                    txtArea.setText(": " + area);
                    // Menggunakan Double.parseDouble untuk menangani angka desimal
                    txtPrice.setText(": Rp." + String.format("%,.0f", Double.parseDouble(price)).replace(',', '.'));
                    txtDesc.setText(desc);
                    imageHouse.setImage(new Image(new ByteArrayInputStream(fotoRumahBytes)));
                });

                // Tambahkan efek hover pada child pane
                childPane.setOnMouseEntered(event -> childPane.setStyle("-fx-background-color: #3ABCF9 ; -fx-background-radius: 5;"));
                childPane.setOnMouseExited(event -> childPane.setStyle("-fx-background-color: linear-gradient(to bottom,  #3573EF, #3ABCF9); -fx-background-radius: 5;"));

                // Tambahkan teks dari database ke child pane
                Text textBlok = new Text(blok);
                textBlok.setStyle("-fx-font-size: 12px; -fx-font-family: 'Montserrat'; -fx-font-weight: bold; -fx-fill: white;");
                Text textKmrTdr = new Text("Kamar Tidur: " + jmlKmrTdr);
                textKmrTdr.setStyle("-fx-font-size: 12px; -fx-font-family: 'Montserrat'; -fx-fill: white;");
                Text textKmrMdn = new Text("Kamar Mandi: " + jmlKmrMdn);
                textKmrMdn.setStyle("-fx-font-size: 12px; -fx-font-family: 'Montserrat'; -fx-fill: white;");
                ImageView houseImageView = new ImageView(new Image(new ByteArrayInputStream(fotoRumahBytes)));

                // Set posisi elemen dalam child pane
                AnchorPane.setTopAnchor(textBlok, 15.0);
                AnchorPane.setLeftAnchor(textBlok, 200.0);
                AnchorPane.setTopAnchor(textKmrTdr, 150.0);
                AnchorPane.setLeftAnchor(textKmrTdr, 200.0);
                AnchorPane.setTopAnchor(textKmrMdn, 170.0);
                AnchorPane.setLeftAnchor(textKmrMdn, 200.0);
                houseImageView.setFitWidth(180);
                houseImageView.setFitHeight(180);
                AnchorPane.setTopAnchor(houseImageView, 10.0);
                AnchorPane.setLeftAnchor(houseImageView, 10.0);
                childPane.getChildren().addAll(houseImageView, textBlok, textKmrTdr, textKmrMdn);

                // Hitung posisi untuk child pane
                double topAnchor = row * (paneHeight + spacing);
                double leftAnchor = col * (paneWidth + spacing);

                // Tambahkan child AnchorPane ke main AnchorPane
                mainPane.getChildren().add(childPane);
                AnchorPane.setTopAnchor(childPane, topAnchor);
                AnchorPane.setLeftAnchor(childPane, leftAnchor);

                // Update kolom dan baris
                col++;
                if (col >= cols) {
                    col = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
