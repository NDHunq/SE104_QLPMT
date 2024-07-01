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
    private Validator validator = new Validator();

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
            if(validator.validate()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                ButtonType buttonTypeYes = new ButtonType("Có");
                ButtonType buttonTypeNo = new ButtonType("Không");
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                alert.setTitle("Xác nhận");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc chắn muốn cập nhật thông tin phiếu khám bệnh này không?");
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                InputStream iconStream = AppUtils.class.getResourceAsStream("/com/example/qlpmt/images/cong.png");
                Image image = new Image(iconStream);
                alertStage.getIcons().add(image);
                Window window = alert.getDialogPane().getScene().getWindow();
                window.setOnCloseRequest(e -> alert.close());
                ButtonType result = alert.showAndWait().orElse(buttonTypeNo);

                if(result == buttonTypeYes){
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
                }
                else
                {
                    alert.close();
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
        setupValidator();
        ToggleGroup group = new ToggleGroup();
        NamChBx.setToggleGroup(group);
        NuChBx.setToggleGroup(group);
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


            validator.createCheck()
                .withMethod(c -> {
                    if (NamSinhTxt.getText().isEmpty() || NamSinhTxt.getText() == null) {
                        NamSinhTxt.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error("Năm sinh không được để trống!");
                    }
                    else{
                        if(!NamSinhTxt.getText().matches("[0-9]+")){
                            NamSinhTxt.setStyle("-fx-border-color: red; -fx-text-fill: red");
                            c.error("Năm sinh không hợp lệ!");
                        }
                        else {
                            if(NamSinhTxt.getText().length() != 4){
                                NamSinhTxt.setStyle("-fx-border-color: red; -fx-text-fill: red");
                                c.error("Năm sinh không hợp lệ!");
                            }
                            else{
                                NamSinhTxt.setStyle("");
                            }

                        }
                    }
                })
                .dependsOn("cccd", NamSinhTxt.textProperty())
                .decorates(NamSinhTxt)
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
