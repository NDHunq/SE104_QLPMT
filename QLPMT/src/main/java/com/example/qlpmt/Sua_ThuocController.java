package com.example.qlpmt;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Sua_ThuocController {
    public PreparedStatement pst=null;
    private ResultSet rs=null;
    private java.sql.Connection dbConnection = null;
    @FXML
    public TextField text_tenthuoc;//
    public TextField text_donvi;
    public  TextField text_soluong;
    public TextField text_dongia;
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
        dbConnection = DBConnection.getConnection();
        huy.setOnMouseClicked(event -> {
            huy.getScene().getWindow().hide();
        });
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
