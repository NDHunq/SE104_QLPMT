package com.example.qlpmt.KhamBenh;

import com.example.qlpmt.DBConnection;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddKhamBenhController implements Initializable {
    @FXML
    Button XongBtn;
    @FXML
    private Button HuyBtn;
    @FXML
    private MFXTextField CCCDTxt;
    @FXML
    private MFXDatePicker DOBPicker;
    @FXML
    private MFXTextField DiaChiTxt;
    @FXML
    private MFXTextField HoTenTxt;
    @FXML
    private MFXRadioButton NamChBx;
    @FXML
    private MFXRadioButton NuChBx;
    @FXML
    private MFXTextField SDTTxt;
    Connection connection = null;
    private PreparedStatement preparedStatement = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HuyBtn.setOnAction((ActionEvent event) -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        XongBtn.setOnAction((ActionEvent event) -> {
            String sql="INSERT INTO DSKB (DSKB_ID, STT, NgayKham, HoTen, CCCD, GioiTinh,NamSinh,DiaChi,CoPKB) VALUES (?,?,?,?,?,?,?,?,?)";
            try {

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "PKB018");
                preparedStatement.setInt(2, 6);
                java.sql.Date currentDate = java.sql.Date.valueOf(LocalDate.now());
                preparedStatement.setDate(3, currentDate);
                preparedStatement.setString(4, HoTenTxt.getText());
                preparedStatement.setString(5, CCCDTxt.getText());
                if (NamChBx.isSelected()) {
                    preparedStatement.setInt(6, 1);
                } else {
                    preparedStatement.setInt(6, 0);
                }
                LocalDate localDate = DOBPicker.getValue();
                int year = localDate.getYear();
                preparedStatement.setInt(7, year);
                preparedStatement.setString(8, DiaChiTxt.getText());
                preparedStatement.setInt(9, 0);
                int state=preparedStatement.executeUpdate();
                if(state>0){
                    System.out.println("Success");
                }else{
                    System.out.println("Failed");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });

        ToggleGroup group = new ToggleGroup();
        NamChBx.setToggleGroup(group);
        NuChBx.setToggleGroup(group);
        //connect to database
        try {
            connection = (Connection) DBConnection.getConnection();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }


    }
}
