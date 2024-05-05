package Model;
import java.time.LocalDate;
public class Thuoc {
    private Integer stt;
    private String tenthuoc;
    private String donvi;
    private String soluong;
    private  String solandung;
    public Thuoc(int stt,String tenthuoc, String donvi, String soluong,String solandung) {

        this.tenthuoc = tenthuoc;
        this.donvi = donvi;
        this.soluong = soluong;
        this.solandung=solandung;
        this.stt=stt;
    }

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }

    public String getSolandung() {
        return solandung;
    }

    public String getTenthuoc() {
        return tenthuoc;
    }

    public void setTenthuoc(String tenthuoc) {
        this.tenthuoc = tenthuoc;
    }

    public String getDonvi() {
        return donvi;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {

        this.soluong = soluong;
    }
}
