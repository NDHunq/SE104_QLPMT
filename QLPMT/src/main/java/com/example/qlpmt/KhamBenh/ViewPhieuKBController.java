package com.example.qlpmt.KhamBenh;

import com.example.qlpmt.HelloApplication;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ViewPhieuKBController implements Initializable {

    @FXML
    private MFXButton HoaDon;

    @FXML
    private ImageView close;

    @FXML
    private MFXTableView<ThuocPKB> table_thuoc;
    private ObservableList<ThuocPKB> list;
double x,y;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HoaDon.setOnAction((ActionEvent event) -> {
            HoaDon();
        });
        close.setOnMouseClicked(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        setupPaginated();


        table_thuoc.autosizeColumnsOnInitialization();
        table_thuoc.getTableColumns().get(4).setPrefWidth(200);
        //Tu dong dieu chinh kich thuoc cac cot de phu hop voi noi dung
        //When.onChanged(table_thuoc.currentPageProperty()).then((oldValue, newValue) -> table_thuoc.autosizeColumns()).listen();


    }
    public void HoaDon(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hoa_don_stage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
