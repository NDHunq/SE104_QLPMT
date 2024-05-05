package com.example.qlpmt.KhamBenh;

import Model.LoaiBenh;
import Model.PhieuKhamBenh;
import Model.TaiKhoanNP;
import com.example.qlpmt.DBConnection;
import com.example.qlpmt.HelloApplication;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class EditPhieuKBController implements Initializable {
    @FXML
    private AnchorPane edit_pkb_root_node = new AnchorPane();

    @FXML
    private VBox vbox_layout_left = new VBox();

    private double x,y=0;
    @FXML
    private MFXButton HuyBtn = new MFXButton();

    @FXML
    private MFXButton XongBtn = new MFXButton();

    @FXML
    private ImageView close = new ImageView();

    @FXML
    private MFXTableView<ThuocPKB> table_thuoc = new MFXTableView<>();
    private ObservableList<ThuocPKB> list;

    @FXML
    private MFXTextField cccd_txtbox = new MFXTextField();

    @FXML
    private MFXTextField trieuChung_txtbox = new MFXTextField();

    @FXML
    private MFXTextField hoTen_txtbox = new MFXTextField();

    @FXML
    private MFXDatePicker ngayKham_datepicker = new MFXDatePicker();

    @FXML
    private MFXComboBox<LoaiBenh> loaiBenh_combobox = new MFXComboBox<>();

    @FXML
    private MFXComboBox<TaiKhoanNP> nguoiKham_combobox = new MFXComboBox<>();

    private ObjectProperty<PhieuKhamBenh> rowDataProperties = new ObjectPropertyBase<PhieuKhamBenh>() {
        @Override
        public Object getBean() {
            return null;
        }

        @Override
        public String getName() {
            return "";
        }
    };

    public EditPhieuKBController(PhieuKhamBenh rowData){
        rowDataProperties.set(rowData);
    }

    public String findLoaiBenh_ID(String string) {
        // Tìm kiếm đối tượng LoaiBenh dựa trên tên bệnh
        for (LoaiBenh loaiBenh : loaiBenh_combobox.getItems()) {
            if (loaiBenh.getTenBenh().equals(string)) {
                return loaiBenh.getLoaiBenh_ID();
            }
        }
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Tạo một Pane tạm thời
        Pane tempPane = new Pane();

        // Thêm Pane tạm thời vào Scene
        vbox_layout_left.getChildren().add(tempPane);

        // Yêu cầu focus vào Pane tạm thời
        Platform.runLater(() -> tempPane.requestFocus());

        close.setOnMouseClicked(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        HuyBtn.setOnAction((ActionEvent event) -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        XongBtn.setOnAction((ActionEvent event) -> {
            String query = "UPDATE PKB SET TrieuChung = ?, LoaiBenh_ID = ? WHERE PKB_ID = ?";
            String query2 = "UPDATE DSKB SET CCCD = ?, HoTen = ?, NgayKham = ? WHERE DSKB_ID = ?";
            try {
                Connection conn = DBConnection.getConnection();

                PreparedStatement ps1 = conn.prepareStatement(query);
                ps1.setString(1, trieuChung_txtbox.getText());
                ps1.setString(2, findLoaiBenh_ID(loaiBenh_combobox.getText()));
                ps1.setString(3, rowDataProperties.get().getIdPKB());

                //ps1.setString(4, nguoiKham_combobox.getSelectedItem().getUsername());
                ps1.executeUpdate();

                PreparedStatement ps2 = conn.prepareStatement(query2);
                ps2.setString(1, cccd_txtbox.getText());
                ps2.setString(2, hoTen_txtbox.getText());
                ps2.setDate(3, java.sql.Date.valueOf(ngayKham_datepicker.getValue()));
                ps2.setString(4, rowDataProperties.get().getIdDSKB());
                ps2.executeUpdate();



            } catch (Exception e) {
                e.printStackTrace();
            }
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        updateData();
        setupPaginated();
        setupContextMenu();

        table_thuoc.autosizeColumnsOnInitialization();
        table_thuoc.getTableColumns().get(4).setPrefWidth(200);
    }

    private void setupPaginated() {

        //Tao cac cot cua tableview
        MFXTableColumn<ThuocPKB> stt = new MFXTableColumn<>("STT", false, Comparator.comparing(ThuocPKB::getStt));
        MFXTableColumn<ThuocPKB> tenthuoc = new MFXTableColumn<>("Thuốc", false, Comparator.comparing(ThuocPKB::getTenThuoc));
        MFXTableColumn<ThuocPKB> donvi = new MFXTableColumn<>("Đơn vị", false, Comparator.comparing(ThuocPKB::getDonVi));
        MFXTableColumn<ThuocPKB> soluong = new MFXTableColumn<>("Số lượng", false, Comparator.comparing(ThuocPKB::getSoLuong));
        MFXTableColumn<ThuocPKB> cachdung = new MFXTableColumn<>("Cách dùng", false, Comparator.comparing(ThuocPKB::getCachDung));

        stt.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocPKB::getStt));
        tenthuoc.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocPKB::getTenThuoc));
        donvi.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocPKB::getDonVi));
        soluong.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocPKB::getSoLuong));
        cachdung.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocPKB::getCachDung));

        //Them cac cot vao tableview
        table_thuoc.getTableColumns().addAll(stt, tenthuoc, donvi, soluong, cachdung);

        //Them cac filter cho tableview
        table_thuoc.getFilters().addAll(
                //new StringFilter<>("STT", (ThuocPKB::getStt) ),
                new StringFilter<>("Thuốc", ThuocPKB::getTenThuoc),
                new StringFilter<>("Đơn vị", ThuocPKB::getDonVi),
                //new StringFilter<>("Số lượng", ThuocPKB::getSoLuong),
                new StringFilter<>("Cách dùng", ThuocPKB::getCachDung)
        );

        //Them du lieu vao tableview
        setData();
        table_thuoc.setItems(list);


    }
    public void setData(){
        list = FXCollections.observableArrayList(
                new ThuocPKB(1, "Thuốc A", "Viên", 10, "Uống 3 lần/ngày"),
                new ThuocPKB(2, "Thuốc B", "Viên", 20, "Uống 2 lần/ngày"),
                new ThuocPKB(3, "Thuốc C", "Viên", 30, "Uống 1 lần/ngày"),
                new ThuocPKB(4, "Thuốc D", "Viên", 40, "Uống 4 lần/ngày"),
                new ThuocPKB(5, "Thuốc E", "Viên", 50, "Uống 5 lần/ngày"),
                new ThuocPKB(6, "Thuốc F", "Viên", 60, "Uống 6 lần/ngày"),
                new ThuocPKB(7, "Thuốc G", "Viên", 70, "Uống 7 lần/ngày"),
                new ThuocPKB(8, "Thuốc H", "Viên", 80, "Uống 8 lần/ngày"),
                new ThuocPKB(9, "Thuốc I", "Viên", 90, "Uống 9 lần/ngày"),
                new ThuocPKB(10, "Thuốc K", "Viên", 100, "Uống 10 lần/ngày"),
                new ThuocPKB(11, "Thuốc L", "Viên", 110, "Uống 11 lần/ngày"),
                new ThuocPKB(12, "Thuốc M", "Viên", 120, "Uống 12 lần/ngày"),
                new ThuocPKB(13, "Thuốc N", "Viên", 130, "Uống 13 lần/ngày"),
                new ThuocPKB(14, "Thuốc O", "Viên", 140, "Uống 14 lần/ngày")
        );
    }

    public void setupContextMenu(){
        MFXContextMenu contextMenu = new MFXContextMenu(table_thuoc);
        MFXContextMenuItem edit = new MFXContextMenuItem("Chỉnh sửa");
        MFXContextMenuItem delete = new MFXContextMenuItem("Xóa");
        contextMenu.getItems().addAll(edit, delete);
        edit.setStyle("-fx-text-fill: #2264D1; -fx-font-size: 16px; -fx-font-family: 'Times New Roman'");
        delete.setStyle("-fx-text-fill: red; -fx-font-size: 16px; -fx-font-family: 'Times New Roman'");

        // Them su kien cho nut chinh sua
        edit.setOnAction(event -> {
            try {
                MFXTableRow<ThuocPKB> row = (MFXTableRow<ThuocPKB>) contextMenu.getOwnerNode();
                ThuocPKB rowData = row.getData();
                SuaThuoc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Them menu context o moi dong cho paginated tableview
        table_thuoc.setTableRowFactory(thuocpkb -> {
            MFXTableRow<ThuocPKB> row = new MFXTableRow<>(table_thuoc, new ThuocPKB(-1,"","",-1, ""));
            row.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
                contextMenu.show(row, event.getScreenX(), event.getScreenY());
                event.consume();
            });
            row.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    contextMenu.hide();
                }
            });
            return row;
        });
    }

    public void SuaThuoc() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("sua_thuoc_pkb.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        Scene scene = new Scene(root, 320, 340);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void updateData(){
        PhieuKhamBenh data = rowDataProperties.get();
        String query = "SELECT * FROM LoaiBenh";
        String query2 = "SELECT * FROM TaiKhoan";
        ObservableList<LoaiBenh> loaiBenh_list = FXCollections.observableArrayList();
        ObservableList<TaiKhoanNP> nguoiKham_list = FXCollections.observableArrayList();

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                LoaiBenh temp = new LoaiBenh(rs.getString("LoaiBenh_ID"),rs.getString("TenBenh"));
                loaiBenh_list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query2);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                TaiKhoanNP temp = new TaiKhoanNP(rs.getString("username"),rs.getString("HoTen"));
                nguoiKham_list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cccd_txtbox.setText(data.getCccd());
        trieuChung_txtbox.setText(data.getTrieuChung());
        hoTen_txtbox.setText(data.getHoTen());
        ngayKham_datepicker.setValue(data.getNgayKham());
        if(!loaiBenh_list.isEmpty()){
            loaiBenh_combobox.setItems(loaiBenh_list);
        }
        else{
            System.out.println("Loai benh is empty");
        }
        loaiBenh_combobox.setText(data.getTenBenh());
        loaiBenh_combobox.setValue(data.getLoaiBenh());

        if(!nguoiKham_list.isEmpty()){
            nguoiKham_combobox.setItems(nguoiKham_list);
        }
        else{
            System.out.println("Nguoi kham is empty");
        }
    }

    public void LoadTableView(){
        String query = "SELECT * FROM DSTHuoc_PKB WHERE PKB_ID = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, rowDataProperties.get().getIdPKB());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ThuocPKB temp = new ThuocPKB(rs.getInt("STT"),rs.getString("TenThuoc"),rs.getString("DonVi"),rs.getInt("SoLuong"),rs.getString("CachDung"));
                list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
