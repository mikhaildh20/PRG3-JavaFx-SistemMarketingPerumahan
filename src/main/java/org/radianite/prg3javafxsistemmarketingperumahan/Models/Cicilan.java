package org.radianite.prg3javafxsistemmarketingperumahan.Models;

public class Cicilan {
   private String id,blok,nama;
   private Integer telat;
   private Double denda,tunggakan;

    public Cicilan(String id, String blok, String nama, Integer telat, Double denda, Double tunggakan) {
        this.id = id;
        this.blok = blok;
        this.nama = nama;
        this.telat = telat;
        this.denda = denda;
        this.tunggakan = tunggakan;
    }

    public String getId() {
        return id;
    }

    public String getBlok() {
        return blok;
    }

    public String getNama() {
        return nama;
    }

    public Integer getTelat() {
        return telat;
    }

    public Double getDenda() {
        return denda;
    }

    public Double getTunggakan() {
        return tunggakan;
    }
}
