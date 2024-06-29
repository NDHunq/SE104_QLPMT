package com.example.qlpmt;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
public class HelloController implements Initializable {

    @FXML
    private AnchorPane Pane1;
    @FXML
    private AnchorPane drawerPane;
    @FXML
    private Label drawerImage;
    @FXML
    private ImageView exit;
    @FXML
    private AnchorPane menu;
    @FXML
    private  ImageView minimize;
    @FXML
    private BorderPane bd1;
    @FXML
    private BorderPane bd2;
    @FXML
    private BorderPane bd3;
    @FXML
    private BorderPane bd4;
    @FXML
    private BorderPane bd5;
    @FXML
    private BorderPane bd6;
    @FXML
    private BorderPane bd7;
    @FXML
    private BorderPane txt1;
    @FXML
    private BorderPane txt2;
    @FXML
    private BorderPane txt3;
    @FXML
    private BorderPane txt4;
    @FXML
    private BorderPane txt5;
    @FXML
    private BorderPane txt6;
    @FXML
    private BorderPane txt7;
    @FXML
    private  BorderPane mainview;
    @FXML
    private ImageView logout;
    @FXML
    private BorderPane lotxt;





    // Add a boolean variable to track the state of the drawerPane
    private boolean isDrawerOpen = false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

//load khambenh.fxml
        Parent content = null;
        try {
            content = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/kham_benh.fxml"));
        } catch (IOException e) {
            System.exit(0);
        }


        handleBorderPaneClick(bd1);
        bd1.setCursor(javafx.scene.Cursor.HAND);
        bd2.setCursor(javafx.scene.Cursor.HAND);
        bd3.setCursor(javafx.scene.Cursor.HAND);
        bd4.setCursor(javafx.scene.Cursor.HAND);
        bd5.setCursor(javafx.scene.Cursor.HAND);
        bd6.setCursor(javafx.scene.Cursor.HAND);
        bd7.setCursor(javafx.scene.Cursor.HAND);
        txt1.setCursor(javafx.scene.Cursor.HAND);
        txt2.setCursor(javafx.scene.Cursor.HAND);
        txt3.setCursor(javafx.scene.Cursor.HAND);
        txt4.setCursor(javafx.scene.Cursor.HAND);
        txt5.setCursor(javafx.scene.Cursor.HAND);
        txt6.setCursor(javafx.scene.Cursor.HAND);
        txt7.setCursor(javafx.scene.Cursor.HAND);
        logout.setCursor(javafx.scene.Cursor.HAND);
    lotxt.setCursor(javafx.scene.Cursor.HAND);
        menu.setCursor(javafx.scene.Cursor.HAND);

        logout.setOnMouseClicked(event -> {
            try {

                Parent root = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/login.fxml"));

                // Get the current stage
                Stage stage = (Stage) logout.getScene().getWindow();
                root.setOnMousePressed(event1 -> {
                    double x = event1.getSceneX();
                    double y = event1.getSceneY();
                    root.setOnMouseDragged(event2 -> {
                        stage.setX(event2.getScreenX() - x);
                        stage.setY(event2.getScreenY() - y);
                    });
                });

                // Create a new scene and set it on the stage
                // Create a new scene and set it on the stage
                Scene scene = new Scene(root);
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lotxt.setOnMouseClicked(event -> {
            try {

                Parent root = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/login.fxml"));

                // Get the current stage
                Stage stage = (Stage) logout.getScene().getWindow();


                // Create a new scene and set it on the stage
                Scene scene = new Scene(root);
                stage.initStyle(StageStyle.UNDECORATED);
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Set the content to the mainView
        mainview.setCenter(content);

        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        minimize.setOnMouseClicked(event -> {
            Stage stage = (Stage) minimize.getScene().getWindow();
            stage.setIconified(true);
        });
        Pane1.setVisible(false);
        drawerPane.setVisible(false);



        bd1.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd1);
            // Load the content from kham_benh.fxml
            Parent khamBenhContent = null;
            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/kham_benh.fxml"));
            } catch (IOException e) {
                System.exit(0);
            }

            // Set the content to the mainView
            mainview.setCenter(khamBenhContent);
        });
        mainview.setOnMouseClicked(event->{
            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),Pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                Pane1.setVisible(false);
            });

            // Create TranslateTransition for drawerPane
            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),drawerPane);
            translateTransition1.setToX(-600); // Move back to initial position
            translateTransition1.play();
            isDrawerOpen = false;
        });


        txt1.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd1);
            // Load the content from kham_benh.fxml
            Parent khamBenhContent = null;
            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/kham_benh.fxml"));
            } catch (IOException e) {
                System.exit(0);
            }
            txtclick(txt1);

            // Set the content to the mainView
            mainview.setCenter(khamBenhContent);
        });

        bd2.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd2);
            Parent khamBenhContent = null;
            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/benh_nhan.fxml"));
            } catch (IOException e) {
                System.exit(0);
            }
            mainview.setCenter(khamBenhContent);

        });
        txt2.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd2);
            // Load the content from kham_benh.fxml
            Parent khamBenhContent = null;
            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/benh_nhan.fxml"));
            } catch (IOException e) {
                System.exit(0);
            }
            txtclick(txt2);

            // Set the content to the mainView
            mainview.setCenter(khamBenhContent);
        });
        bd3.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd3);
            Parent khamBenhContent = null;
            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/doanh_thu.fxml"));
            } catch (IOException e) {
                System.exit(0);
            }
            mainview.setCenter(khamBenhContent);

        });
        txt3.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd3);
            // Load the content from kham_benh.fxml
            Parent khamBenhContent = null;
            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/doanh_thu.fxml"));
            } catch (IOException e) {
                System.exit(0);
            }
            txtclick(txt3);

            // Set the content to the mainView
            mainview.setCenter(khamBenhContent);
        });
        bd4.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd4);
            Parent khamBenhContent = null;
            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/thuoc.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainview.setCenter(khamBenhContent);

        });
        txt4.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd4);
            // Load the content from kham_benh.fxml
            Parent khamBenhContent = null;
            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/thuoc.fxml"));
            } catch (IOException e) {
                System.exit(0);
            }
            txtclick(txt4);

            // Set the content to the mainView
            mainview.setCenter(khamBenhContent);
        });
        bd5.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd5);
            Parent khamBenhContent = null;
            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/kho_thuoc.fxml"));
            } catch (IOException e) {
                System.exit(0);
            }
            mainview.setCenter(khamBenhContent);

        });
        txt5.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd5);
            // Load the content from kham_benh.fxml
            Parent khamBenhContent = null;
            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/kho_thuoc.fxml"));
            } catch (IOException e) {
                System.exit(0);
            }
            txtclick(txt5);

            // Set the content to the mainView
            mainview.setCenter(khamBenhContent);
        });
        bd6.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd6);
            Parent khamBenhContent = null;
            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/cai_dat.fxml"));
            } catch (IOException e) {
                System.exit(0);
            }
            mainview.setCenter(khamBenhContent);

        });
        txt6.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd6);
            // Load the content from kham_benh.fxml
            Parent khamBenhContent = null;
            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/cai_dat.fxml"));
            } catch (IOException e) {
                System.exit(0);
            }
            txtclick(txt6);

            // Set the content to the mainView
            mainview.setCenter(khamBenhContent);
        });
        bd7.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd7);
            Parent khamBenhContent = null;

            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/nhanvien.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            mainview.setCenter(khamBenhContent);

        });
        txt7.setOnMouseClicked(event -> {
            handleBorderPaneClick(bd7);
            // Load the content from kham_benh.fxml
            Parent khamBenhContent = null;
            try {
                khamBenhContent = FXMLLoader.load(getClass().getResource("/com/example/qlpmt/nhanvien.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            txtclick(txt7);

            // Set the content to the mainView
            mainview.setCenter(khamBenhContent);
        });
        // Add event handlers for each BorderPane

        // Add event handlers for each BorderPane

        // Set initial position of drawerPane
        drawerPane.setTranslateX(-600);

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(1),Pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        menu.setOnMouseClicked(event -> {
            Pane1.setVisible(true);

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(1),Pane1);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            // Check the state of the drawerPane
            if (isDrawerOpen) {
                Pane1.setVisible(false);
                // If the drawerPane is open, close it
                TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),drawerPane);
                translateTransition1.setToX(-600); // Move back to initial position
                translateTransition1.play();
                isDrawerOpen = false;
            } else {
                // If the drawerPane is closed, open it
                drawerPane.setVisible(true);

                TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),drawerPane);
                translateTransition1.setToX(0); // Move to original position
                translateTransition1.play();
                isDrawerOpen = true;
            }
        });

        Pane1.setOnMouseClicked(event -> {
            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),Pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                Pane1.setVisible(false);
            });

            // Create TranslateTransition for drawerPane
            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),drawerPane);
            translateTransition1.setToX(-600); // Move back to initial position
            translateTransition1.play();
            isDrawerOpen = false;
        });
    }

    private void handleBorderPaneClick(BorderPane clickedBorderPane) {
        // Remove the style from all BorderPanes
        bd1.getStyleClass().remove("square-border-pane");
        bd2.getStyleClass().remove("square-border-pane");
        bd3.getStyleClass().remove("square-border-pane");
        bd4.getStyleClass().remove("square-border-pane");
        bd5.getStyleClass().remove("square-border-pane");
        bd6.getStyleClass().remove("square-border-pane");
        bd7.getStyleClass().remove("square-border-pane");

        if (isDrawerOpen) {
            Pane1.setVisible(false);
            // If the drawerPane is open, close it
            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
            translateTransition1.setToX(-600); // Move back to initial position
            translateTransition1.play();
            isDrawerOpen = false;
        }

        // Add the style to the clicked BorderPane
        clickedBorderPane.getStyleClass().add("square-border-pane");
    }

    private void txtclick(BorderPane clickedBorderPane) {

        if(clickedBorderPane == txt1){
            bd1.getStyleClass().remove("square-border-pane");
            bd2.getStyleClass().remove("square-border-pane");
            bd3.getStyleClass().remove("square-border-pane");
            bd4.getStyleClass().remove("square-border-pane");
            bd5.getStyleClass().remove("square-border-pane");
            bd6.getStyleClass().remove("square-border-pane");
            bd7.getStyleClass().remove("square-border-pane");

            if (isDrawerOpen) {
                Pane1.setVisible(false);
                // If the drawerPane is open, close it
                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
                translateTransition1.setToX(-600); // Move back to initial position
                translateTransition1.play();
                isDrawerOpen = false;
            }

            // Add the style to the clicked BorderPane
            bd1.getStyleClass().add("square-border-pane");
        }
        if(clickedBorderPane==txt2)
        {
            bd1.getStyleClass().remove("square-border-pane");
            bd2.getStyleClass().remove("square-border-pane");
            bd3.getStyleClass().remove("square-border-pane");
            bd4.getStyleClass().remove("square-border-pane");
            bd5.getStyleClass().remove("square-border-pane");
            bd6.getStyleClass().remove("square-border-pane");
            bd7.getStyleClass().remove("square-border-pane");

            if (isDrawerOpen) {
                Pane1.setVisible(false);
                // If the drawerPane is open, close it
                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
                translateTransition1.setToX(-600); // Move back to initial position
                translateTransition1.play();
                isDrawerOpen = false;
            }

            // Add the style to the clicked BorderPane
            bd2.getStyleClass().add("square-border-pane");
        }
        if(clickedBorderPane==txt3)
        {
            bd1.getStyleClass().remove("square-border-pane");
            bd2.getStyleClass().remove("square-border-pane");
            bd3.getStyleClass().remove("square-border-pane");
            bd4.getStyleClass().remove("square-border-pane");
            bd5.getStyleClass().remove("square-border-pane");
            bd6.getStyleClass().remove("square-border-pane");
            bd7.getStyleClass().remove("square-border-pane");

            if (isDrawerOpen) {
                Pane1.setVisible(false);
                // If the drawerPane is open, close it
                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
                translateTransition1.setToX(-600); // Move back to initial position
                translateTransition1.play();
                isDrawerOpen = false;
            }

            // Add the style to the clicked BorderPane
            bd3.getStyleClass().add("square-border-pane");
        }
        if(clickedBorderPane==txt4)
        {
            bd1.getStyleClass().remove("square-border-pane");
            bd2.getStyleClass().remove("square-border-pane");
            bd3.getStyleClass().remove("square-border-pane");
            bd4.getStyleClass().remove("square-border-pane");
            bd5.getStyleClass().remove("square-border-pane");
            bd6.getStyleClass().remove("square-border-pane");
            bd7.getStyleClass().remove("square-border-pane");

            if (isDrawerOpen) {
                Pane1.setVisible(false);
                // If the drawerPane is open, close it
                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
                translateTransition1.setToX(-600); // Move back to initial position
                translateTransition1.play();
                isDrawerOpen = false;
            }

            // Add the style to the clicked BorderPane
            bd4.getStyleClass().add("square-border-pane");
        }
        if(clickedBorderPane==txt5)
        {
            bd1.getStyleClass().remove("square-border-pane");
            bd2.getStyleClass().remove("square-border-pane");
            bd3.getStyleClass().remove("square-border-pane");
            bd4.getStyleClass().remove("square-border-pane");
            bd5.getStyleClass().remove("square-border-pane");
            bd6.getStyleClass().remove("square-border-pane");
            bd7.getStyleClass().remove("square-border-pane");

            if (isDrawerOpen) {
                Pane1.setVisible(false);
                // If the drawerPane is open, close it
                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
                translateTransition1.setToX(-600); // Move back to initial position
                translateTransition1.play();
                isDrawerOpen = false;
            }

            // Add the style to the clicked BorderPane
            bd5.getStyleClass().add("square-border-pane");
        }
        if(clickedBorderPane==txt6)
        {
            bd1.getStyleClass().remove("square-border-pane");
            bd2.getStyleClass().remove("square-border-pane");
            bd3.getStyleClass().remove("square-border-pane");
            bd4.getStyleClass().remove("square-border-pane");
            bd5.getStyleClass().remove("square-border-pane");
            bd6.getStyleClass().remove("square-border-pane");
            bd7.getStyleClass().remove("square-border-pane");

            if (isDrawerOpen) {
                Pane1.setVisible(false);
                // If the drawerPane is open, close it
                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
                translateTransition1.setToX(-600); // Move back to initial position
                translateTransition1.play();
                isDrawerOpen = false;
            }

            // Add the style to the clicked BorderPane
            bd6.getStyleClass().add("square-border-pane");
        }
        if(clickedBorderPane==txt7)
        {
            bd1.getStyleClass().remove("square-border-pane");
            bd2.getStyleClass().remove("square-border-pane");
            bd3.getStyleClass().remove("square-border-pane");
            bd4.getStyleClass().remove("square-border-pane");
            bd5.getStyleClass().remove("square-border-pane");
            bd6.getStyleClass().remove("square-border-pane");
            bd7.getStyleClass().remove("square-border-pane");

            if (isDrawerOpen) {
                Pane1.setVisible(false);
                // If the drawerPane is open, close it
                TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
                translateTransition1.setToX(-600); // Move back to initial position
                translateTransition1.play();
                isDrawerOpen = false;
            }

            // Add the style to the clicked BorderPane
            bd7.getStyleClass().add("square-border-pane");
        }
    }


}