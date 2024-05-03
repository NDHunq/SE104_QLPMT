package com.example.qlpmt;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Sua_ThuocController {
    @FXML
    public TextField text_tenthuoc;//
    public TextField text_donvi;
    public  TextField text_soluong;
    public TextField text_dongia;
    @FXML
    private MFXButton huy;
    @FXML
    private MFXButton suathuoc;
    public void initialize() {
        huy.setOnMouseClicked(event -> {
            huy.getScene().getWindow().hide();
        });
        suathuoc.setOnMouseClicked(event -> {

        });
    }
}
