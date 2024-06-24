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
import java.util.ResourceBundle;

import static com.example.qlpmt.loginController.username;

public class suathongtinController implements Initializable {
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
    public suathongtinController(cai_datController controller) {
        this.controller = controller;
    }

public void Validator() {
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
    validationResult = validator.validate();
}

    public void initialize(URL location, ResourceBundle resources) {
        dbConnection = DBConnection.getConnection();
        xong.setOnMouseClicked(event -> {
           Validator();
            if(validationResult) {
                switch (Share.getInstance().getSharedVariable()) {
                    case "2":

                        this.controller.hoten.setText(suathongtin.getText());
                        String hoten = suathongtin.getText();
                        String sql2 = "UPDATE TaiKhoan SET Hoten=? WHERE username=?";
                        try {
                            PreparedStatement pstmt = dbConnection.prepareStatement(sql2);
//
                            pstmt.setString(1, hoten);
                            pstmt.setString(2, username);
                            pstmt.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Stage stage = (Stage) xong.getScene().getWindow();
                        stage.close();

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
            }



        });
        huy.setOnMouseClicked(event -> {
            Stage stage = (Stage) huy.getScene().getWindow();
            stage.close();
        });
    }
}
