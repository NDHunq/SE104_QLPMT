package com.example.qlpmt.KhamBenh;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PhieuKhamBenhItemController implements Initializable {
    @FXML
    private Label cachdung;

    @FXML
    private Label dvi;

    @FXML
    private Label sl;

    @FXML
    private Label stt;

    @FXML
    private Label thuoc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void SetData(PhieuKhamBenh pkb)
    {
        stt.setText(pkb.getStt());
        thuoc.setText(pkb.getThuoc());
        cachdung.setText(pkb.getCachdung());
        sl.setText(pkb.getSl());
        dvi.setText(pkb.getDvi());
    }
}
