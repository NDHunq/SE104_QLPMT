package com.example.qlpmt;
import com.example.qlpmt.Share;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.awt.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ThemcachdungController implements Initializable {
    @FXML
    public TextField them;
    @FXML
    public Button xong;
    @FXML
    public Button huy;
    private java.sql.Connection dbConnection = null;
    private cai_datController controller;

    public ThemcachdungController(cai_datController caiDatController) {
    }

    public void setCaiDatController(cai_datController controller) {
        this.controller = controller;
    }

    public void initialize(URL location, ResourceBundle resources) {
//        dbConnection = DBConnection.getConnection();
//        ThemcachdungController themcachdungController = new ThemcachdungController();
//        cai_datController caiDatController = new cai_datController();
//        themcachdungController.setCaiDatController(caiDatController);
//        xong.setOnMouseClicked(event -> {
//            switch (Share.getInstance().getSharedVariable()) {
//                case "5":
//                    String soluongAsString = "CD";
//                    try
//                    {
//                        int soluong = 0;
//
//                        String sql="Select count(*) as total from CachDung";
//                        PreparedStatement pst = dbConnection.prepareStatement(sql);
//                        ResultSet rs = pst.executeQuery();
//                        while (rs.next()){
//                            soluong = rs.getInt("total");
//
//                        }
//                        soluongAsString += Integer.toString(soluong+1);
//
//                        try {
//                            caiDatController.refreshPage();
//                        } catch (   Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                    catch (SQLException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    try {
//                        PreparedStatement pstmt = dbConnection.prepareStatement("INSERT INTO CachDung VALUES (?,?)");
//                        pstmt.setString(1, soluongAsString);
//                        pstmt.setString(2, them.getText());
//                        pstmt.executeUpdate();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case "6":
//                    String soluongAsString1 = "DV";
//                    try
//                    {
//                        int soluong = 0;
//
//                        String sql="Select count(*) as total from DonViThuoc";
//                        PreparedStatement pst = dbConnection.prepareStatement(sql);
//                        ResultSet rs = pst.executeQuery();
//                        while (rs.next()){
//                            soluong = rs.getInt("total");
//
//                        }
//                        soluongAsString1 += Integer.toString(soluong+1);
//
//                        try {
////                            caiDatController.refreshPage();
//                        } catch (   Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                    catch (SQLException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    try {
//                        PreparedStatement pstmt = dbConnection.prepareStatement("INSERT INTO DonViThuoc VALUES (?,?)");
//                        pstmt.setString(1, soluongAsString1);
//                        pstmt.setString(2, them.getText());
//                        pstmt.executeUpdate();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case "7":
//                    String soluongAsString2 = "LB";
//                    try
//                    {
//                        int soluong = 0;
//
//                        String sql="Select count(*) as total from LoaiBenh";
//                        PreparedStatement pst = dbConnection.prepareStatement(sql);
//                        ResultSet rs = pst.executeQuery();
//                        while (rs.next()){
//                            soluong = rs.getInt("total");
//
//                        }
//                        soluongAsString2 += Integer.toString(soluong+1);
//
//                        try {
////                            caiDatController.refreshPage();
//                        } catch (   Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                    catch (SQLException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    try {
//                        PreparedStatement pstmt = dbConnection.prepareStatement("INSERT INTO LoaiBenh VALUES (?,?)");
//                        pstmt.setString(1, soluongAsString2);
//                        pstmt.setString(2, them.getText());
//                        pstmt.executeUpdate();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//
//            }
//            Stage stage = (Stage) xong.getScene().getWindow();
//            stage.close();
//
//        });
//
//    }
    }
}
