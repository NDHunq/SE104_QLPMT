package com.example.qlpmt;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    private MFXTextField Email;
    @FXML
    private MFXTextField hoten;
    @FXML
    private MFXPasswordField matkhau;
    @FXML
    private Button luu;
    @FXML
    private Button huy;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        // Call the method to fetch and display the user information when the controller is initialized
        displayUserInfo("selectedUsername"); // replace "selectedUsername" with the actual selected username
        luu.setOnAction(event -> saveUserInfo());
        huy.setOnAction(event -> {
            // Get the current stage
            Stage stage = (Stage) huy.getScene().getWindow();
            // Close the stage
            stage.close();
        });
    }
    private void displayUserInfo(String usr) {
        try {
            Connection connection = DBConnectionQuyen.getConnection();
            String sql = "SELECT * FROM TaiKhoan WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,usr);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
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
            Connection connection = DBConnectionQuyen.getConnection();
            String sql = "UPDATE TaiKhoan SET HoTen = ?, mk = ?, Email = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, hoten.getText());
            statement.setString(2, matkhau.getText());
            statement.setString(3, Email.getText());
            statement.setString(4, usernamee.getText());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User information updated successfully.");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}