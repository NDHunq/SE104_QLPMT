package com.example.qlpmt.KhamBenh;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewPhieuKBController implements Initializable {
    @FXML
    private VBox pkb_layout;

    @FXML
    private MFXButton HoaDon;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HoaDon.setOnAction((ActionEvent event) -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        List<PhieuKhamBenh> ls=new ArrayList<>(pkb());
        for (int i=0;i<ls.size();i++){
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("phieu_kb_item.fxml"));
            try {

                HBox hBox=loader.load();
                PhieuKhamBenhItemController item=loader.getController();
                item.SetData(ls.get(i));
                pkb_layout.getChildren().add(hBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public List<PhieuKhamBenh> pkb(){
        List<PhieuKhamBenh> ls=new ArrayList<>();
        ls.add(new PhieuKhamBenh("001","Paracetamol","Sáng: 1 viên, chiều: 2 viên","viên","20"));
        ls.add(new PhieuKhamBenh("002","Paracetamol","Sáng: 1 viên, chiều: 2 viên","viên","20"));
        ls.add(new PhieuKhamBenh("003","Paracetamol","Sáng: 1 viên, chiều: 2 viên","viên","20"));
        ls.add(new PhieuKhamBenh("004","Paracetamol","Sáng: 1 viên, chiều: 2 viên","viên","20"));
        ls.add(new PhieuKhamBenh("005","Paracetamol","Sáng: 1 viên, chiều: 2 viên","viên","20"));
        ls.add(new PhieuKhamBenh("006","Paracetamol","Sáng: 1 viên, chiều: 2 viên","viên","20"));
        ls.add(new PhieuKhamBenh("007","Paracetamol","Sáng: 1 viên, chiều: 2 viên","viên","20"));
        ls.add(new PhieuKhamBenh("008","Paracetamol","Sáng: 1 viên, chiều: 2 viên","viên","20"));
        ls.add(new PhieuKhamBenh("009","Paracetamol","Sáng: 1 viên, chiều: 2 viên","viên","20"));
        ls.add(new PhieuKhamBenh("010","Paracetamol","Sáng: 1 viên, chiều: 2 viên","viên","20"));
        ls.add(new PhieuKhamBenh("011","Paracetamol","Sáng: 1 viên, chiều: 2 viên","viên","20"));
        return ls;
    }

}
