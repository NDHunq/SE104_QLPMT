package com.example.qlpmt.KhamBenh;

import Model.*;
import Model.PhieuKhamBenh;
import com.example.qlpmt.DBConnection;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SuaThuocPKBController2 implements Initializable {
    @FXML
    private MFXComboBox<String> thuoc_combobox = new MFXComboBox<>();
    @FXML
    private MFXComboBox<String> cachDung_combobox = new MFXComboBox<>();
    @FXML
    private MFXTextField soLuong_txtbox = new MFXTextField();
    @FXML
    private MFXComboBox<String> donVi_combobox = new MFXComboBox<>();
    @FXML
    private Button XongBtn = new Button();
    @FXML
    private Button HuyBtn = new Button();
    Connection con=null;
    String PKB_id;
    ThuocPKB rowData;
    private AddPhieuKBController addPhieuKBController = new AddPhieuKBController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HuyBtn.setOnAction((ActionEvent event) -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        XongBtn.setOnAction((ActionEvent event) -> {
            String selectedThuoc = thuoc_combobox.getText();
            String selectedDonVi = donVi_combobox.getSelectionModel().getSelectedItem();
            String selectedCachDung = cachDung_combobox.getSelectionModel().getSelectedItem();
            String enteredSoLuong = soLuong_txtbox.getText();

            try {
                Statement stmt = con.createStatement();

                // Fetch IDs corresponding to the selected names
                String idThuoc = fetchId("Thuoc", "TenThuoc", "Thuoc_ID", selectedThuoc);
                String idDonVi = fetchId("DonViThuoc", "TenDVThuoc", "DVThuoc_ID", selectedDonVi);
                String idCachDung = fetchId("CachDung", "TenCachDung", "CachDung_ID", selectedCachDung);

                String sql = "UPDATE DSTHuoc_PKB SET Thuoc_ID = '" + idThuoc + "', SoLuong = '" + enteredSoLuong + "' WHERE PKB_ID = '" + PKB_id + "'";
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
        thuoc_combobox.getItems().addAll(fetchData("Thuoc", "TenThuoc"));
        donVi_combobox.getItems().addAll(fetchData("donvithuoc", "TenDVThuoc"));
        cachDung_combobox.getItems().addAll(fetchData("cachdung", "TencachDung"));
        thuoc_combobox.setOnAction((ActionEvent event) -> {
            String selectedThuoc = thuoc_combobox.getSelectionModel().getSelectedItem();
            try {
                // Fetch ID corresponding to the selected name
                String idThuoc = fetchId("Thuoc", "TenThuoc", "Thuoc_ID", selectedThuoc);

                // Fetch corresponding DonVi from database
                String sql = "SELECT DonViThuoc.TenDVThuoc, CachDung.TenCachDung FROM Thuoc JOIN DonViThuoc ON Thuoc.DonViThuoc_ID = DonViThuoc.DVThuoc_ID JOIN CachDung ON Thuoc.CachDung_ID = CachDung.CachDung_ID WHERE Thuoc.Thuoc_ID = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, idThuoc);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    String donVi = rs.getString("TenDVThuoc");
                    String cachDung = rs.getString("TenCachDung");
                    donVi_combobox.setValue(donVi);
                    cachDung_combobox.setValue(cachDung);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // Set the values for the ComboBoxes and TextField
        thuoc_combobox.setText(rowData.getTenThuoc());
        donVi_combobox.setText(rowData.getDonVi());
        soLuong_txtbox.setText(String.valueOf(rowData.getSoLuong()));
        cachDung_combobox.setText(rowData.getCachDung());
        donVi_combobox.setDisable(true);
        cachDung_combobox.setDisable(true);


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
    public void InitData(String id, ThuocPKB rowData) {
        System.out.println(rowData.getTenThuoc()+" "+rowData.getDonVi()+" "+rowData.getSoLuong()+" "+rowData.getCachDung()  );
        PKB_id = id;
        this.rowData = rowData;
    }
    public void setAddPhieuKBController(AddPhieuKBController controller) {
        this.addPhieuKBController = controller;
    }
}
