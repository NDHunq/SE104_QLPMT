package Model;

public class DonViThuocTable {
    private int STT;
    private String TenDonVi;
    private String xoa;

    public DonViThuocTable(int STT, String tenDonVi,String xoa) {
        this.STT = STT;
        TenDonVi = tenDonVi;
        this.xoa=xoa;
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
    public String getXoa() {
        return xoa;
    }

    public void setXoa(String xoa) {
        this.xoa = xoa;
    }
}
