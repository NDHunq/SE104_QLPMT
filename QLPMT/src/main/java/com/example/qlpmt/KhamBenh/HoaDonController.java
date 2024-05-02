package com.example.qlpmt.KhamBenh;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class HoaDonController implements Initializable {

    @FXML
    private MFXButton InBtn;

    @FXML
    private ImageView close;

    @FXML
    private MFXTableView<ThuocHD> table_thuoc;
    private ObservableList<ThuocHD> list;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        close.setOnMouseClicked(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        InBtn.setOnAction((ActionEvent event) -> {

        });
        setupPaginated();
        table_thuoc.autosizeColumnsOnInitialization();
        table_thuoc.getTableColumns().get(4).setPrefWidth(200);

    }
    private void setupPaginated() {

        //Tao cac cot cua tableview
        MFXTableColumn<ThuocHD> stt = new MFXTableColumn<>("STT", false, Comparator.comparing(ThuocHD::getStt));
        MFXTableColumn<ThuocHD> tenthuoc = new MFXTableColumn<>("Thuốc", false, Comparator.comparing(ThuocHD::getTenThuoc));
        MFXTableColumn<ThuocHD> donvi = new MFXTableColumn<>("Đơn vị", false, Comparator.comparing(ThuocHD::getDonVi));
        MFXTableColumn<ThuocHD> soluong = new MFXTableColumn<>("Số lượng", false, Comparator.comparing(ThuocHD::getSoLuong));
        MFXTableColumn<ThuocHD> dongia = new MFXTableColumn<>("Đơn giá", false, Comparator.comparing(ThuocHD::getDonGia));
        MFXTableColumn<ThuocHD> thanhtien = new MFXTableColumn<>("Thành tiền", false, Comparator.comparing(ThuocHD::getThanhTien));


        stt.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocHD::getStt));
        tenthuoc.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocHD::getTenThuoc));
        donvi.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocHD::getDonVi));
        soluong.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocHD::getSoLuong));
        dongia.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocHD::getDonGia));
        thanhtien.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocHD::getThanhTien));

        //Them cac cot vao tableview
        table_thuoc.getTableColumns().addAll(stt, tenthuoc, donvi, soluong, dongia, thanhtien);

        //Them cac filter cho tableview
        table_thuoc.getFilters().addAll(
                //new StringFilter<>("STT", (ThuocPKB::getStt) ),
                new StringFilter<>("Thuốc", ThuocHD::getTenThuoc),
                new StringFilter<>("Đơn vị", ThuocHD::getDonVi),
                new StringFilter<>("Đơn giá", ThuocHD::getDonGia),
                new StringFilter<>("Thành tiền", ThuocHD::getThanhTien)

        );

        //Them du lieu vao tableview
        setData();
        table_thuoc.setItems(list);


    }
    public void setData(){
        list = FXCollections.observableArrayList(
                new ThuocHD(1, "Thuốc A", "Viên", 10, "100,000", "1,000,000"),
                new ThuocHD(2, "Thuốc B", "Viên", 20, "200,000", "4,000,000"),
                new ThuocHD(3, "Thuốc C", "Viên", 30, "300,000", "9,000,000"),
                new ThuocHD(4, "Thuốc D", "Viên", 40, "400,000", "16,000,000"),
                new ThuocHD(5, "Thuốc E", "Viên", 50, "500,000", "25,000,000"),
                new ThuocHD(6, "Thuốc F", "Viên", 60, "600,000", "36,000,000"),
                new ThuocHD(7, "Thuốc G", "Viên", 70, "700,000", "49,000,000"),
                new ThuocHD(8, "Thuốc H", "Viên", 80, "800,000", "64,000,000"),
                new ThuocHD(9, "Thuốc I", "Viên", 90, "900,000", "81,000,000"),
                new ThuocHD(10, "Thuốc K", "Viên", 100, "1,000,000", "100,000,000"),
                new ThuocHD(11, "Thuốc L", "Viên", 110, "1,100,000", "121,000,000"),
                new ThuocHD(12, "Thuốc M", "Viên", 120, "1,200,000", "144,000,000"),
                new ThuocHD(13, "Thuốc N", "Viên", 130, "1,300,000", "169,000,000"),
                new ThuocHD(14, "Thuốc O", "Viên", 140, "1,400,000", "196,000,000")
               );
    }
}
