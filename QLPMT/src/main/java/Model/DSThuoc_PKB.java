package Model;

public class DSThuoc_PKB {
    private PhieuKhamBenh phieuKhamBenh = new PhieuKhamBenh();
    private DSThuoc Thuoc = new DSThuoc();
    private int soLuong;

    public DSThuoc_PKB() {
        phieuKhamBenh = new PhieuKhamBenh();
        Thuoc = new DSThuoc();
        soLuong = 0;
    }

    public DSThuoc_PKB(PhieuKhamBenh phieuKhamBenh, DSThuoc Thuoc, int soLuong) {
        this.phieuKhamBenh = phieuKhamBenh;
        this.Thuoc = Thuoc;
        this.soLuong = soLuong;
    }

    public PhieuKhamBenh getPhieuKhamBenh() {
        return phieuKhamBenh;
    }

    public void setPhieuKhamBenh(PhieuKhamBenh phieuKhamBenh) {
        this.phieuKhamBenh = phieuKhamBenh;
    }

    public String getThuoc_ID() {
        return Thuoc.getThuoc_ID();
    }

    public String getTenThuoc() {
        return Thuoc.getTenThuoc();
    }

    public String getThuoc_DVT() {
        return Thuoc.getDonViThuoc().getTenDonViThuoc();
    }

    public String getThuoc_DVT_ID(){
        return Thuoc.getDonViThuoc().getDonViThuoc_ID();
    }

    public String getThuoc_CachDung() {
        return Thuoc.getCachDung().getTenCachDung();
    }

    public String getThuoc_CachDung_ID() {
        return Thuoc.getCachDung().getCachDung_ID();
    }

    public DSThuoc getThuoc() {
        return Thuoc;
    }

    public void setThuoc(DSThuoc thuoc) {
        Thuoc = thuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
