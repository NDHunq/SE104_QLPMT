package com.example.qlpmt;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThemThuocController {
    @FXML
    private MFXButton huy;
    @FXML
    private MFXButton themthuoc;
    @FXML
    private TextField text_tenthuoc;
    @FXML
    private TextField text_soluong;
    @FXML
    private TextField text_dongia;
    @FXML
    private TextField text_dongiaban;
    @FXML
    private MFXComboBox<String>text_cachdung;
    @FXML
    private MFXComboBox<String>text_donvi;
    public PreparedStatement pst=null;
    private ResultSet rs=null;
    private Connection dbConnection = null;


    public void initialize() {
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
        try {
            themthuoc.setOnMouseClicked(event -> {
                String donvi = text_donvi.getValue();
                String cachdung = text_cachdung.getValue();
                int soluong = 0;
                String soluongAsString = "";
                try{
                    String sql = "select CachDung_ID from CachDung where TenCachDung=?";
                    pst = dbConnection.prepareStatement(sql);
                    pst.setString(1,cachdung);
                    rs = pst.executeQuery();
                    while (rs.next()){
                        cachdung = rs.getString("CachDung_ID");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("1");
                try{
                    String sql = "select DVThuoc_ID from DonViThuoc where TenDVTHuoc=?";
                    pst = dbConnection.prepareStatement(sql);
                    pst.setString(1,donvi);
                    rs = pst.executeQuery();
                    while (rs.next()){
                        donvi = rs.getString("DVThuoc_ID");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("2");
                try {
                    String sql="Select count(*) as total from Thuoc";
                    pst = dbConnection.prepareStatement(sql);
                    rs = pst.executeQuery();
                    while (rs.next()){
                        soluong = rs.getInt("total");

                    }
                    soluongAsString = Integer.toString(soluong+1);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("3");
                try {
                    String sql = "insert into Thuoc(Thuoc_ID,TenThuoc,GiaMua,GiaBan,TonKho,CachDung_ID,DonViThuoc_ID) values(?,?,?,?,?,?,?)";
                    pst = dbConnection.prepareStatement(sql);
                    pst.setString(1,"T0"+soluongAsString);
                    pst.setString(2,text_tenthuoc.getText());
                    pst.setString(3,text_dongia.getText());
                    pst.setString(4,text_dongiaban.getText());
                    pst.setString(5,text_soluong.getText());
                    pst.setString(6,cachdung);
                    pst.setString(7,donvi);
                    int rowsAffected = pst.executeUpdate();
                    rowsAffected = pst.executeUpdate();

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("4");
    //                try {
    //                    String sql = "insert into DonViThuoc(TenDVTHuoc) values(?)";
    //                    pst = dbConnection.prepareStatement(sql);
    //                    pst.setString(1,text_donvi.getValue());
    //                    int rowsAffected = pst.executeUpdate();
    //                    rowsAffected = pst.executeUpdate();
    //
    //                }
    //                catch (Exception e){
    //                    e.printStackTrace();
    //                }
                themthuoc.getScene().getWindow().hide();

            });

            // Đóng kết nối

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
