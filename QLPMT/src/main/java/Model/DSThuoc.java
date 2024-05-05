package Model;

public class DSThuoc {
    private String thuoc_ID;
    private String tenThuoc;
    private double giaMua;
    private double giaBan;
    private long tonKho;
    private CachDung cachDung;
    private DonViThuoc donViThuoc;

    public DSThuoc() {
        thuoc_ID = "";
        tenThuoc = "";
        giaMua = 0;
        giaBan = 0;
        tonKho = 0;
        cachDung = new CachDung();
        donViThuoc = new DonViThuoc();
    }

    public DSThuoc(String thuoc_ID, String tenThuoc, double giaMua, double giaBan, long tonKho, CachDung cachDung, DonViThuoc donViThuoc) {
        this.thuoc_ID = thuoc_ID;
        this.tenThuoc = tenThuoc;
        this.giaMua = giaMua;
        this.giaBan = giaBan;
        this.tonKho = tonKho;
        this.cachDung = cachDung;
        this.donViThuoc = donViThuoc;
    }

    public String getThuoc_ID() {
        return thuoc_ID;
    }

    public void setThuoc_ID(String thuoc_ID) {
        this.thuoc_ID = thuoc_ID;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public double getGiaMua() {
        return giaMua;
    }

    public void setGiaMua(double giaMua) {
        this.giaMua = giaMua;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public long getTonKho() {
        return tonKho;
    }

    public void setTonKho(long tonKho) {
        this.tonKho = tonKho;
    }



    public CachDung getCachDung() {
        return cachDung;
    }

    public void setCachDung(CachDung cachDung) {
        this.cachDung = cachDung;
    }

    public DonViThuoc getDonViThuoc() {
        return donViThuoc;
    }

    public void setDonViThuoc(DonViThuoc donViThuoc) {
        this.donViThuoc = donViThuoc;
    }
}
