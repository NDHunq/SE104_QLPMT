package Model;

import javafx.collections.ObservableArray;

import java.time.LocalDate;
import java.util.Date;

public class PhieuKhamBenh{

    private String Cccd;
    private String HoTen;
    private String LoaiBenh;
    private String TrieuChung;
    private LocalDate NgayKham;

    public PhieuKhamBenh(String Cccd, String HoTen, String LoaiBenh, String TrieuChung, LocalDate NgayKham) {
        this.Cccd = Cccd;
        this.HoTen = HoTen;
        this.LoaiBenh = LoaiBenh;
        this.TrieuChung = TrieuChung;
        this.NgayKham = NgayKham;
    }

    public String getCccd() {
        return Cccd;
    }

    public void setCccd(String Cccd) {
        this.Cccd = Cccd;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getLoaiBenh() {
        return LoaiBenh;
    }

    public void setLoaiBenh(String LoaiBenh) {
        this.LoaiBenh = LoaiBenh;
    }

    public String getTrieuChung() {
        return TrieuChung;
    }

    public void setTrieuChung(String TrieuChung) {
        this.TrieuChung = TrieuChung;
    }

    public LocalDate getNgayKham() {
        return NgayKham;
    }

    public String getNgayKham_string(){
        return NgayKham.toString();
    }

    public void setNgayKham(LocalDate NgayKham) {
        this.NgayKham = NgayKham;
    }
}
