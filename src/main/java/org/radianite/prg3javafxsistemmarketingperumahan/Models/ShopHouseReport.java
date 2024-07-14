package org.radianite.prg3javafxsistemmarketingperumahan.Models;

public class ShopHouseReport {
    private String invoice,tanggal,blok,penyewa,pembayaran,bank,total;

    public ShopHouseReport(String invoice, String tanggal, String blok, String penyewa, String pembayaran, String bank, String total) {
        this.invoice = invoice;
        this.tanggal = tanggal;
        this.blok = blok;
        this.penyewa = penyewa;
        this.pembayaran = pembayaran;
        this.bank = bank;
        this.total = total;
    }

    public String getPembayaran() {
        return pembayaran;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getBlok() {
        return blok;
    }

    public String getPenyewa() {
        return penyewa;
    }

    public String getBank() {
        return bank;
    }

    public String getTotal() {
        return total;
    }
}
