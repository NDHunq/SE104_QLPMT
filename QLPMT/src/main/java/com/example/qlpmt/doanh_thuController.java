package com.example.qlpmt;

import Model.DoanhThu;
import Model.PhieuKhamBenh;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.FloatFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

public class doanh_thuController implements Initializable {

    private boolean isBangSoLieu = true;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);

    @FXML
    private AnchorPane doanh_thu_root_node;

    @FXML
    private MFXButton bangSoLieu;

    @FXML
    private MFXButton bieuDo;

    @FXML
    private MFXPaginatedTableView<DoanhThu> doanhThu;

    @FXML
    private MFXTextField search_txtbox;

    private ObservableList<DoanhThu> doanhThuList;

    //Ham khoi tao
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButton();
        setVisible();
        buttonClickEvent();

        //Khoi tao paginated tableview
        setupPaginated();

        //chia deu kich thuoc cac cot de vua voi chieu rong cua tableview
        double tableViewWidth = doanhThu.getPrefWidth();
        int numberOfColumns = doanhThu.getTableColumns().size();
        for (MFXTableColumn column : doanhThu.getTableColumns()) {
            column.setPrefWidth(tableViewWidth / numberOfColumns);
        }

        ////Tu dong dieu chinh kich thuoc cac cot de phu hop voi noi dung khi vua khoi tao
        //pkb.autosizeColumnsOnInitialization();

        //Tu dong dieu chinh kich thuoc cac cot de phu hop voi noi dung khi co thay doi
        When.onChanged(doanhThu.currentPageProperty())
                .then((oldValue, newValue) -> doanhThu.autosizeColumns())
                .listen();

        // Them su kien unfocus cho search_txtbox khi click ra ngoai bang cach requestFocus den node khac
        doanh_thu_root_node.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Kiem tra xem search_txtbox co dang duoc focus hay khong
                if (mouseEvent.getTarget() != search_txtbox) {
                    // Neu khong thi unfocus search_txtbox
                    doanh_thu_root_node.requestFocus();
                }
            }
        });

    }

    //Doi mau button khi chon giua cac tab
    public void setButton() {
        if(isBangSoLieu){
            bangSoLieu.setStyle("-fx-background-color: #2264D1; -fx-text-fill: #FFFFFF");
            bieuDo.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2264d1");
        } else {
            bieuDo.setStyle("-fx-background-color: #2264D1; -fx-text-fill: #FFFFFF");
            bangSoLieu.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2264d1");
        }
    }

    //Xu ly su kien khi click button
    public void buttonClickEvent(){
        bangSoLieu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!isBangSoLieu){
                    isBangSoLieu = !isBangSoLieu;
                    setButton();
                    setVisible();
                }
            }
        });

        bieuDo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(isBangSoLieu){
                    isBangSoLieu = !isBangSoLieu;
                    setButton();
                    setVisible();
                }
            }
        });
    }

    //Hien thi hoac an cac node cua tung tab
    public void setVisible(){
        if(isBangSoLieu){
            doanhThu.setVisible(true);
            search_txtbox.setVisible(true);
        } else {
            doanhThu.setVisible(false);
            search_txtbox.setVisible(false);
        }
    }

    public void setData(){
        doanhThuList = FXCollections.observableArrayList(
                new DoanhThu(1, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(2, LocalDate.parse("11/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(3, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(4, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(5, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(6, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(7, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(8, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(9, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(10, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(11, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(12, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(13, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(14, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(15, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(16, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(17, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(18, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(19, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(20, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(21, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(22, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(23, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(24, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(25, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(26, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(27, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(28, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(29, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(30, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(31, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(32, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(33, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(34, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(35, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(36, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(37, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(38, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(39, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(40, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(41, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(42, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(43, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(44, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(45, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(46, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(47, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(48, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(49, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(50, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f),
                new DoanhThu(51, LocalDate.parse("12/07/2023", formatter), 1000000, 10, 0.1f)
        );

    }
    public void setupPaginated(){
        //Tao cac cot cua tableview
        MFXTableColumn<DoanhThu> stt = new MFXTableColumn<>("STT", false, Comparator.comparing(DoanhThu::getId));
        MFXTableColumn<DoanhThu> ngaykham = new MFXTableColumn<>("Ngày khám", false, Comparator.comparing(DoanhThu::getNgayKham_string));
        MFXTableColumn<DoanhThu> doanhthu = new MFXTableColumn<>("Doanh thu", false, Comparator.comparing(DoanhThu::getDoanhThu));
        MFXTableColumn<DoanhThu> soluong = new MFXTableColumn<>("Số lượng bệnh nhân", false, Comparator.comparing(DoanhThu::getSoLuongBenhNhan));
        MFXTableColumn<DoanhThu> tile = new MFXTableColumn<>("Tỉ lệ", false, Comparator.comparing(DoanhThu::getTiLe));

        //Tao cac dong cho cot cua tableview
        stt.setRowCellFactory(doanh_thu -> new MFXTableRowCell<>(DoanhThu::getId));
        ngaykham.setRowCellFactory(doanh_thu -> new MFXTableRowCell<>(DoanhThu::getNgayKham_string));
        doanhthu.setRowCellFactory(doanh_thu -> new MFXTableRowCell<>(DoanhThu::getDoanhThu));
        soluong.setRowCellFactory(doanh_thu -> new MFXTableRowCell<>(DoanhThu::getSoLuongBenhNhan));
        tile.setRowCellFactory(doanh_thu -> new MFXTableRowCell<>(DoanhThu::getTiLe));

        //Them cac cot vao tableview
        doanhThu.getTableColumns().addAll(stt, ngaykham, doanhthu, soluong, tile);

        //Them cac filter cho tableview
        doanhThu.getFilters().addAll(
                new IntegerFilter<>("STT", DoanhThu::getId),
                new StringFilter<>("Ngày khám", DoanhThu::getNgayKham_string),
                new DoubleFilter<>("Doanh thu", DoanhThu::getDoanhThu),
                new IntegerFilter<>("Số lượng bệnh nhân", DoanhThu::getSoLuongBenhNhan),
                new FloatFilter<>("Tỉ lệ", DoanhThu::getTiLe)
        );

        //Them du lieu vao tableview
        setData();
        doanhThu.setItems(doanhThuList);
    }
}
