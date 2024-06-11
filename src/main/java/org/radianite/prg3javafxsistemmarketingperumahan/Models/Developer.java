package org.radianite.prg3javafxsistemmarketingperumahan.Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Developer {
    private final SimpleStringProperty idDeveloper;
    private final SimpleStringProperty namaDeveloper;
    private final SimpleIntegerProperty status;

    public Developer(String idDeveloper, String namaDeveloper, int status) {
        this.idDeveloper = new SimpleStringProperty(idDeveloper);
        this.namaDeveloper = new SimpleStringProperty(namaDeveloper);
        this.status = new SimpleIntegerProperty(status);
    }

    public String getIdDeveloper() {
        return idDeveloper.get();
    }

    public void setIdDeveloper(String idDeveloper) {
        this.idDeveloper.set(idDeveloper);
    }

    public String getNamaDeveloper() {
        return namaDeveloper.get();
    }

    public void setNamaDeveloper(String namaDeveloper) {
        this.namaDeveloper.set(namaDeveloper);
    }

    public int getStatus() {
        return status.get();
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public SimpleStringProperty idDeveloperProperty() {
        return idDeveloper;
    }

    public SimpleStringProperty namaDeveloperProperty() {
        return namaDeveloper;
    }

    public SimpleIntegerProperty statusProperty() {
        return status;
    }
}
