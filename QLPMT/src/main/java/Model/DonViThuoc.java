package Model;

import javafx.fxml.FXML;

public class DonViThuoc {
    private String DonViThuoc_ID;
    private String TenDonViThuoc;

    @Override
    public String toString() {
        return TenDonViThuoc;
    }

    public DonViThuoc() {
        DonViThuoc_ID = "";
        TenDonViThuoc = "";
    }

    public DonViThuoc(String DonViThuoc_ID, String TenDonViThuoc) {
        this.DonViThuoc_ID = DonViThuoc_ID;
        this.TenDonViThuoc = TenDonViThuoc;
    }

    public String getDonViThuoc_ID() {
        return DonViThuoc_ID;
    }

    public void setDonViThuoc_ID(String donViThuoc_ID) {
        DonViThuoc_ID = donViThuoc_ID;
    }

    public String getTenDonViThuoc() {
        return TenDonViThuoc;
    }

    public void setTenDonViThuoc(String tenDonViThuoc) {
        TenDonViThuoc = tenDonViThuoc;
    }
}
