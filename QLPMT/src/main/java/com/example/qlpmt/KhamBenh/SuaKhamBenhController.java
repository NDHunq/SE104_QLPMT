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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SuaKhamBenhController implements Initializable {
    @FXML
    Button XongBtn;
    @FXML
    private Button HuyBtn;
    @FXML
    private MFXTextField CCCDTxt;

    @FXML
    private MFXTextField NamSinhTxt;
    @FXML
    private MFXTextField DiaChiTxt;
    @FXML
    private MFXTextField HoTenTxt;
    @FXML
    private MFXRadioButton NamChBx;
    @FXML
    private MFXRadioButton NuChBx;
    Connection connection = null;
    private PreparedStatement preparedStatement = null;
    String DSKB_id;
    KhamBenh kb;
    kham_benhController khamBenhController=new kham_benhController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connection = (Connection) DBConnection.getConnection();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        HuyBtn.setOnAction((ActionEvent event) -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        XongBtn.setOnAction((ActionEvent event) -> {
            // Get the values from the text fields and radio buttons
            String hoTen = HoTenTxt.getText();
            String cccd = CCCDTxt.getText();
            String diaChi = DiaChiTxt.getText();
            int namSinh = Integer.parseInt(NamSinhTxt.getText());
            int gioiTinh = NamChBx.isSelected() ? 1 : 0;

            // Create an SQL update statement
            String sql = "UPDATE DSKB SET HoTen = ?, CCCD = ?, DiaChi = ?, NamSinh = ?, GioiTinh = ? WHERE DSKB_id = ?";

            try {
                // Prepare the statement with the connection object
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                // Set the parameters in the prepared statement
                preparedStatement.setString(1, hoTen);
                preparedStatement.setString(2, cccd);
                preparedStatement.setString(3, diaChi);
                preparedStatement.setInt(4, namSinh);
                preparedStatement.setInt(5, gioiTinh);
                preparedStatement.setString(6, DSKB_id);

                // Execute the update operation
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
            // Close the stage
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            khamBenhController.refreshPage();
            stage.close();
        });

        ToggleGroup group = new ToggleGroup();
        NamChBx.setToggleGroup(group);
        NuChBx.setToggleGroup(group);
    }
    public void InitData(String DSKB_id, KhamBenh kb, kham_benhController khamBenhController)
    {
        this.DSKB_id = DSKB_id;
        this.kb = kb;
        this.khamBenhController = khamBenhController;
        try {
            String sql = "SELECT * FROM DSKB WHERE DSKB_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, DSKB_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                HoTenTxt.setText(rs.getString("HoTen"));
                CCCDTxt.setText(rs.getString("CCCD"));
                DiaChiTxt.setText(rs.getString("DiaChi"));
                NamSinhTxt.setText(String.valueOf(rs.getInt("NamSinh")));
                if(rs.getInt("GioiTinh")==1)
                {
                    NamChBx.setSelected(true);
                }
                else
                {
                    NuChBx.setSelected(true);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

}
