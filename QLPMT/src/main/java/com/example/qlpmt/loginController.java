package com.example.qlpmt;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
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
    private PasswordField passwordField;
    private boolean isPasswordVisible = false;
    @FXML
    private TextField textField;
    @FXML
    private Button login;


    @FXML
    private Text  signin;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      exit.setOnMouseClicked(event -> {
          System.exit(0);
      });

        login.setOnAction(event -> {
            try {
                login.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(javafx.scene.paint.Color.valueOf("#134494"), new javafx.scene.layout.CornerRadii(5), new javafx.geometry.Insets(0))));
                // Load the new FXML file
                Parent root = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/hello-view.fxml"));


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
        });
        login.setCursor(Cursor.HAND);

      minimize.setOnMouseClicked(event -> {
          Stage stage = (Stage)((ImageView)event.getSource()).getScene().getWindow();
          stage.setIconified(true);
      });
      signin.setOnMouseClicked(event -> {
          try {
              signin.setCursor(Cursor.HAND);
              Parent root = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/signin.fxml"));
              Stage stage = (Stage) signin.getScene().getWindow();
              root.setOnMousePressed(event1 -> {
                  double x = event1.getSceneX();
                  double y = event1.getSceneY();
                  root.setOnMouseDragged(event2 -> {
                      stage.setX(event2.getScreenX() - x);
                      stage.setY(event2.getScreenY() - y);
                  });
              });
              Scene scene = new Scene(root);
              scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
              stage.setScene(scene);
          } catch (IOException e) {
              e.printStackTrace();
          }
      });
      textField.setVisible(false);
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
    }
}
