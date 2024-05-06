package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PhieuKhamBenh{

    private String idPKB;
    private String idDSKB;
    private TaiKhoanNP nguoiKham = new TaiKhoanNP();
    private int stt;
    private String cccd;
    private String hoTen;
    private LoaiBenh loaiBenh = new LoaiBenh();
    private String trieuChung;
    private LocalDate ngayKham;
    DateTimeFormatter string_formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PhieuKhamBenh() {
        idPKB = "";
        idDSKB = "";
        nguoiKham = new TaiKhoanNP();
        stt = -123981;
        cccd = "";
        hoTen = "";
        loaiBenh = new LoaiBenh();
        trieuChung = "";
        ngayKham = LocalDate.now();
    }

    public PhieuKhamBenh(int stt, String Cccd, String HoTen, String LoaiBenh, String TrieuChung, LocalDate NgayKham) {
        this.stt = stt;
        this.cccd = Cccd;
        this.hoTen = HoTen;
        this.loaiBenh.setTenBenh(LoaiBenh);
        this.trieuChung = TrieuChung;
        this.ngayKham = NgayKham;
    }

    public PhieuKhamBenh(String idPKB, int stt, String cccd, String hoTen, String loaiBenh, String trieuChung, LocalDate ngayKham) {
        this.idPKB = idPKB;
        this.stt = stt;
        this.cccd = cccd;
        this.hoTen = hoTen;
        this.loaiBenh.setTenBenh(loaiBenh);
        this.trieuChung = trieuChung;
        this.ngayKham = ngayKham;
    }

    public PhieuKhamBenh(String idPKB, String idLoaiBenh, String idDSKB, String idNguoiKham, int stt, String cccd, String hoTen, String loaiBenh, String trieuChung,String nguoiKham ,LocalDate ngayKham) {
        this.idPKB = idPKB;
        this.loaiBenh.setLoaiBenh_ID(idLoaiBenh);
        this.idDSKB = idDSKB;
        this.nguoiKham.setUsername(idNguoiKham);
        this.stt = stt;
        this.cccd = cccd;
        this.hoTen = hoTen;
        this.loaiBenh.setTenBenh(loaiBenh);
        this.trieuChung = trieuChung;
        this.nguoiKham.setHoTen(nguoiKham);
        this.ngayKham = ngayKham;
    }

    public String getIdDSKB() {
        return idDSKB;
    }

    public void setIdDSKB(String idDSKB) {
        this.idDSKB = idDSKB;
    }

    public String getIdPKB() {
        return idPKB;
    }

    public void setIdPKB(String idPKB) {
        this.idPKB = idPKB;
    }

    public TaiKhoanNP getNguoiKham() {
        return nguoiKham;
    }

    public String getNguoiKham_username(){
        return nguoiKham.getUsername();
    }

    public String getNguoiKham_hoTen(){
        return nguoiKham.getHoTen();
    }

    public void setNguoiKham(TaiKhoanNP nguoiKham) {
        this.nguoiKham = nguoiKham;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String Cccd) {
        this.cccd = Cccd;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String HoTen) {
        this.hoTen = HoTen;
    }

    public LoaiBenh getLoaiBenh() {
        return loaiBenh;
    }

    public String getTenBenh(){
        return loaiBenh.getTenBenh();
    }

    public String getLoaiBenh_ID(){
        return loaiBenh.getLoaiBenh_ID();
    }

    public void setLoaiBenh(LoaiBenh LoaiBenh) {
        this.loaiBenh = LoaiBenh;
    }

    public String getTrieuChung() {
        return trieuChung;
    }

    public void setTrieuChung(String TrieuChung) {
        this.trieuChung = TrieuChung;
    }

    public LocalDate getNgayKham() {
        return ngayKham;
    }

    public String getNgayKham_string(){
        return String.format("%s", ngayKham.format(string_formatter));
    }

    public void setNgayKham(LocalDate NgayKham) {
        this.ngayKham = NgayKham;
    }
}
