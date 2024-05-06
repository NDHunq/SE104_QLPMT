package com.example.qlpmt;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class ThemThuocController {
    @FXML
    private MFXButton huy;
    public void initialize() {
        huy.setOnMouseClicked(event -> {
            huy.getScene().getWindow().hide();
        });
    }
}
