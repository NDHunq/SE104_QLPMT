package Model;

public class CachDung {
    private String cachDung_ID;
    private String TenCachDung;

    public CachDung() {
        cachDung_ID = "";
        TenCachDung = "";
    }

    public CachDung(String cachDung_ID, String TenCachDung) {
        this.cachDung_ID = cachDung_ID;
        this.TenCachDung = TenCachDung;
    }

    public String getCachDung_ID() {
        return cachDung_ID;
    }

    public void setCachDung_ID(String cachDung_ID) {
        this.cachDung_ID = cachDung_ID;
    }

    public String getTenCachDung() {
        return TenCachDung;
    }

    public void setTenCachDung(String tenCachDung) {
        TenCachDung = tenCachDung;
    }
}
