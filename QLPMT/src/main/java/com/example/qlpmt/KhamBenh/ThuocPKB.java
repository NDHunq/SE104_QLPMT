package com.example.qlpmt.KhamBenh;

import javafx.collections.ObservableList;

public class ThuocPKB {
    private int stt;
    private String tenThuoc;
    private String donVi;
    private int soLuong;
    private String cachDung;

    public ThuocPKB(int stt, String tenThuoc, String donVi, int soLuong, String cachDung) {
        this.stt = stt;
        this.tenThuoc = tenThuoc;
        this.donVi = donVi;
        this.soLuong = soLuong;
        this.cachDung = cachDung;
    }

    public int getStt() {
        return stt;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public String getDonVi() {
        return donVi;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public String getCachDung() {
        return cachDung;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setCachDung(String cachDung) {
        this.cachDung = cachDung;
    }
}
