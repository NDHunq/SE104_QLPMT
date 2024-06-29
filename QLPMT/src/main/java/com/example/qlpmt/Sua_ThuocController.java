package com.example.qlpmt;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Sua_ThuocController {
    public PreparedStatement pst=null;
    private ResultSet rs=null;
    private java.sql.Connection dbConnection = null;
    @FXML
    public TextField text_tenthuoc;//
    @FXML
    public MFXComboBox<String>text_cachdung;
    @FXML
    public MFXComboBox<String>text_donvi;
    @FXML
    public  TextField text_soluong;
    @FXML
    public TextField text_dongia;
    @FXML
    public TextField text_dongianhap;
    @FXML
    private MFXButton huy;
    @FXML
    public MFXButton suathuoc;
    public String Thuoc_ID;
    public String DonViThuoc_ID;
    public void initialize() {
        text_soluong.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")|| newValue.equals("0")) {
                text_soluong.setText(oldValue);
            }
        });
        text_dongia.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")|| newValue.equals("0")) {
                text_dongia.setText(oldValue);
            }
        });
        text_dongianhap.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")|| newValue.equals("0")) {
                text_dongianhap.setText(oldValue);
            }
        });
        dbConnection = DBConnection.getConnection();
        huy.setOnMouseClicked(event -> {
            huy.getScene().getWindow().hide();
        });
        dbConnection = DBConnection.getConnection();
        List<String> drugcachdung = new ArrayList<>();
        List<String> drugdonvi = new ArrayList<>();
        text_tenthuoc.setEditable(true);

        try {

            // Thực hiện truy vấn để lấy tên thuốc
            String sql = "SELECT TenCachDung FROM CachDung";
            pst=dbConnection.prepareStatement(sql);

            rs=pst.executeQuery();

            // Lưu kết quả truy vấn vào mảng
            while (rs.next()) {
                drugcachdung.add(rs.getString("TenCachDung"));

            }
            text_cachdung.getItems().addAll(drugcachdung);
            // Đóng kết nối

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            // Thực hiện truy vấn để lấy tên thuốc
            String sql = "SELECT TenDVTHuoc FROM DonViThuoc";
            pst=dbConnection.prepareStatement(sql);

            rs=pst.executeQuery();

            // Lưu kết quả truy vấn vào mảng
            while (rs.next()) {
                drugdonvi.add(rs.getString("TenDVTHuoc"));
            }
            text_donvi.getItems().addAll(drugdonvi);
            // Đóng kết nối

        } catch (Exception e) {
            e.printStackTrace();
        }
//        suathuoc.setOnMouseClicked(event -> {
//            try {
//                String sql = "update Thuoc set TenThuoc=?,TonKho=?,GiaMua=? where Thuoc_ID=?";
//                pst = dbConnection.prepareStatement(sql);
//                pst.setString(1,text_tenthuoc.getText());
//                pst.setString(2,text_soluong.getText());
//                pst.setString(3,text_dongia.getText());
//                pst.setString(4,Thuoc_ID);
//                int rowsAffected = pst.executeUpdate();
//                rowsAffected = pst.executeUpdate();
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//            try {
//                String sql = "update DonViThuoc set TenDVTHuoc=? where DVTHuoc_ID=?";
//                pst = dbConnection.prepareStatement(sql);
//                pst.setString(1,text_donvi.getText());
//                pst.setString(2,Thuoc_ID);
//                int rowsAffected = pst.executeUpdate();
//                rowsAffected = pst.executeUpdate();
//
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//            suathuoc.getScene().getWindow().hide();
//
//        });
    }
}
