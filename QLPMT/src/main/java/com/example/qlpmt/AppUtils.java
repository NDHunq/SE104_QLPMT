package com.example.qlpmt;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

public class AppUtils {

    public static void setIcon(Stage stage) {
        InputStream iconStream = AppUtils.class.getResourceAsStream("/com/example/qlpmt/images/cong.png");
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
    }
}