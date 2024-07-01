package Model;

public class LoaiBenhTable {
    private int STT;
    private String TenLoaiBenh;
    private String xoa;

    public LoaiBenhTable(int STT, String tenLoaiBenh,String xoa) {
        this.STT = STT;
        TenLoaiBenh = tenLoaiBenh;
        this.xoa=xoa;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getTenLoaiBenh() {
        return TenLoaiBenh;
    }
    public String getXoa() {
        return xoa;
    }

    public void setXoa(String xoa) {
        this.xoa = xoa;
    }

    public void setTenLoaiBenh(String tenLoaiBenh) {
        TenLoaiBenh = tenLoaiBenh;
    }
}
