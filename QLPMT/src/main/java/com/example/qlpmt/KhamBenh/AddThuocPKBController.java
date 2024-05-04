package com.example.qlpmt.KhamBenh;

import com.example.qlpmt.DBConnection;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddThuocPKBController implements Initializable {
    @FXML
    private MFXComboBox<String> CachDungCBB;

    @FXML
    private MFXComboBox<String> DonViCBB;

    @FXML
    private Button HuyBtn;

    @FXML
    private MFXTextField SoLuongTXT;

    @FXML
    private MFXComboBox<String> ThuocCBB;

    @FXML
    private MFXButton XongBtn;
    Connection con=null;
    String PKB_id;
    private AddPhieuKBController addPhieuKBController = new AddPhieuKBController();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HuyBtn.setOnAction((ActionEvent event) -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        XongBtn.setOnAction((ActionEvent event) -> {
            String selectedThuoc = ThuocCBB.getSelectionModel().getSelectedItem();
            String selectedDonVi = DonViCBB.getSelectionModel().getSelectedItem();
            String selectedCachDung = CachDungCBB.getSelectionModel().getSelectedItem();
            String enteredSoLuong = SoLuongTXT.getText();

            try {
                Statement stmt = con.createStatement();

                // Fetch IDs corresponding to the selected names
                String idThuoc = fetchId("Thuoc", "TenThuoc", "Thuoc_ID", selectedThuoc);
                String idDonVi = fetchId("DonViThuoc", "TenDVThuoc", "DVThuoc_ID", selectedDonVi);
                String idCachDung = fetchId("CachDung", "TenCachDung", "CachDung_ID", selectedCachDung);

                String sql = "INSERT INTO DSTHuoc_PKB (PKB_ID, Thuoc_ID, SoLuong) VALUES ('" + PKB_id + "', '" + idThuoc + "', '" + enteredSoLuong + "')";
                stmt.executeUpdate(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            addPhieuKBController.refreshPage();
        });
        con= DBConnection.getConnection();

        // Populate ComboBoxes
        ThuocCBB.getItems().addAll(fetchData("Thuoc", "TenThuoc"));
        DonViCBB.getItems().addAll(fetchData("donvithuoc", "TenDVThuoc"));
        CachDungCBB.getItems().addAll(fetchData("cachdung", "TencachDung"));
    }
    public void setAddPhieuKBController(AddPhieuKBController controller) {
        this.addPhieuKBController = controller;
    }
    private List<String> fetchData(String tableName, String columnName) {
        List<String> data = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT " + columnName + " FROM " + tableName);
            while (rs.next()) {
                data.add(rs.getString(columnName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    private String fetchId(String tableName, String columnName, String idColumnName, String name) {
        String id = "";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT " + idColumnName + " FROM " + tableName + " WHERE " + columnName + " = '" + name + "'");
            if (rs.next()) {
                id = rs.getString(idColumnName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
    public void InitData(String id) {
        PKB_id = id;
    }
}
