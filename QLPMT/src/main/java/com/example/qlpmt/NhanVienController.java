package com.example.qlpmt;

import Model.NhanVien;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

public class NhanVienController implements Initializable {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
    @FXML
    private Text soNhanVien;
    @FXML
    private AnchorPane nhanvien_root;
    @FXML
    private MFXTextField search_txtbox;
    @FXML
    private MFXPaginatedTableView<NhanVien> pkb;
    private ObservableList<NhanVien> pkb_list;
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        setupPaginated();

        //chia deu kich thuoc cac cot de vua voi chieu rong cua tableview
        double tableViewWidth = pkb.getPrefWidth();
        int numberOfColumns = pkb.getTableColumns().size();
        for (MFXTableColumn column : pkb.getTableColumns()) {
            column.setPrefWidth(tableViewWidth / numberOfColumns);
        }

        ////Tu dong dieu chinh kich thuoc cac cot de phu hop voi noi dung khi vua khoi tao
        //pkb.autosizeColumnsOnInitialization();

        //Tu dong dieu chinh kich thuoc cac cot de phu hop voi noi dung khi co thay doi
        When.onChanged(pkb.currentPageProperty())
                .then((oldValue, newValue) -> pkb.autosizeColumns())
                .listen();

        // Them su kien unfocus cho search_txtbox khi click ra ngoai bang cach requestFocus den node khac
        nhanvien_root.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Kiem tra xem search_txtbox co dang duoc focus hay khong
                if (mouseEvent.getTarget() != search_txtbox) {
                    // Neu khong thi unfocus search_txtbox
                    nhanvien_root.requestFocus();
                }
            }
        });

        // Cap nhat so luong benh nhan luc khoi tao
        soNhanVien.setText(String.valueOf(pkb_list.size()));
    }
    private void setupPaginated() {

        //Tao cac cot cua tableview

        MFXTableColumn<NhanVien> hoten = new MFXTableColumn<>("Ho Ten", false, Comparator.comparing(NhanVien::getHoten));
        MFXTableColumn<NhanVien> chucvu = new MFXTableColumn<>("Chuc vu", false, Comparator.comparing(NhanVien::getChucvu));
        MFXTableColumn<NhanVien> username = new MFXTableColumn<>("Username", false, Comparator.comparing(NhanVien::getUsername));
        MFXTableColumn<NhanVien> email = new MFXTableColumn<>("Email", false, Comparator.comparing(NhanVien::getEmail));

        //Tao cac dong cho cot cua tableview

        hoten.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(NhanVien::getHoten));
        chucvu.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(NhanVien::getChucvu));
        username.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(NhanVien::getUsername));
        email.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(NhanVien::getEmail));

        //Them cac cot vao tableview
        pkb.getTableColumns().addAll(hoten, chucvu, username, email);

        //Them cac filter cho tableview
        pkb.getFilters().addAll(
                new StringFilter<>("Ho ten", NhanVien::getHoten),
                new StringFilter<>("Chuc vu", NhanVien::getChucvu),
                new StringFilter<>("Username", NhanVien::getUsername),
                new StringFilter<>("Email", NhanVien::getEmail)
        );

        //Them du lieu vao tableview
        setData();
        pkb.setItems(pkb_list);
    }
    public void setData(){
        pkb_list = FXCollections.observableArrayList(
                new NhanVien("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau"),
                new NhanVien("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau")

        );
    }

}
