package com.example.qlpmt;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class doanh_thuController implements Initializable {

    private boolean isBangSoLieu = true;

    @FXML
    private MFXButton bangSoLieu;

    @FXML
    private MFXButton bieuDo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButton();

        bangSoLieu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!isBangSoLieu){
                    isBangSoLieu = !isBangSoLieu;
                    setButton();
                }
            }
        });

        bieuDo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(isBangSoLieu){
                    isBangSoLieu = !isBangSoLieu;
                    setButton();
                }
            }
        });

    }

    public void setButton() {
        if(isBangSoLieu){
            bangSoLieu.setStyle("-fx-background-color: #2264D1; -fx-text-fill: #FFFFFF");
            bieuDo.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2264d1");
        } else {
            bieuDo.setStyle("-fx-background-color: #2264D1; -fx-text-fill: #FFFFFF");
            bangSoLieu.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2264d1");
        }
    }
}
