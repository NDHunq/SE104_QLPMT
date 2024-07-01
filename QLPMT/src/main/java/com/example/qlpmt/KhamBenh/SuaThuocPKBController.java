package com.example.qlpmt.KhamBenh;

import Model.*;
import Model.PhieuKhamBenh;
import com.example.qlpmt.AppUtils;
import com.example.qlpmt.DBConnection;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.synedra.validatorfx.Validator;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SuaThuocPKBController implements Initializable {
    private Validator validator = new Validator();
    private StringBinding problemsText;

    @FXML
    private MFXComboBox<DSThuoc> thuoc_combobox = new MFXComboBox<>();
    @FXML
    private MFXComboBox<CachDung> cachDung_combobox = new MFXComboBox<>();
    @FXML
    private MFXTextField soLuong_txtbox = new MFXTextField();
    @FXML
    private MFXComboBox<DonViThuoc> donVi_combobox = new MFXComboBox<>();
    @FXML
    private MFXButton XongBtn = new MFXButton();
    @FXML
    private MFXButton HuyBtn = new MFXButton();

    private ObservableList<DSThuoc> existedThuoc_list = FXCollections.observableArrayList();


    // Luu du lieu cua dong duoc truyen vao tu EditPhieuKBController
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

    // Hàm tìm kiếm thuốc_ID dựa vào tên thuốc
    public String findThuoc_ID(String string) {
        // Tìm kiếm đối tượng DonVi dựa trên tên đơn vị
        for (DSThuoc thuoc : thuoc_combobox.getItems()) {
            if (thuoc.getTenThuoc().equals(string)) {
                return thuoc.getThuoc_ID();
            }
        }
        return null;
    }

    public SuaThuocPKBController(DSThuoc_PKB rowData, ObservableList<DSThuoc> t){
        rowDataProperties.set(rowData);
        existedThuoc_list = t;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateData();
        setupValidator();

        //Goi ham khi thay doi gia tri cua thuoc_combobox
        thuoc_combobox_ChangedValue();


        HuyBtn.setOnAction((ActionEvent event) -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        XongBtn.setOnAction((ActionEvent event) -> {
            if(validator.validate()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                ButtonType buttonTypeYes = new ButtonType("Có");
                ButtonType buttonTypeNo = new ButtonType("Không");
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                alert.setTitle("Xác nhận");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc chắn muốn cập nhật thông tin thuốc này không?");
                Window window = alert.getDialogPane().getScene().getWindow();
                window.setOnCloseRequest(e -> alert.close());
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                InputStream iconStream = AppUtils.class.getResourceAsStream("/com/example/qlpmt/images/cong.png");
                Image image = new Image(iconStream);
                alertStage.getIcons().add(image);
                ButtonType result = alert.showAndWait().orElse(buttonTypeNo);
                String query = "UPDATE DSTHuoc_PKB SET Thuoc_ID = ?, SoLuong = ? WHERE PKB_ID = '"+rowDataProperties.get().getPhieuKhamBenh().getIdPKB()+"' AND Thuoc_ID = '"+rowDataProperties.get().getThuoc().getThuoc_ID()+"'";

                if(result == buttonTypeYes){
                    try{
                        Connection conn = DBConnection.getConnection();
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setString(1, findThuoc_ID(thuoc_combobox.getText()));
                        ps.setInt(2, Integer.parseInt(soLuong_txtbox.getText()));
                        ps.executeUpdate();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Đã có lỗi xảy ra, vui lòng kiểm tra lại thông tin!");
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                InputStream iconStream = AppUtils.class.getResourceAsStream("/com/example/qlpmt/images/cong.png");
                Image image = new Image(iconStream);
                alertStage.getIcons().add(image);
                ButtonType result = alert.showAndWait().orElse(ButtonType.CLOSE);
            }
        });


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

        // Loại bỏ các thuốc đã tồn tại trong phiếu khám bệnh
        ObservableList<DSThuoc> newthuoc_list = FXCollections.observableArrayList(thuoc_list);
        for (DSThuoc existedThuoc : existedThuoc_list) {
            newthuoc_list.removeIf(thuoc -> thuoc.getThuoc_ID().equals(existedThuoc.getThuoc_ID()));
        }

        if(newthuoc_list.isEmpty()){
            System.out.println("Khong con thuoc nao de them");
        }
        else{
            System.out.println("Con thuoc de them");
            thuoc_combobox.setItems(newthuoc_list);
        }

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

        //set value cho cachDung_combobox dua vao thuoc_ID
        try {
            String queery = "SELECT TenCachDung FROM Thuoc INNER JOIN CachDung ON Thuoc.CachDung_ID = CachDung.CachDung_ID WHERE Thuoc_ID = '"+rowDataProperties.get().getThuoc().getThuoc_ID()+"'";
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

        //set value cho donVi_combobox dua vao thuoc_ID
        try {
            String queery = "SELECT TenDVTHuoc FROM Thuoc INNER JOIN DonViThuoc ON Thuoc.DonViThuoc_ID = DonViThuoc.DVTHuoc_ID WHERE Thuoc_ID = '"+rowDataProperties.get().getThuoc().getThuoc_ID()+"'";
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

    //Ham xu ly su kien khi thay doi gia tri cua thuoc_combobox
    public void thuoc_combobox_ChangedValue(){
        DSThuoc_PKB rowData = rowDataProperties.get();
        thuoc_combobox.setOnAction((ActionEvent event) -> {
            String selectedThuoc = thuoc_combobox.getSelectionModel().getSelectedItem().getTenThuoc();
            try {
                Connection con = DBConnection.getConnection();
                // Fetch ID corresponding to the selected name
                String idThuoc = fetchId("Thuoc", "TenThuoc", "Thuoc_ID", selectedThuoc);

                // Fetch corresponding DonVi from database
                String sql = "SELECT Thuoc.DonViThuoc_ID, Thuoc.CachDung_ID, DonViThuoc.TenDVTHuoc, CachDung.TenCachDung FROM Thuoc JOIN DonViThuoc ON Thuoc.DonViThuoc_ID = DonViThuoc.DVThuoc_ID JOIN CachDung ON Thuoc.CachDung_ID = CachDung.CachDung_ID WHERE Thuoc.Thuoc_ID = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, idThuoc);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    DonViThuoc donVi = new DonViThuoc(rs.getString("DonViThuoc_ID"), rs.getString("TenDVTHuoc"));
                    CachDung cachDung = new CachDung(rs.getString("CachDung_ID"), rs.getString("TenCachDung"));
                    donVi_combobox.setValue(donVi);
                    cachDung_combobox.setValue(cachDung);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private String fetchId(String tableName, String columnName, String idColumnName, String name) {
        String id = "";
        try {
            Connection con = DBConnection.getConnection();
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

    private void setupValidator(){
        validator.createCheck()
                .withMethod(c -> {
                    if (!(soLuong_txtbox.getText().isEmpty() || soLuong_txtbox.getText() == null)) {
                        if(isInteger(soLuong_txtbox.getText())){
                            if(Integer.parseInt(soLuong_txtbox.getText()) <= 0){
                                soLuong_txtbox.setStyle("-fx-border-color: red; -fx-text-fill: red");
                                c.error("Số lượng không hợp lệ!");
                            }
                            else{
                                soLuong_txtbox.setStyle("");
                            }
                        }
                        else{
                            soLuong_txtbox.setStyle("-fx-border-color: red; -fx-text-fill: red");
                            c.error("Số lượng không hợp lệ!");
                        }
                    }
                    else{
                        soLuong_txtbox.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error("Số lượng không được để trống!");
                    }
                })
                .dependsOn("soLuong", soLuong_txtbox.textProperty())
                .decorates(soLuong_txtbox)
                .immediate();
    }

    private TextArea createProblemOutput() {
        TextArea problems = new TextArea();
        problems.setEditable(false);
        problems.setPrefHeight(80);
        problems.setBackground(Background.EMPTY);
        problems.setFocusTraversable(false);
        problemsText = Bindings.createStringBinding(this::getProblemText, validator.validationResultProperty());
        problems.textProperty().bind(problemsText);
        return problems;
    }

    private String getProblemText() {
        return validator.validationResultProperty().get().getMessages().stream()
                .map(msg -> msg.getSeverity().toString() + ": " + msg.getText())
                .collect(Collectors.joining("\n"));
    }

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
