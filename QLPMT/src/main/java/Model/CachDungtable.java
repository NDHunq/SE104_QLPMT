package Model;

public class CachDungtable {
    private int STT;
    private String TenCachDung;

    public CachDungtable(int STT, String tenCachDung) {

        this.STT = STT;
        TenCachDung = tenCachDung;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public String getTenCachDung() {
        return TenCachDung;
    }

    public void setTenCachDung(String tenCachDung) {
        TenCachDung = tenCachDung;
    }
}
