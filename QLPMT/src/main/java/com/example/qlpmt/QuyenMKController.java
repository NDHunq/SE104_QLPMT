package com.example.qlpmt;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class QuyenMKController  implements Initializable {
    @FXML
    private ImageView exit;
    @FXML
    private ImageView minimize;
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        exit.setOnMouseClicked(event -> {
            // Get the current stage
            Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
            // Close the stage
            stage.close();
        });


        minimize.setOnMouseClicked(event -> {
            Stage stage = (Stage)((ImageView)event.getSource()).getScene().getWindow();
            stage.setIconified(true);
        });
    }
}
