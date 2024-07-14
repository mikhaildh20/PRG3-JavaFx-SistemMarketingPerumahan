package org.radianite.prg3javafxsistemmarketingperumahan.Controller.Laporan;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.radianite.prg3javafxsistemmarketingperumahan.Connection.Database;
import org.radianite.prg3javafxsistemmarketingperumahan.Methods.Library;
import org.radianite.prg3javafxsistemmarketingperumahan.Models.ShopHouseReport;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.UUID;

public class LaporanRumah extends Library implements Initializable {
    @FXML
    private ComboBox<String> cbPayment;

    @FXML
    private TableColumn<ShopHouseReport, String> colAddress;

    @FXML
    private TableColumn<ShopHouseReport, String> colBank;

    @FXML
    private TableColumn<ShopHouseReport, String> colDate;

    @FXML
    private TableColumn<ShopHouseReport, Void> colDocument;

    @FXML
    private TableColumn<ShopHouseReport, String> colInvoice;

    @FXML
    private TableColumn<ShopHouseReport, String> colPayment;

    @FXML
    private TableColumn<ShopHouseReport, String> colRenter;

    @FXML
    private TableColumn<ShopHouseReport, String> colTotal;

    @FXML
    private DatePicker endDate;

    @FXML
    private DatePicker startDate;

    @FXML
    private TableView<ShopHouseReport> tableView;

    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnExcel;
    private ObservableList<ShopHouseReport> listLaporann = FXCollections.observableArrayList();
    private ObservableList<ShopHouseReport> listFilter = FXCollections.observableArrayList();
    private ObservableList<String> listPayment = FXCollections.observableArrayList("CASH","CREDIT");
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbPayment.setItems(listPayment);
        loadLaporan();
        colInvoice.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("blok"));
        colRenter.setCellValueFactory(new PropertyValueFactory<>("penyewa"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("pembayaran"));
        colBank.setCellValueFactory(new PropertyValueFactory<>("bank"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDocument.setCellFactory(param->new TableCell<>(){
            private final Button btnExport = new Button("Export");
            {
                btnExport.setOnAction(event -> {
                    downloadPDF(listFilter.get(getIndex()).getInvoice(),
                            "C:/Users/Herdiansah/Mikhail/Laporan/HouseBuyer_"+ listFilter.get(getIndex()).getPenyewa()+ UUID.randomUUID()+".pdf",
                            "tr_rumah",
                            "dokumen_pembelian",
                            "id_trRumah");
                    successBox(btnExport,"PDF has been exported successfully.");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(btnExport);
                    setGraphic(buttons);
                }
            }
        });
    }

    public void loadLaporan()
    {
        try{
            Database connect = new Database();
            String query = "EXEC LaporanRumah";
            connect.stat = connect.conn.createStatement();
            connect.result = connect.stat.executeQuery(query);

            while (connect.result.next())
            {
                listLaporann.add(new ShopHouseReport(
                        connect.result.getString("INVOICE"),
                        connect.result.getString("TANGGAL"),
                        connect.result.getString("BLOK"),
                        connect.result.getString("PEMBELI"),
                        connect.result.getString("PEMBAYARAN"),
                        connect.result.getString("BANK") == null ? "Not Applied" :  connect.result.getString("BANK"),
                        convertDoubleString(connect.result.getDouble("TOTAL"))
                ));
            }

            connect.stat.close();
            connect.result.close();
        }catch (SQLException ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
        listFilter = listLaporann;
        tableView.setItems(listFilter);
    }

    @FXML
    void excelButton(ActionEvent event) {
        try(Workbook workbook = new XSSFWorkbook()){
            Sheet sheet = workbook.createSheet("HouseReport"+LocalDate.now());

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Invoice");
            headerRow.createCell(1).setCellValue("Transaction Date");
            headerRow.createCell(2).setCellValue("Address");
            headerRow.createCell(3).setCellValue("Renter");
            headerRow.createCell(4).setCellValue("Payment");
            headerRow.createCell(5).setCellValue("Bank");
            headerRow.createCell(6).setCellValue("Total");

            int rowIdx = 1;
            for (ShopHouseReport data : listFilter)
            {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(data.getInvoice());
                row.createCell(1).setCellValue(data.getTanggal());
                row.createCell(2).setCellValue(data.getBlok());
                row.createCell(3).setCellValue(data.getPenyewa());
                row.createCell(4).setCellValue(data.getPembayaran());
                row.createCell(5).setCellValue(data.getBank());
                row.createCell(6).setCellValue(data.getTotal());
            }

            try(FileOutputStream fileOut = new FileOutputStream("C:/Users/Herdiansah/Mikhail/Laporan/HouseReport"+LocalDate.now()+UUID.randomUUID()+".xlsx")){
                workbook.write(fileOut);
            }
            successBox(btnExcel,"Data has been exported successfully.");
        }catch (IOException ex)
        {
            System.out.println("Error: "+ex.getMessage());
        }
    }

    @FXML
    void filterButton(ActionEvent event) {
        ObservableList<ShopHouseReport> filtering = FXCollections.observableArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (ShopHouseReport data : listLaporann) {
            try {
                Date fromDate = sdf.parse(startDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                Date toDate = sdf.parse(endDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                Date reportDate = sdf.parse(data.getTanggal());
                if (reportDate.after(fromDate) && reportDate.before(toDate)) {
                    filtering.add(data);
                }
            } catch (ParseException ex) {
                System.out.println("Error: "+ex.getMessage());
            }
        }
        tableView.setItems(filtering);
        tableView.refresh();
    }

    @FXML
    void printButton(ActionEvent event) {
        Database connect = new Database();
        try {
            URL url = getClass().getResource("/org/radianite/prg3javafxsistemmarketingperumahan/Report/LaporanRumah.jrxml");
            String jrxmlPath = URLDecoder.decode(url.getFile(), "UTF-8");
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), connect.conn);

            // Create a JasperViewer
            final JasperViewer viewer = new JasperViewer(jasperPrint);

            // Get the component that displays the report
            final JComponent reportComponent = (JComponent) viewer.getComponent(0);

            // Create a JavaFX window to display the report
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            SwingNode swingNode = new SwingNode();
            swingNode.setContent(reportComponent); // Add the report component to the SwingNode
            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(swingNode); // Add the SwingNode to the BorderPane
            Scene scene = new Scene(borderPane);
            stage.setScene(scene);
            stage.show();
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void refreshButton(ActionEvent event) {
        listFilter = listLaporann;
        tableView.setItems(listFilter);
        tableView.refresh();
    }

    @FXML
    void filterPayment(ActionEvent event) {
        ObservableList<ShopHouseReport> filtering = FXCollections.observableArrayList();
        String selectedPayment = cbPayment.getSelectionModel().getSelectedItem();
        for (ShopHouseReport data : listLaporann) {
            if (data.getPembayaran().equals(selectedPayment)) {
                filtering.add(new ShopHouseReport(data.getInvoice(),
                        data.getTanggal(),
                        data.getBlok(),
                        data.getPenyewa(),
                        data.getPembayaran(),
                        data.getBank(),
                        data.getTotal()));
            }
        }

        listFilter = filtering;
        tableView.setItems(listFilter);
        tableView.refresh();
    }

    @FXML
    void keySearch(KeyEvent event) {
        String searchText = txtSearch.getText().toLowerCase();
        ObservableList<ShopHouseReport> filtering = FXCollections.observableArrayList();
        filtering.setAll(listLaporann);

        filtering.clear();
        for (ShopHouseReport shopHouseReport : listLaporann) {
            if (shopHouseReport.getInvoice().toLowerCase().contains(searchText)
                    || shopHouseReport.getBlok().toLowerCase().contains(searchText)
                    || shopHouseReport.getPenyewa().toLowerCase().contains(searchText)
                    || shopHouseReport.getBank().toLowerCase().contains(searchText)) {
                filtering.add(shopHouseReport);
            }
        }
        listFilter = filtering;
        tableView.setItems(listFilter);
        tableView.refresh();
    }
}
