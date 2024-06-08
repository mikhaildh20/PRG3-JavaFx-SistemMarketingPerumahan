module org.radianite.prg3javafxsistemmarketingperumahan {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens org.radianite.prg3javafxsistemmarketingperumahan.Controller.TipeRumah to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan;
    exports org.radianite.prg3javafxsistemmarketingperumahan.App;
    opens org.radianite.prg3javafxsistemmarketingperumahan.App to javafx.fxml;
}