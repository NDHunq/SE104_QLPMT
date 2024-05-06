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
import java.util.ResourceBundle;

public class SuaThuocPKBController implements Initializable {
    @FXML
    private MFXComboBox<DSThuoc> thuoc_combobox = new MFXComboBox<>();
    @FXML
    private MFXComboBox<CachDung> cachDung_combobox = new MFXComboBox<>();
    @FXML
    private MFXTextField soLuong_txtbox = new MFXTextField();
    @FXML
    private MFXComboBox<DonViThuoc> donVi_combobox = new MFXComboBox<>();
    @FXML
    private Button XongBtn = new Button();
    @FXML
    private Button HuyBtn = new Button();

    private ObjectProperty<Model.DSThuoc_PKB> rowDataProperties = new ObjectPropertyBase<DSThuoc_PKB>() {
        @Override
        public Object getBean() {
            return null;
        }

        @Override
        public String getName() {
            return "";
        }
    };

    public SuaThuocPKBController(DSThuoc_PKB rowData){
        rowDataProperties.set(rowData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HuyBtn.setOnAction((ActionEvent event) -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        XongBtn.setOnAction((ActionEvent event) -> {

        });

        updateData();
    }

    public void updateData(){
        DSThuoc_PKB rowData = rowDataProperties.get();
        String query = "SELECT * FROM Thuoc";
        String query2 = "SELECT * FROM CachDung";
        String query3 = "SELECT * FROM DonViThuoc";
        ObservableList<DSThuoc> thuoc_list = FXCollections.observableArrayList();
        ObservableList<CachDung> cachDung_list = FXCollections.observableArrayList();
        ObservableList<DonViThuoc> donVi_list = FXCollections.observableArrayList();

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                DSThuoc temp = new DSThuoc(rs.getString("Thuoc_ID"),rs.getString("TenThuoc"),rs.getDouble("GiaMua"),rs.getDouble("GiaBan"),rs.getLong("TonKho"),new CachDung(),new DonViThuoc());
                thuoc_list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        thuoc_combobox.setItems(thuoc_list);

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query2);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                CachDung temp = new CachDung(rs.getString("CachDung_ID"), rs.getString("TenCachDung"));
                cachDung_list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cachDung_combobox.setItems(cachDung_list);

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query3);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                DonViThuoc temp = new DonViThuoc(rs.getString("DVTHuoc_ID"),rs.getString("TenDVTHuoc"));
                donVi_list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        donVi_combobox.setItems(donVi_list);

        thuoc_combobox.setText(rowData.getThuoc().getTenThuoc());
        thuoc_combobox.setValue(rowData.getThuoc());

        try {
            String queery = "SELECT TenCachDung FROM Thuoc INNER JOIN CachDung ON Thuoc.CachDung_ID = CachDung.CachDung_ID WHERE Thuoc_ID = '"+rowData.getThuoc().getThuoc_ID()+"'";
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(queery);
            ResultSet rs = ps.executeQuery();
            String tencachdung = "";
            while(rs.next()){
                tencachdung = rs.getString("TenCachDung");
            }
            cachDung_combobox.setText(tencachdung);
            cachDung_combobox.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String queery = "SELECT TenDVTHuoc FROM Thuoc INNER JOIN DonViThuoc ON Thuoc.DonViThuoc_ID = DonViThuoc.DVTHuoc_ID WHERE Thuoc_ID = '"+rowData.getThuoc().getThuoc_ID()+"'";
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(queery);
            ResultSet rs = ps.executeQuery();
            String tendonvi = "";
            while(rs.next()){
                tendonvi = rs.getString("TenDVTHuoc");
            }
            donVi_combobox.setText(tendonvi);
            donVi_combobox.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        soLuong_txtbox.setText(String.valueOf(rowData.getSoLuong()));
    }

    public void thuoc_combobox_ChangedValue(){
        thuoc_combobox.setOnAction((ActionEvent event) -> {
            String selectedThuoc = thuoc_combobox.getSelectionModel().getSelectedItem().getTenThuoc();
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
                    DonViCBB.setValue(donVi);
                    CachDungCBB.setValue(cachDung);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
