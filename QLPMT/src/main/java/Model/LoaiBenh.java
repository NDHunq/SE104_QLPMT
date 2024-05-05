package Model;

public class LoaiBenh {
    private String LoaiBenh_ID;
    private String TenBenh;

    @Override
    public String toString() {
        return TenBenh;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LoaiBenh) {
            return ((LoaiBenh) obj).getLoaiBenh_ID().equals(this.getLoaiBenh_ID()) && ((LoaiBenh) obj).getTenBenh().equals(this.getTenBenh());
        }
        return false;
    }

    public LoaiBenh() {
        LoaiBenh_ID = "";
        TenBenh = "";
    }

    public LoaiBenh(String LoaiBenh_ID, String TenBenh) {
        this.LoaiBenh_ID = LoaiBenh_ID;
        this.TenBenh = TenBenh;
    }

    public String getLoaiBenh_ID() {
        return LoaiBenh_ID;
    }

    public void setLoaiBenh_ID(String loaiBenh_ID) {
        LoaiBenh_ID = loaiBenh_ID;
    }

    public String getTenBenh() {
        return TenBenh;
    }

    public void setTenBenh(String tenBenh) {
        TenBenh = tenBenh;
    }
}
