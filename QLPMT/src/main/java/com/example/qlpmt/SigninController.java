package com.example.qlpmt;
import com.example.qlpmt.KhamBenh.kham_benhController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
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
    private MFXTextField hoten;
    @FXML
    private MFXPasswordField matkhau;
    @FXML
    private MFXPasswordField matkhau2;
    @FXML
    private Button dangky;
    private NhanVienController ccontroller=new NhanVienController();


    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        exit.setOnMouseClicked(event -> {
            // Get the current stage
            Stage stage1 = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            // Close the current stage
            stage1.close();


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
        dangky.setOnAction(event -> {
            insertData();
        });


    }
    public void setController(NhanVienController controller) {
        this.ccontroller = controller;
    }
    private void insertData() {
        // Tạo kết nối với cơ sở dữ liệu
        Connection connection = DBConnectionQuyen.getConnection();
        if (connection == null) {
            return;
        }

        // Kiểm tra xem tất cả các trường đã được điền chưa
        if (matkhau.getText().isEmpty() || matkhau2.getText().isEmpty() || username.getText().isEmpty() || Email.getText().isEmpty() || hoten.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập đầy đủ thông tin");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();

            InputStream iconStream = AppUtils.class.getResourceAsStream("/com/example/qlpmt/images/cong.png");
            Image image = new Image(iconStream);
            alertStage.getIcons().add(image);
            alert.showAndWait();
            return;
        }

        // Kiểm tra mật khẩu
        if (!matkhau.getText().equals(matkhau2.getText())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Sai mật khẩu");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();

            InputStream iconStream = AppUtils.class.getResourceAsStream("/com/example/qlpmt/images/cong.png");
            Image image = new Image(iconStream);
            alertStage.getIcons().add(image);
            alert.showAndWait();
            return;
        }

        // Kiểm tra xem tên người dùng đã tồn tại chưa
        try {
            PreparedStatement checkUserStatement = connection.prepareStatement("SELECT * FROM TaiKhoan WHERE username = ?");
            checkUserStatement.setString(1, username.getText());
            ResultSet resultSet = checkUserStatement.executeQuery();
            if (resultSet.next()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Tên người dùng đã tồn tại");
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();

                InputStream iconStream = AppUtils.class.getResourceAsStream("/com/example/qlpmt/images/cong.png");
                Image image = new Image(iconStream);
                alertStage.getIcons().add(image);


                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Xác định chức vụ
        String chucVu = "Nhân viên";
        if (bs.getStyleClass().contains("chooser")) {
            chucVu = "Bác sĩ";
        }

        // Tạo câu truy vấn SQL
        String sql = "INSERT INTO TaiKhoan (username, Email, mk, ChucVu, HoTen) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username.getText());
            statement.setString(2, Email.getText());
            statement.setString(3, matkhau.getText());
            statement.setString(4, chucVu);
            statement.setString(5, hoten.getText());

            statement.executeUpdate();

            // Show success message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText(null);
            alert.setContentText("Thêm thành công");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();

            InputStream iconStream = AppUtils.class.getResourceAsStream("/com/example/qlpmt/images/cong.png");
            Image image = new Image(iconStream);
            alertStage.getIcons().add(image);
            alert.showAndWait();

            // Clear all input fields
            username.clear();
            Email.clear();
            matkhau.clear();
            matkhau2.clear();
            hoten.clear();
           ccontroller.refreshpage();

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
