package com.example.qlpmt;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
public class loginController  implements Initializable {

    @FXML
    private ImageView exit;
    @FXML
    private ImageView minimize;
    @FXML
    private ImageView see;
    @FXML

    private boolean isPasswordVisible = false;
    @FXML

    private Button login;
    @FXML
    private MFXTextField usernameField;
    @FXML
    private MFXPasswordField passwordField;
    @FXML
    private Text quen;



    int quanly = 0;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
        quen.setOnMouseClicked(event -> {
            try {
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);


                Parent root = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/quyenmk.fxml"));

                root.setOnMousePressed(event1 -> {
                    double x = event1.getSceneX();
                    double y = event1.getSceneY();
                    root.setOnMouseDragged(event2 -> {
                        stage.setX(event2.getScreenX() - x);
                        stage.setY(event2.getScreenY() - y);
                    });
                });


                // Create a new scene and set it on the stage
                Scene scene = new Scene(root);
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        login.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (checkLogin(username, password)) {
                try {
                    login.setBackground(new Background(new BackgroundFill(Color.valueOf("#134494"), new CornerRadii(5), new Insets(0))));
                    // Load the new FXML file
                   String link = "/com/example/qlpmt/hello-view2.fxml";
                   if (quanly == 1) {
                            link = "/com/example/qlpmt/hello-view.fxml";
                        }
                    Parent root = FXMLLoader.load(getClass().getResource(link));


                    // Get the current stage
                    Stage stage = (Stage) login.getScene().getWindow();

                    root.setOnMousePressed(event1 -> {
                        double x = event1.getSceneX();
                        double y = event1.getSceneY();
                        root.setOnMouseDragged(event2 -> {
                            stage.setX(event2.getScreenX() - x);
                            stage.setY(event2.getScreenY() - y);
                        });
                    });


                    // Create a new scene and set it on the stage
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
                    stage.setScene(scene);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Đăng nhập thất bại, hiển thị thông báo
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Lỗi đăng nhập");
                alert.setHeaderText(null);
                alert.setContentText("Đăng nhập thất bại");
                alert.showAndWait();
            }
        });
        login.setCursor(Cursor.HAND);

        minimize.setOnMouseClicked(event -> {
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            stage.setIconified(true);
        });


    }

    private boolean checkLogin (String username, String password) {
        // Tạo kết nối với cơ sở dữ liệu
        Connection connection = DBConnection.getConnection();
        if (connection == null) {
            return false;
        }

        // Tạo câu truy vấn SQL
        String sql = "SELECT * FROM TaiKhoan WHERE username = ? AND mk = ? AND ChucVu !='NGHI'";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            // Nếu có kết quả trả về, kiểm tra cột ChucVu
            if (resultSet.next()) {
                String chucVu = resultSet.getString("ChucVu");
                if ("Quản lý".equals(chucVu)) {
                    quanly = 1;
                } else {
                    quanly = 0;
                }
                connection.close();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Nếu không có kết quả trả về, đóng kết nối và trả về false
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}