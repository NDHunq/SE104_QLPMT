package com.example.qlpmt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class HelloApplication extends Application {
    double x,y=0;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root = loader.load();
        StackPane stackPane = new StackPane();


        stage.initStyle(StageStyle.TRANSPARENT);

        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        Scene scene = new Scene(root, 370, 488
        );


        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);

        scene.setFill(Color.TRANSPARENT);
        stage.centerOnScreen();

        AppUtils.setIcon(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
