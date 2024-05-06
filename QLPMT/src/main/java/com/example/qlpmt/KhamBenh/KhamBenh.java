package com.example.qlpmt.KhamBenh;

public class KhamBenh {
    private String STT;
    private String HoTen;
    private String CCCD;
    private String GioiTinh;
    private String NamSinh;
    private String DiaChi;
    private String imgPkb;

    public KhamBenh(String STT, String hoTen, String CCCD, int gioiTinh, String namSinh, String diaChi, int imgPkb) {
        this.STT = STT;
        HoTen = hoTen;
        this.CCCD = CCCD;
        if(gioiTinh == 1){
            GioiTinh = "Nam";
        }else{
            GioiTinh = "Nữ";
        }
        NamSinh = namSinh;
        DiaChi = diaChi;
        if(imgPkb == 1){
            this.imgPkb = "Xem";
        }else{
            this.imgPkb = "Tạo";
        }
    }

    public String getImgPkb() {
        return imgPkb;
    }

    public void setImgPkb(String imgPkb) {
        this.imgPkb = imgPkb;
    }

    public String getSTT() {
        return STT;
    }

    public void setSTT(String STT) {
        this.STT = STT;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(String namSinh) {
        NamSinh = namSinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }
}
