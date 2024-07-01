package com.example.qlpmt;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Inf_TK_controller implements Initializable {
    @FXML
    private Text usernamee; // assuming this is the Text field where you want to display the username


    @FXML
    private ImageView exit;
    @FXML
    private ImageView minimize;
    @FXML
    private MFXTextField Email;
    @FXML
    private MFXTextField hoten;
    @FXML
    private MFXPasswordField matkhau;
    @FXML
    private Button luu;
    @FXML
    private Button huy;
    @FXML
    private BorderPane bs;
    @FXML
    private BorderPane nv;
    @FXML
    private Text bstxt;
    @FXML
    private Text nvtxt;
    private NhanVienController ccontroller=new NhanVienController();


    private String usr ;
    public void setUsr(String usr) {
        this.usr = usr;
        displayUserInfo(usr); // replace "selectedUsername" with the actual selected username
    }
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        // Call the method to fetch and display the user information when the controller is initialized
        nv.setCursor(javafx.scene.Cursor.HAND);
        bs.setCursor(javafx.scene.Cursor.HAND);

        exit.setOnMouseClicked(event -> {
            // Get the current stage
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            // Close the stage
            stage.close();
        });
        minimize.setOnMouseClicked(event -> {
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            stage.setIconified(true);
        });


        nv.setOnMouseClicked(event -> {
            nv.getStyleClass().clear();
            nv.getStyleClass().add("chooser");
            bs.getStyleClass().clear();

            bstxt.setFill(Color.web("#7b7b7b"));
            nvtxt.setFill(Color.web("#2264d1"));
        });

        bs.setOnMouseClicked(event -> {
            bs.getStyleClass().clear();
            bs.getStyleClass().add("chooser");
            nv.getStyleClass().clear();

            nvtxt.setFill(Color.web("#7b7b7b"));
            bstxt.setFill(Color.web("#2264d1"));
        });
        luu.setOnAction(event -> {
            // Get the current stage
            Stage stage = (Stage) luu.getScene().getWindow();
            // Close the stage
            stage.close();
            saveUserInfo();

        });
        huy.setOnAction(event -> {
            // Get the current stage
            Stage stage = (Stage) huy.getScene().getWindow();
            // Close the stage
            stage.close();
        });
    }
    public void setController(NhanVienController controller) {
        this.ccontroller = controller;
    }

    private void displayUserInfo(String usr) {
        try {
            Connection connection = DBConnection.getConnection();
            String sql = "SELECT * FROM TaiKhoan WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,usr);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                if(rs.getString("ChucVu").equals("Nhân viên"))
                {
                    nv.getStyleClass().clear();
                    nv.getStyleClass().add("chooser");
                    bs.getStyleClass().clear();

                    bstxt.setFill(Color.web("#7b7b7b"));
                    nvtxt.setFill(Color.web("#2264d1"));

                }
                else
                {
                    bs.getStyleClass().clear();
                    bs.getStyleClass().add("chooser");
                    nv.getStyleClass().clear();
                    nvtxt.setFill(Color.web("#7b7b7b"));
                    bstxt.setFill(Color.web("#2264d1"));
                }
                usernamee.setText(rs.getString("username")) ;
                hoten.setText(rs.getString("HoTen"));
                matkhau.setText(rs.getString("mk"));
                Email.setText(rs.getString("Email"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void saveUserInfo() {
        try {
            Connection connection = DBConnection.getConnection();

            // Determine the ChucVu value
            String chucVu = nv.getStyleClass().contains("chooser") ? "Nhân viên" : "Bác sĩ";

            // Modify the SQL query
            String sql = "UPDATE TaiKhoan SET HoTen = ?, mk = ?, Email = ?, ChucVu = ? WHERE username = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, hoten.getText());
            statement.setString(2, matkhau.getText());
            statement.setString(3, Email.getText());
            statement.setString(4, chucVu); // Set the ChucVu value
            statement.setString(5, usernamee.getText());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User information updated successfully.");

                // Show a success alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Cập nhật thông tin thành công.");
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();

                InputStream iconStream = AppUtils.class.getResourceAsStream("/com/example/qlpmt/images/cong.png");
                Image image = new Image(iconStream);
                alertStage.getIcons().add(image);
                alert.showAndWait();
            }
            connection.close();
            ccontroller.refreshpage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}