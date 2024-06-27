package org.radianite.prg3javafxsistemmarketingperumahan.Models;

public class Cicilan {
    private String id,blok,tgl;
    private Double tunggakan;

    public Cicilan(String id, String blok, String tgl, Double tunggakan) {
        this.id = id;
        this.blok = blok;
        this.tgl = tgl;
        this.tunggakan = tunggakan;
    }

    public String getId() {
        return id;
    }

    public String getBlok() {
        return blok;
    }

    public String getTgl() {
        return tgl;
    }

    public Double getTunggakan() {
        return tunggakan;
    }
}
