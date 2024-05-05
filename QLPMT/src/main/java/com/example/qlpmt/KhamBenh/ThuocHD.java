package com.example.qlpmt.KhamBenh;

public class ThuocHD {
    private int stt;
    private String tenThuoc;
    private String donVi;
    private int soLuong;
    private String DonGia;
    private String ThanhTien;

    public ThuocHD(int stt, String tenThuoc, String donVi, int soLuong, String donGia, String thanhTien) {
        this.stt = stt;
        this.tenThuoc = tenThuoc;
        this.donVi = donVi;
        this.soLuong = soLuong;
        DonGia = donGia;
        ThanhTien = thanhTien;
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

    public String getDonGia() {
        return DonGia;
    }

    public String getThanhTien() {
        return ThanhTien;
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

    public void setDonGia(String donGia) {
        DonGia = donGia;
    }

    public void setThanhTien(String thanhTien) {
        ThanhTien = thanhTien;
    }
}
