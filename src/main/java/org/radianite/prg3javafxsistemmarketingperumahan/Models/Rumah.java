package org.radianite.prg3javafxsistemmarketingperumahan.Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

import java.sql.Date;

public class Rumah {
    private final SimpleStringProperty idRumah;
    private final SimpleStringProperty idPerumahan;
    private final SimpleObjectProperty<ImageView> fotoRumah; // Ubah tipe menjadi ImageView
    private final SimpleStringProperty blok;
    private final SimpleIntegerProperty dayaListrik;
    private final SimpleStringProperty interior;
    private final SimpleIntegerProperty jmlKmrTdr;
    private final SimpleIntegerProperty jmlKmrMdn;
    private final SimpleStringProperty idTipe;
    private final SimpleStringProperty description;
    private final SimpleObjectProperty<Double> harga;
    private final SimpleObjectProperty<Date> thnBangun;
    private final SimpleIntegerProperty ketersediaan;
    private final SimpleIntegerProperty status;

    public Rumah(String idRumah, String idPerumahan, ImageView fotoRumah, String blok, int dayaListrik,
                 String interior, int jmlKmrTdr, int jmlKmrMdn, String idTipe, String description,
                  Double harga, Date thnBangun, int ketersediaan, int status) {
        this.idRumah = new SimpleStringProperty(idRumah);
        this.idPerumahan = new SimpleStringProperty(idPerumahan);
        this.fotoRumah = new SimpleObjectProperty<>(fotoRumah); // Ubah tipe menjadi ImageView
        this.blok = new SimpleStringProperty(blok);
        this.dayaListrik = new SimpleIntegerProperty(dayaListrik);
        this.interior = new SimpleStringProperty(interior);
        this.jmlKmrTdr = new SimpleIntegerProperty(jmlKmrTdr);
        this.jmlKmrMdn = new SimpleIntegerProperty(jmlKmrMdn);
        this.idTipe = new SimpleStringProperty(idTipe);
        this.description = new SimpleStringProperty(description);
        this.harga = new SimpleObjectProperty<>(harga);
        this.thnBangun = new SimpleObjectProperty<>(thnBangun);
        this.ketersediaan = new SimpleIntegerProperty(ketersediaan);
        this.status = new SimpleIntegerProperty(status);
    }

    public String getIdRumah() {
        return idRumah.get();
    }

    public void setIdRumah(String idRumah) {
        this.idRumah.set(idRumah);
    }

    public SimpleStringProperty idRumahProperty() {
        return idRumah;
    }

    public String getIdPerumahan() {
        return idPerumahan.get();
    }

    public void setIdPerumahan(String idPerumahan) {
        this.idPerumahan.set(idPerumahan);
    }

    public SimpleStringProperty idPerumahanProperty() {
        return idPerumahan;
    }

    public ImageView getFotoRumah() { // Ubah tipe data menjadi ImageView
        return fotoRumah.get();
    }

    public void setFotoRumah(ImageView fotoRumah) { // Ubah tipe data menjadi ImageView
        this.fotoRumah.set(fotoRumah);
    }

    public SimpleObjectProperty<ImageView> fotoRumahProperty() { // Ubah tipe data menjadi ImageView
        return fotoRumah;
    }

    public String getBlok() {
        return blok.get();
    }

    public void setBlok(String blok) {
        this.blok.set(blok);
    }

    public SimpleStringProperty blokProperty() {
        return blok;
    }

    public int getDayaListrik() {
        return dayaListrik.get();
    }

    public void setDayaListrik(int dayaListrik) {
        this.dayaListrik.set(dayaListrik);
    }

    public SimpleIntegerProperty dayaListrikProperty() {
        return dayaListrik;
    }

    public String getInterior() {
        return interior.get();
    }

    public void setInterior(String interior) {
        this.interior.set(interior);
    }

    public SimpleStringProperty interiorProperty() {
        return interior;
    }

    public int getJmlKmrTdr() {
        return jmlKmrTdr.get();
    }

    public void setJmlKmrTdr(int jmlKmrTdr) {
        this.jmlKmrTdr.set(jmlKmrTdr);
    }

    public SimpleIntegerProperty jmlKmrTdrProperty() {
        return jmlKmrTdr;
    }

    public int getJmlKmrMdn() {
        return jmlKmrMdn.get();
    }

    public void setJmlKmrMdn(int jmlKmrMdn) {
        this.jmlKmrMdn.set(jmlKmrMdn);
    }

    public SimpleIntegerProperty jmlKmrMdnProperty() {
        return jmlKmrMdn;
    }

    public String getIdTipe() {
        return idTipe.get();
    }

    public void setIdTipe(String idTipe) {
        this.idTipe.set(idTipe);
    }

    public SimpleStringProperty idTipeProperty() {
        return idTipe;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }


    public Double getHarga() {
        return harga.get();
    }

    public void setHarga(Double harga) {
        this.harga.set(harga);
    }

    public SimpleObjectProperty<Double> hargaProperty() {
        return harga;
    }

    public Date getThnBangun() {
        return thnBangun.get();
    }

    public void setThnBangun(Date thnBangun) {
        this.thnBangun.set(thnBangun);
    }

    public SimpleObjectProperty<Date> thnBangunProperty() {
        return thnBangun;
    }

    public int getKetersediaan() {
        return ketersediaan.get();
    }

    public void setKetersediaan(int ketersediaan) {
        this.ketersediaan.set(ketersediaan);
    }

    public SimpleIntegerProperty ketersediaanProperty() {
        return ketersediaan;
    }

    public int getStatus() {
        return status.get();
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public SimpleIntegerProperty statusProperty() {
        return status;
    }
}
