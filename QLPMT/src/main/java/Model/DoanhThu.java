package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DoanhThu {
    private int id;
    private LocalDate ngayKham;
    private String doanhThu;
    private int soLuongBenhNhan;
    private float tiLe;
    DateTimeFormatter string_formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DoanhThu(int id, LocalDate ngayKham, int soLuongBenhNhan, String doanhThu, float tiLe) {
        this.id = id;
        this.ngayKham = ngayKham;
        this.doanhThu = doanhThu;
        this.soLuongBenhNhan = soLuongBenhNhan;
        this.tiLe = tiLe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getNgayKham() {
        return ngayKham;
    }

    public String getNgayKham_string() {
        return String.format("%s", ngayKham.format(string_formatter));
    }

    public void setNgayKham(LocalDate ngayKham) {
        this.ngayKham = ngayKham;
    }

    public void setNgayKham(String ngayKham) {
        this.ngayKham = LocalDate.parse(ngayKham, string_formatter);
    }

    public String getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(String doanhThu) {
        this.doanhThu = doanhThu;
    }

    public int getSoLuongBenhNhan() {
        return soLuongBenhNhan;
    }

    public void setSoLuongBenhNhan(int soLuongBenhNhan) {
        this.soLuongBenhNhan = soLuongBenhNhan;
    }

    public float getTiLe() {
        return tiLe;
    }

    public void setTiLe(float tiLe) {
        this.tiLe = tiLe;
    }


}
