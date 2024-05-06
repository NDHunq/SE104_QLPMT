package Model;

public class TongDoanhThu {
    private int thang;
    private int nam;
    private String tongdoanhThu;

    public TongDoanhThu(){
        thang = 0;
        nam = 0;
        tongdoanhThu = "0";
    }

    public TongDoanhThu(int thang, int nam, String doanhThu) {
        this.thang = thang;
        this.nam = nam;
        this.tongdoanhThu = doanhThu;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public String getTongDoanhThu() {
        return tongdoanhThu;
    }

    public void setDoanhThu(String doanhThu) {
        this.tongdoanhThu = doanhThu;
    }
}
