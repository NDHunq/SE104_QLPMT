package com.example.qlpmt.KhamBenh;

import Model.*;
import Model.PhieuKhamBenh;
import com.example.qlpmt.DBConnection;
import com.example.qlpmt.HelloApplication;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.LongFilter;
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
import javafx.stage.Modality;
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
    private MFXTableView<DSThuoc_PKB> table_thuoc = new MFXTableView<>();
    private ObservableList<DSThuoc_PKB> list = FXCollections.observableArrayList();

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
        table_thuoc.getTableColumns().get(3).setPrefWidth(200);
    }

    private void setupPaginated() {

        //Tao cac cot cua tableview
        //MFXTableColumn<ThuocPKB> stt = new MFXTableColumn<>("STT", false, Comparator.comparing(ThuocPKB::getStt));
        MFXTableColumn<DSThuoc_PKB> tenthuoc = new MFXTableColumn<>("Thuốc", false, Comparator.comparing(DSThuoc_PKB::getTenThuoc));
        MFXTableColumn<DSThuoc_PKB> donvi = new MFXTableColumn<>("Đơn vị", false, Comparator.comparing(DSThuoc_PKB::getThuoc_DVT));
        MFXTableColumn<DSThuoc_PKB> soluong = new MFXTableColumn<>("Số lượng", false, Comparator.comparing(DSThuoc_PKB::getSoLuong));
        MFXTableColumn<DSThuoc_PKB> cachdung = new MFXTableColumn<>("Cách dùng", false, Comparator.comparing(DSThuoc_PKB::getThuoc_CachDung));

        //stt.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocPKB::getStt));
        tenthuoc.setRowCellFactory(khambenh -> new MFXTableRowCell<>(DSThuoc_PKB::getTenThuoc));
        donvi.setRowCellFactory(khambenh -> new MFXTableRowCell<>(DSThuoc_PKB::getThuoc_DVT));
        soluong.setRowCellFactory(khambenh -> new MFXTableRowCell<>(DSThuoc_PKB::getSoLuong));
        cachdung.setRowCellFactory(khambenh -> new MFXTableRowCell<>(DSThuoc_PKB::getThuoc_CachDung));

        //Them cac cot vao tableview
        table_thuoc.getTableColumns().addAll(tenthuoc, donvi, soluong, cachdung);

        //Them cac filter cho tableview
        table_thuoc.getFilters().addAll(
                //new StringFilter<>("STT", (ThuocPKB::getStt) ),
                new StringFilter<>("Thuốc", DSThuoc_PKB::getTenThuoc),
                new StringFilter<>("Đơn vị", DSThuoc_PKB::getThuoc_DVT),
                new IntegerFilter<>("Số lượng", DSThuoc_PKB::getSoLuong),
                new StringFilter<>("Cách dùng", DSThuoc_PKB::getThuoc_CachDung)
        );

        //chia deu kich thuoc cac cot de vua voi chieu rong cua tableview
        double tableViewWidth = table_thuoc.getPrefWidth();
        int numberOfColumns = table_thuoc.getTableColumns().size();
        for (MFXTableColumn column : table_thuoc.getTableColumns()) {
            column.setPrefWidth(tableViewWidth / numberOfColumns);
        }

        LoadTableView();
        //Them du lieu vao tableview
        table_thuoc.setItems(list);


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
                MFXTableRow<DSThuoc_PKB> row = (MFXTableRow<DSThuoc_PKB>) contextMenu.getOwnerNode();
                DSThuoc_PKB rowData = row.getData();
                SuaThuoc(rowData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Them menu context o moi dong cho paginated tableview
        table_thuoc.setTableRowFactory(thuocpkb -> {
            MFXTableRow<DSThuoc_PKB> row = new MFXTableRow<>(table_thuoc, new DSThuoc_PKB());
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

    public void SuaThuoc(DSThuoc_PKB rowData) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("sua_thuoc_pkb.fxml"));
        SuaThuocPKBController controller = new SuaThuocPKBController(rowData);
        loader.setController(controller);

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
        stage.initModality(Modality.APPLICATION_MODAL);
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
        String query = " SELECT DSTHuoc_PKB.Thuoc_ID, Thuoc.CachDung_ID, Thuoc.DonViThuoc_ID, TenThuoc, TenDVTHuoc, SoLuong, TenCachDung FROM DSTHuoc_PKB\n" +
                " INNER JOIN PKB ON DSTHuoc_PKB.PKB_ID = PKB.PKB_ID\n" +
                " INNER JOIN Thuoc ON DSTHuoc_PKB.Thuoc_ID = Thuoc.Thuoc_ID\n" +
                " INNER JOIN CachDung ON CachDung.CachDung_ID = Thuoc.CachDung_ID\n" +
                " INNER JOIN DonViThuoc ON DonViThuoc.DVTHuoc_ID = Thuoc.DonViThuoc_ID" + " WHERE PKB.PKB_ID = '" + rowDataProperties.get().getIdPKB() + "'";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                CachDung cd = new CachDung(rs.getString("CachDung_ID"),rs.getString("TenCachDung"));
                DonViThuoc dvt = new DonViThuoc(rs.getString("DonViThuoc_ID"),rs.getString("TenDVTHuoc"));
                DSThuoc t = new DSThuoc(rs.getString("Thuoc_ID"),rs.getString("TenThuoc"),0,0,0,cd,dvt);
                DSThuoc_PKB temp = new DSThuoc_PKB(new PhieuKhamBenh(),t,rs.getInt("SoLuong"));
                list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
