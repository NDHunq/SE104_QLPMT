package com.example.qlpmt;
import com.example.qlpmt.Share;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

import java.awt.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.qlpmt.loginController.username;

public class SuacachdungController implements Initializable {
    @FXML
    public TextField suathongtin;
    @FXML
    public Button xong;
    @FXML
    public Button huy;
    private java.sql.Connection dbConnection = null;
    private cai_datController controller;
    private Validator validator = new Validator();
    boolean validationResult ;
    public  SuacachdungController(cai_datController controller) {
        this.controller = controller;
    }
    List<String> cd = new ArrayList<>();
    List<String> dv = new ArrayList<>();
    List<String> lb = new ArrayList<>();
    public void Validator() {
        System.out.println(Share.getInstance().getSharedVariable());
        validator.createCheck()
                .withMethod(c -> {
                    if (suathongtin.getText().equals("")
                    ) {
                        suathongtin.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error(" Không được để trống thông tin!");


                    }
                    else {
                        suathongtin.setStyle("-fx-border-color: #2264D1; -fx-text-fill: black");

                    }
                })
                .dependsOn("thongtin", suathongtin.textProperty())
                .decorates(suathongtin)
                .immediate();
        switch (Share.getInstance().getSharedVariable()) {
            case "8":

                validator.createCheck()
                        .withMethod(c -> {
                            if (cd.contains(suathongtin.getText())&&Share.getInstance().getSharedVariable().equals("8")) {
                                suathongtin.setStyle("-fx-border-color: red; -fx-text-fill: red");
                                c.error("Cách dùng đã tồn tại!");
                            }
                            else {
                                suathongtin.setStyle("-fx-border-color: #2264D1; -fx-text-fill: black");

                            }
                        })
                        .dependsOn("thongtin", suathongtin.textProperty())
                        .decorates(suathongtin)
                        .immediate();
                break;
            case "9":
                validator.createCheck()
                        .withMethod(c -> {
                            if (dv.contains(suathongtin.getText())&&Share.getInstance().getSharedVariable().equals("9")) {
                                suathongtin.setStyle("-fx-border-color: red; -fx-text-fill: red");
                                c.error("Đơn vị thuốc đã tồn tại!");
                            }
                            else {
                                suathongtin.setStyle("-fx-border-color: #2264D1; -fx-text-fill: black");

                            }
                        })
                        .dependsOn("thongtin", suathongtin.textProperty())
                        .decorates(suathongtin)
                        .immediate();
                break;
            case "10":
                validator.createCheck()
                        .withMethod(c -> {
                            if (lb.contains(suathongtin.getText())&&Share.getInstance().getSharedVariable().equals("10")) {
                                suathongtin.setStyle("-fx-border-color: red; -fx-text-fill: red");
                                c.error("Bệnh đã tồn tại!");
                            }
                            else {
                                suathongtin.setStyle("-fx-border-color: #2264D1; -fx-text-fill: black");

                            }
                        })
                        .dependsOn("thongtin", suathongtin.textProperty())
                        .decorates(suathongtin)
                        .immediate();
                break;
        }



        validationResult = validator.validate();
    }

    public void initialize(URL location, ResourceBundle resources) {
        dbConnection = DBConnection.getConnection();
        String sql = "SELECT TenCachDung  FROM CachDung";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.execute();
            while (preparedStatement.getResultSet().next()) {
                cd.add(preparedStatement.getResultSet().getString("TenCachDung"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String sql1 = "SELECT TenDVTHuoc  FROM DonViThuoc";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql1);
            preparedStatement.execute();
            while (preparedStatement.getResultSet().next()) {
                dv.add(preparedStatement.getResultSet().getString("TenDVTHuoc"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String sql2 = "SELECT TenBenh  FROM LoaiBenh";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql2);
            preparedStatement.execute();
            while (preparedStatement.getResultSet().next()) {
                lb.add(preparedStatement.getResultSet().getString("TenBenh"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        huy.setOnMouseClicked(event -> {
            Stage stage = (Stage) huy.getScene().getWindow();
            stage.close();
        });
    }
}
