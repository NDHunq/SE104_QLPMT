package com.example.qlpmt.KhamBenh;

import com.example.qlpmt.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class KhamBenhItemController implements Initializable {
    double x,y=0;

    @FXML
    private Label cccd;

    @FXML
    private Label diachi;

    @FXML
    private Label gioitinh;

    @FXML
    private Label hoten;

    @FXML
    private Label namsinh;

    @FXML
    private ImageView pkb;

    @FXML
    private Label stt;
    @FXML
    private HBox hbox;
    private boolean HadPKB;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void SetData(KhamBenh kb, String backColor)
    {
            stt.setText(kb.getSTT());
            hoten.setText(kb.getHoTen());
            cccd.setText(kb.getCCCD());
            gioitinh.setText(kb.getGioiTinh());
            namsinh.setText(kb.getNamSinh());
            diachi.setText(kb.getDiaChi());
            hbox.setStyle(backColor);
            if(kb.getImgPkb().equals("/com/example/qlpmt/images/plus.png"))
            {
                HadPKB = false;
            }
            else
            {
                HadPKB = true;
            }
        InputStream is = getClass().getResourceAsStream(kb.getImgPkb());
        if (is != null) {
            Image img = new Image(is);
            pkb.setImage(img);
        } else {
            System.out.println("Image not found: /images/finding.png");
        }
    }
    public void PKB(MouseEvent eventt) throws IOException {
        FXMLLoader loader;
        if(HadPKB==false)
        {
            loader = new FXMLLoader(HelloApplication.class.getResource("add_phieukb.fxml"));
        }
        else {
            loader = new FXMLLoader(HelloApplication.class.getResource("view_phieukb.fxml"));
        }

        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        Scene scene = new Scene(root, 684, 499);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
