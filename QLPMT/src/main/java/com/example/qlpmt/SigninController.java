package com.example.qlpmt;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
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
    private PasswordField passwordField;
    private boolean isPasswordVisible = false;
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField1;
    private boolean isPasswordVisible1 = false;
    @FXML
    private TextField textField1;



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
        see.setOnMouseClicked(event -> {
            if (!isPasswordVisible) {
                see.setImage(new Image(getClass().getResource("/com/example/qlpmt/images/see.png").toExternalForm()));
                textField.setText(passwordField.getText());
                textField.setVisible(true);
                passwordField.setVisible(false);
                isPasswordVisible = true;
            } else {
                see.setImage(new Image(getClass().getResource("/com/example/qlpmt/images/eye.png").toExternalForm()));
                passwordField.setText(textField.getText());
                passwordField.setVisible(true);
                textField.setVisible(false);
                isPasswordVisible = false;
            }
        });
        see1.setOnMouseClicked(event -> {
            if (!isPasswordVisible1) {
                see1.setImage(new Image(getClass().getResource("/com/example/qlpmt/images/see.png").toExternalForm()));
                textField1.setText(passwordField1.getText());
                textField1.setVisible(true);
                passwordField1.setVisible(false);
                isPasswordVisible1 = true;
            } else {
                see1.setImage(new Image(getClass().getResource("/com/example/qlpmt/images/eye.png").toExternalForm()));
                passwordField1.setText(textField1.getText());
                passwordField1.setVisible(true);
                textField1.setVisible(false);
                isPasswordVisible1 = false;
            }
        });
    }
}
