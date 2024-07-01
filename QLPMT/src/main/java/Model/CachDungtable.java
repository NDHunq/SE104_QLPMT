package Model;

import javax.swing.*;

public class CachDungtable {
    private int STT;
    private String TenCachDung;
    private String xoa;

    public CachDungtable(int STT, String tenCachDung,String xoa) {

        this.STT = STT;
        TenCachDung = tenCachDung;
        this.xoa=xoa;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }
    public String getXoa() {
        return xoa;
    }

    public void setXoa(String xoa) {
        this.xoa = xoa;
    }

    public String getTenCachDung() {
        return TenCachDung;
    }

    public void setTenCachDung(String tenCachDung) {
        TenCachDung = tenCachDung;
    }


}
