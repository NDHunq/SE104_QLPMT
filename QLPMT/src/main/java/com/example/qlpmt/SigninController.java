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
}
