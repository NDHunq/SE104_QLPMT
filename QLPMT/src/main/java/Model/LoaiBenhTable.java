package Model;

public class LoaiBenhTable {
    private int STT;
    private String TenLoaiBenh;

    public LoaiBenhTable(int STT, String tenLoaiBenh) {
        this.STT = STT;
        TenLoaiBenh = tenLoaiBenh;
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

    public void setTenLoaiBenh(String tenLoaiBenh) {
        TenLoaiBenh = tenLoaiBenh;
    }
}
