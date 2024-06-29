package com.example.qlpmt.KhamBenh;

import com.example.qlpmt.AppUtils;
import com.example.qlpmt.DBConnection;
import com.example.qlpmt.HelloApplication;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewPhieuKBController implements Initializable {


    @FXML
    private MFXTextField CCCDTxt;

    @FXML
    private MFXTextField HoTenTxt;

    @FXML
    private MFXButton HoaDon;

    @FXML
    private MFXTextField LoaibenhTxt;

    @FXML
    private MFXDatePicker NgayKhamPicker;

    @FXML
    private MFXTextField NguoiKhamTxt;

    @FXML
    private MFXTextField TrieuChungTxt;

    @FXML
    private ImageView close;

    @FXML
    private MFXTableView<ThuocPKB> table_thuoc;
    private ObservableList<ThuocPKB> list;
    Connection con;
    private PreparedStatement pst=null;
    private ResultSet rs=null;
    double x,y;
    String DSKB_id;
    String PKB_id;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        con= DBConnection.getConnection();
        HoaDon.setOnAction((ActionEvent event) -> {
            HoaDon();
        });
        close.setOnMouseClicked(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
    }
    public void HoaDon(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hoa_don_stage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HoaDonController controller = loader.getController();
        controller.InitData(PKB_id,DSKB_id);
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
        Scene scene = new Scene(root, 680, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
        AppUtils.setIcon(stage);
        stage.show();
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
        if(table_thuoc.getTableColumns().isEmpty())
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
        double tableViewWidth = table_thuoc.getPrefWidth();
        int numberOfColumns = table_thuoc.getTableColumns().size();
        for (MFXTableColumn column : table_thuoc.getTableColumns()) {
            column.setPrefWidth(tableViewWidth / numberOfColumns);
        }

    }
    public void setData(){
        try {
            list= FXCollections.observableArrayList();
            String sql="SELECT Thuoc.TenThuoc, DonViThuoc.TenDVTHuoc as TenDV, DSTHuoc_PKB.SoLuong, CachDung.TenCachDung " +
                    "FROM DSTHuoc_PKB " +
                    "JOIN Thuoc ON DSTHuoc_PKB.Thuoc_ID = Thuoc.Thuoc_ID " +
                    "JOIN DonViThuoc ON Thuoc.DonViThuoc_ID = DonViThuoc.DVTHuoc_ID " +
                    "JOIN CachDung ON Thuoc.CachDung_ID = CachDung.CachDung_ID " +
                    "WHERE PKB_ID = ?";
            pst=con.prepareStatement(sql);
            pst.setString(1, PKB_id);
            rs=pst.executeQuery();
            int stt = 1;
            while(rs.next()){
                list.add(new ThuocPKB(stt++,rs.getString("TenThuoc"),rs.getString("TenDV"),rs.getInt("SoLuong"),rs.getString("TenCachDung")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void InitData(KhamBenh kb, LocalDate ngayKham) {
        HoTenTxt.setText(kb.getHoTen());
        CCCDTxt.setText(kb.getCCCD());
        NgayKhamPicker.setValue(ngayKham);
        NgayKhamPicker.setDisable(true);

        try {
            // Get DSKB_ID
            String sql = "SELECT DSKB_ID FROM DSKB WHERE NgayKham = ? AND STT = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, java.sql.Date.valueOf(ngayKham));
            pst.setInt(2, Integer.parseInt(kb.getSTT()));

            // Execute query and get the result
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                DSKB_id = rs.getString("DSKB_ID");
            }

            // Get PKB_ID, TrieuChung, NguoiKham, TenLoaiBenh from PKB and LoaiBenh
            sql = "SELECT PKB.PKB_ID, PKB.TrieuChung, PKB.NguoiKham, LoaiBenh.TenBenh " +
                    "FROM PKB JOIN LoaiBenh ON PKB.LoaiBenh_ID = LoaiBenh.LoaiBenh_ID " +
                    "WHERE PKB.DSKB_ID = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, DSKB_id);

            // Execute query and get the result
            rs = pst.executeQuery();
            if (rs.next()) {
                PKB_id = rs.getString("PKB_ID");
                TrieuChungTxt.setText(rs.getString("TrieuChung"));
                NguoiKhamTxt.setText(rs.getString("NguoiKham"));
                LoaibenhTxt.setText(rs.getString("TenBenh"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        setupPaginated();
    }


}
