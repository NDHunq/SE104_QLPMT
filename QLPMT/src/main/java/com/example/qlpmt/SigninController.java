package com.example.qlpmt;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SigninController   implements Initializable {
    @FXML
    private ImageView exit;
    @FXML
    private ImageView see;
    @FXML
    private ImageView see1;
    @FXML
    private ImageView minimize;
    @FXML
    private BorderPane bs;
    @FXML
    private BorderPane nv;

    @FXML
    private Text bstxt;
    @FXML
    private Text nvtxt;

    @FXML
    private MFXTextField username;
    @FXML
    private MFXTextField Email;
    @FXML
    private MFXPasswordField matkhau;
    @FXML
    private MFXPasswordField matkhau2;
    @FXML
    private Button dangky;


    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
        nv.setCursor(javafx.scene.Cursor.HAND);
        bs.setCursor(javafx.scene.Cursor.HAND);

        minimize.setOnMouseClicked(event -> {
            Stage stage = (Stage)((ImageView)event.getSource()).getScene().getWindow();
            stage.setIconified(true);
        });
        nv.setOnMouseClicked(event -> {
            nv.getStyleClass().add("chooser");
            bs.getStyleClass().remove("chooser");

            bstxt.setFill(Color.web("#7b7b7b"));
            nvtxt.setFill(Color.web("#2264d1"));


        });
        bs.setOnMouseClicked(event -> {
            bs.getStyleClass().add("chooser");
            nv.getStyleClass().remove("chooser");
            nvtxt.setFill(Color.web("#7b7b7b"));
            bstxt.setFill(Color.web("#2264d1"));


        });


    }
    private void insertData() {
        // Tạo kết nối với cơ sở dữ liệu
        Connection connection = DBConnectionQuyen.getConnection();
        if (connection == null) {
            return;
        }

        // Kiểm tra mật khẩu
        if (!matkhau.getText().equals(matkhau2.getText())) {
            // Mật khẩu không khớp
            return;
        }

        // Xác định chức vụ
        String chucVu = "Nhân viên";
        if (bs.getStyleClass().contains("chooser")) {
            chucVu = "Bác sĩ";
        }

        // Tạo câu truy vấn SQL
        String sql = "INSERT INTO TaiKhoan (username, Email, mk, ChucVu) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = ((Connection) connection).prepareStatement(sql);
            statement.setString(1, username.getText());
            statement.setString(2, Email.getText());
            statement.setString(3, matkhau.getText());
            statement.setString(4, chucVu);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Đóng kết nối
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
