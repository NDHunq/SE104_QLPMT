package com.example.qlpmt;

import Model.KhoThuoc;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThemThuocController {
    @FXML
    public MFXButton huy;
    @FXML
    public MFXButton themthuoc;
    @FXML
    public TextField text_tenthuoc;
    @FXML
    public TextField text_soluong;
    @FXML
    public TextField text_dongia;
    @FXML
    public TextField text_dongiaban;
    @FXML
    public MFXComboBox<String>text_cachdung;
    @FXML
    public MFXComboBox<String>text_donvi;
    public PreparedStatement pst=null;
    private ResultSet rs=null;
    private Connection dbConnection = null;
    private ObservableList<KhoThuoc> KhoThuoc_list;
//    public  ThemThuocController(kho_thuocController kho_thuocController) {
//    }


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
        text_dongiaban.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")|| newValue.equals("0")) {
                text_dongiaban.setText(oldValue);
            }
        });
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
    }

}
