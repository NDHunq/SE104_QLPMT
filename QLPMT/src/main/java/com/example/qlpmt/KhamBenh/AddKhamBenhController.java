package com.example.qlpmt.KhamBenh;

import com.example.qlpmt.AppUtils;
import com.example.qlpmt.DBConnection;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.synedra.validatorfx.Validator;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private Validator validator = new Validator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HuyBtn.setOnAction((ActionEvent event) -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        XongBtn.setOnAction((ActionEvent event) -> {
            if(validator.validate()){
                String sql="INSERT INTO DSKB (DSKB_ID, STT, NgayKham, HoTen, CCCD, GioiTinh,NamSinh,DiaChi,CoPKB) VALUES (?,?,?,?,?,?,?,?,?)";
                try {

                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, getNextDSKB_ID() );
                    preparedStatement.setInt(2, getNextSTT());
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
        setupValidator();
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
    private void setupValidator(){
        //Validator cho căn cước công dân
        validator.createCheck()
                .withMethod(c -> {
                    if (CCCDTxt.getText().isEmpty() || CCCDTxt.getText() == null) {
                        CCCDTxt.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error("Căn cước công dân không được để trống!");
                    }
                    else{
                        if(!CCCDTxt.getText().matches("[0-9]+")){
                            CCCDTxt.setStyle("-fx-border-color: red; -fx-text-fill: red");
                            c.error("Căn cước công dân không hợp lệ!");
                        }
                        else {
                            if(CCCDTxt.getText().length() != 12){
                                CCCDTxt.setStyle("-fx-border-color: red; -fx-text-fill: red");
                                c.error("Căn cước công dân không hợp lệ!");
                            }
                            else{
                                CCCDTxt.setStyle("");
                            }

                        }
                    }
                })
                .dependsOn("cccd", CCCDTxt.textProperty())
                .decorates(CCCDTxt)
                .immediate();

        //Validator cho họ tên
        validator.createCheck()
                .withMethod(c -> {
                    if (HoTenTxt.getText().isEmpty() || HoTenTxt.getText() == null) {
                        HoTenTxt.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error("Họ tên không được để trống!");
                    }
                    else{
                        HoTenTxt.setStyle("");
                    }
                })
                .dependsOn("hoTen", HoTenTxt.textProperty())
                .decorates(HoTenTxt)
                .immediate();

        //Validator cho triệu chứng
        validator.createCheck()
                .withMethod(c -> {
                    if (DiaChiTxt.getText().isEmpty() || DiaChiTxt.getText() == null) {
                        DiaChiTxt.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error("Địa chỉ không được để trống!");
                    }
                    else{
                        DiaChiTxt.setStyle("");
                    }
                })
                .dependsOn("trieuChung", DiaChiTxt.textProperty())
                .decorates(DiaChiTxt)
                .immediate();

        //Validator cho ngày khám
        validator.createCheck()
                .withMethod(c -> {
                    if (DOBPicker.getValue().isAfter(LocalDate.now())) {
                        DOBPicker.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        DOBPicker.lookup(".mfx-icon-wrapper .mfx-font-icon").setStyle("-mfx-color: red;");
                        DOBPicker.lookup(".mfx-icon-wrapper .mfx-ripple-generator").setStyle("-mfx-ripple-color: #FF6961;");
                        c.error("Ngày sinh không được lớn hơn ngày hiện tại!");
                    }
                    else{
                        DOBPicker.setStyle("");
                        DOBPicker.lookup(".mfx-icon-wrapper .mfx-font-icon").setStyle("-mfx-color: #2264D1;");
                        DOBPicker.lookup(".mfx-icon-wrapper .mfx-ripple-generator").setStyle("-mfx-ripple-color: #D4F2FF;");
                    }
                })
                .dependsOn("ngayKham", DOBPicker.valueProperty())
                .decorates(DOBPicker)
                .immediate();
    }

    public String getMaxDSKB_ID() {
        String sql = "SELECT MAX(DSKB_ID) AS MaxID FROM DSKB";
        String maxID = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                maxID = resultSet.getString("MaxID");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return maxID;
    }
    public String getNextDSKB_ID() {
        String maxID = getMaxDSKB_ID();
        String nextID = "KB" + String.format("%03d", Integer.parseInt(maxID.substring(2)) + 1);
        return nextID;
    }
    public int getNextSTT() {
        java.sql.Date currentDate = java.sql.Date.valueOf(LocalDate.now());
        String sql = "SELECT MAX(STT) AS MaxSTT FROM DSKB WHERE NgayKham = ?";
        int maxSTT = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, currentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                maxSTT = resultSet.getInt("MaxSTT");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return maxSTT + 1;
    }
}
