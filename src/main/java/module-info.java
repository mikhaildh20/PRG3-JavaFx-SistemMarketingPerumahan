module org.radianite.prg3javafxsistemmarketingperumahan {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    exports org.radianite.prg3javafxsistemmarketingperumahan.Controller.TipeRumah;
    opens org.radianite.prg3javafxsistemmarketingperumahan.Controller.TipeRumah to javafx.fxml;
   exports org.radianite.prg3javafxsistemmarketingperumahan.Controller.User;
    opens org.radianite.prg3javafxsistemmarketingperumahan.Controller.User to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan.Controller.Kavling;
    opens org.radianite.prg3javafxsistemmarketingperumahan.Controller.Kavling to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan.Controller.Ruko;
    opens org.radianite.prg3javafxsistemmarketingperumahan.Controller.Ruko to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan.App;
    opens org.radianite.prg3javafxsistemmarketingperumahan.App to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan.Models;
    opens org.radianite.prg3javafxsistemmarketingperumahan.Models to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer;
    opens org.radianite.prg3javafxsistemmarketingperumahan.Controller.Developer to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah;
    opens org.radianite.prg3javafxsistemmarketingperumahan.Controller.Rumah to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan.Controller.Role;
    opens org.radianite.prg3javafxsistemmarketingperumahan.Controller.Role to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan.Controller.Perumahan;
    opens org.radianite.prg3javafxsistemmarketingperumahan.Controller.Perumahan to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan.App.Admin;
    opens org.radianite.prg3javafxsistemmarketingperumahan.App.Admin to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan.App.Agen;
    opens org.radianite.prg3javafxsistemmarketingperumahan.App.Agen to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan.App.Manager;
    opens org.radianite.prg3javafxsistemmarketingperumahan.App.Manager to javafx.fxml;
    exports org.radianite.prg3javafxsistemmarketingperumahan.Controller.TransaksiRuko;
    opens org.radianite.prg3javafxsistemmarketingperumahan.Controller.TransaksiRuko to javafx.fxml;

}