package org.radianite.prg3javafxsistemmarketingperumahan.Models;

public class Perumahan {
    private String id, idv, name, namadev;
    private int status; // Added attribute status

    public Perumahan(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Perumahan(String id, String idv, String name, String namadev) {
        this.id = id;
        this.idv = idv;
        this.name = name;
        this.namadev = namadev;
    }

    public Perumahan(String id, String idv, String name, String namadev, int status) { // Added new constructor
        this.id = id;
        this.idv = idv;
        this.name = name;
        this.namadev = namadev;
        this.status = status;
    }

    public String getIdv() {
        return idv;
    }

    public String getNamadev() {
        return namadev;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStatus() { // Added getter for status
        return status;
    }

    public void setStatus(int status) { // Added setter for status
        this.status = status;
    }
}
