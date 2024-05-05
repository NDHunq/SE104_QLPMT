package Model;

public class NhanVien {

    String Hoten;
    String Chucvu;
    String username;
    String email;

    public NhanVien(String Hoten, String Chucvu, String username, String email) {

        this.Hoten = Hoten;
        this.Chucvu = Chucvu;
        this.username = username;
        this.email = email;
    }


    public String getHoten () {
        return Hoten;
    }

    public void setHoten (String hoten) {
        Hoten = hoten;
    }

    public String getChucvu () {
        return Chucvu;
    }

    public void setChucvu (String chucvu) {
        Chucvu = chucvu;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }
}
