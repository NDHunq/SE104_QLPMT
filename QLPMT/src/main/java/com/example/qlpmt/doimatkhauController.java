package com.example.qlpmt;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;

import java.io.InputStream;
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
    boolean validationmk;
    private Validator validatormk = new Validator();

    public void Validator()
    {
        mkht=mkhientai.getText();
        mkmoi1=mkmoi.getText();
        mklai1=mklai.getText();
        validatormk.createCheck()
                .withMethod(c -> {
                    if (mkhientai.getText().equals("")
                    ) {
                        mkhientai.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error(" Mật khẩu hiện tại không được để trống!");
                    }
                    else {
                        mkhientai.setStyle("");
                    }
                })
                .dependsOn("thongtin", mkhientai.textProperty())
                .decorates(mkhientai)
                .immediate();
        validatormk.createCheck()
                .withMethod(c -> {
                    if (mkmoi.getText().equals("")) {
                        mkmoi.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error(" Mật khẩu mới không được để trống!");
                    }
                    else {
                        mkmoi.setStyle("");
                    }
                })
                .dependsOn("thongtin", mkmoi.textProperty())
                .decorates(mkmoi)
                .immediate();
        validatormk.createCheck()
                .withMethod(c -> {
                    if (mklai.getText().equals("")) {
                        mklai.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error(" Mật khẩu nhập lại không được để trống!");
                    }
                    else {
                        mklai.setStyle("");
                    }
                })
                .dependsOn("thongtin", mklai.textProperty())
                .decorates(mklai)
                .immediate();
        validatormk.createCheck()
                .withMethod(c -> {
                    if (!mkht.equals(mkcu)) {
                        mkhientai.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error(" Mật khẩu hiện tại không đúng!");
                    }
                    else {
                        mkhientai.setStyle("");
                    }
                })
                .dependsOn("thongtin", mkhientai.textProperty())
                .decorates(mkhientai)
                .immediate();
        validatormk.createCheck()
                .withMethod(c -> {
                    if (mkht.equals(mkmoi1)) {
                        mkmoi.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error(" Mật khẩu mới phải khác mật khẩu hiện tại!");
                    }
                    else {
                        mkmoi.setStyle("");
                    }
                })
                .dependsOn("thongtin", mkmoi.textProperty())
                .decorates(mkmoi)
                .immediate();
        validatormk.createCheck()
                .withMethod(c -> {
                    if (!mklai1.equals(mkmoi1)
                    ) {
                        mklai.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error(" Mật khẩu mới không khớp!");
                    }
                    else {
                        mklai.setStyle("");
                    }
                })
                .dependsOn("thongtin", mklai.textProperty())
                .decorates(mklai)
                .immediate();


        validationmk = validatormk.validate();
        System.out.println(validationmk);
    }
    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Đổi mật khẩu thành công");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        InputStream iconStream = AppUtils.class.getResourceAsStream("/com/example/qlpmt/images/cong.png");
        Image image = new Image(iconStream);
        alertStage.getIcons().add(image);

        alert.showAndWait();
    }
    public void initialize(URL location, ResourceBundle resources) {


        dbConnection = DBConnection.getConnection();
        xong.setOnMouseClicked(event -> {
            Validator();


            if(validationmk)
            {
                matkhau=mkmoi1;
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
                showAlert();
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
