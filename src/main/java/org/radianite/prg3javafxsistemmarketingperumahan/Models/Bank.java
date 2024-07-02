package org.radianite.prg3javafxsistemmarketingperumahan.Models;

public class Bank {
    private String id, name;
    private Integer bunga;
    private int status; // Added status attribute

    public Bank(String id, String name, Integer bunga) {
        this.id = id;
        this.name = name;
        this.bunga = bunga;
    }

    public Bank(String id, String name, Integer bunga, int status) { // Added new constructor
        this.id = id;
        this.name = name;
        this.bunga = bunga;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBunga() {
        return bunga;
    }

    public int getStatus() { // Added getter for status
        return status;
    }

    public void setStatus(int status) { // Added setter for status
        this.status = status;
    }
}
