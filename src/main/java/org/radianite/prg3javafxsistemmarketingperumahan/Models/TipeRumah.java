package org.radianite.prg3javafxsistemmarketingperumahan.Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TipeRumah {
    private final SimpleStringProperty idTipe;
    private final SimpleStringProperty nama;
    private final SimpleIntegerProperty status;

    public TipeRumah(String idTipe, String nama, int status) {
        this.idTipe = new SimpleStringProperty(idTipe);
        this.nama = new SimpleStringProperty(nama);
        this.status = new SimpleIntegerProperty(status);
    }

    public String getIdTipe() {
        return idTipe.get();
    }

    public void setIdTipe(String idTipe) {
        this.idTipe.set(idTipe);
    }

    public String getNama() {
        return nama.get();
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    public int getStatus() {
        return status.get();
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public SimpleStringProperty idTipeProperty() {
        return idTipe;
    }

    public SimpleStringProperty namaProperty() {
        return nama;
    }

    public SimpleIntegerProperty statusProperty() {
        return status;
    }
}