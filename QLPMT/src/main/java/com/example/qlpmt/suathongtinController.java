package com.example.qlpmt;
import com.example.qlpmt.Share;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class suathongtinController implements Initializable {
    @FXML
    public TextField suathongtin;
    @FXML
    public Button xong;
    @FXML
    public Button huy;
    private java.sql.Connection dbConnection = null;
    private cai_datController controller;
    public suathongtinController(cai_datController controller) {
        this.controller = controller;
    }


    public void initialize(URL location, ResourceBundle resources) {
        dbConnection = DBConnection.getConnection();
        xong.setOnMouseClicked(event -> {
            switch (Share.getInstance().getSharedVariable()) {
                case "2":
                    this.controller.hoten.setText(suathongtin.getText());
                    String hoten = suathongtin.getText();
                    String sql2 = "UPDATE TaiKhoan SET Hoten=? WHERE IdTT='TT01'";
                    try {
                        PreparedStatement pstmt = dbConnection.prepareStatement(sql2);
//                        pstmt.setString(1, tienkham);
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "3":
                    this.controller.tienkham.setText(suathongtin.getText());
                    String tienkham = suathongtin.getText();
                    String sql = "UPDATE ThongTinPK SET Gtri=? WHERE IdTT='TT01'";
                    try {
                        PreparedStatement pstmt = dbConnection.prepareStatement(sql);
                        pstmt.setString(1, tienkham);
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "4":
                    this.controller.bntoida.setText(suathongtin.getText());
                    String bntoida = suathongtin.getText();
                    String sql1 = "UPDATE ThongTinPK SET Gtri=? WHERE IdTT='TT02'";
                    try {
                        PreparedStatement pstmt = dbConnection.prepareStatement(sql1);
                        pstmt.setString(1, bntoida);
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

            }
            Stage stage = (Stage) xong.getScene().getWindow();
            stage.close();

        });
        huy.setOnMouseClicked(event -> {
            Stage stage = (Stage) huy.getScene().getWindow();
            stage.close();
        });
    }
}
