package Model;

public class TaiKhoanNP {
    private String userName;
    private String hoTen;

    @Override
    public String toString() {
        return userName;
    }

    public TaiKhoanNP() {
        userName = "";
        hoTen = "";
    }

    public TaiKhoanNP(String username, String HoTen) {
        this.userName = username;
        this.hoTen = HoTen;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
}
