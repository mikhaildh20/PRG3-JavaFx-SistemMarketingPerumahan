module org.radianite.prg3javafxsistemmarketingperumahan {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.radianite.prg3javafxsistemmarketingperumahan to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan;
}