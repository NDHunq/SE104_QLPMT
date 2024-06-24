package com.example.qlpmt;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static com.example.qlpmt.loginController.matkhau;
import static com.example.qlpmt.loginController.username;

public class doimatkhauController implements Initializable {
    private java.sql.Connection dbConnection = null;
    @FXML
    MFXPasswordField mkhientai;
    @FXML
    MFXPasswordField mkmoi;
    @FXML
    MFXPasswordField mklai;
    @FXML
    MFXButton xong;
    @FXML
     MFXButton huy;

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String mkcu=matkhau;
    String mkht;
    String mkmoi1;
    String mklai1;
    public void initialize(URL location, ResourceBundle resources) {


        dbConnection = DBConnection.getConnection();
        xong.setOnMouseClicked(event -> {
            mkht=mkhientai.getText();
            mkmoi1=mkmoi.getText();
            mklai1=mklai.getText();

            if(check())
            {
               String sql="update TaiKhoan set mk=? where username=?";
                try {
                    System.out.println(mkmoi1);
                    System.out.println(username);
                    preparedStatement = dbConnection.prepareStatement(sql);
                    preparedStatement.setString(1,mkmoi1);
                    preparedStatement.setString(2,username);
                    preparedStatement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage) xong.getScene().getWindow();
                stage.close();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Đổi mật khẩu thất bại");
                alert.showAndWait();
            }

        });
        huy.setOnMouseClicked(event -> {
            Stage stage = (Stage) huy.getScene().getWindow();
            stage.close();
        });
        //To change body of generated methods, choose Tools | Templates.
    }
    public boolean check()
    {
        if(mkht.equals("")||mkmoi1.equals("")||mklai1.equals(""))
        {
            return false;
        }
        if(mkht.equals(mkmoi1))
        {
            return false;
        }
        if(!mkmoi1.equals(mklai1))
        {
            return false;
        }
        if(!mkht.equals(mkcu))
        {
            return false;
        }
        return true;
    }
}
