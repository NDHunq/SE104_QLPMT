package com.example.qlpmt.KhamBenh;

public class PhieuKhamBenh {
    String stt;
    String thuoc;
    String cachdung;
    String dvi;
    String sl;

    public PhieuKhamBenh(String stt, String thuoc, String cachdung, String dvi, String sl) {
        this.stt = stt;
        this.thuoc = thuoc;
        this.cachdung = cachdung;
        this.dvi = dvi;
        this.sl = sl;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getThuoc() {
        return thuoc;
    }

    public void setThuoc(String thuoc) {
        this.thuoc = thuoc;
    }

    public String getCachdung() {
        return cachdung;
    }

    public void setCachdung(String cachdung) {
        this.cachdung = cachdung;
    }

    public String getDvi() {
        return dvi;
    }

    public void setDvi(String dvi) {
        this.dvi = dvi;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }
}
