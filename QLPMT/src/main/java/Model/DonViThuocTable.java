package Model;

public class DonViThuocTable {
    private int STT;
    private String TenDonVi;

    public DonViThuocTable(int STT, String tenDonVi) {
        this.STT = STT;
        TenDonVi = tenDonVi;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getTenDonVi() {
        return TenDonVi;
    }

    public void setTenDonVi(String tenDonVi) {
        TenDonVi = tenDonVi;
    }
}
