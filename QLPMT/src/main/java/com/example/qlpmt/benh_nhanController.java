package com.example.qlpmt;

import Model.PhieuKhamBenh;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

public class benh_nhanController implements Initializable{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
    @FXML
    private Text soBenhNhan;
    @FXML
    private AnchorPane benh_nhan_root_node;
    @FXML
    private MFXTextField search_txtbox;
    @FXML
    private MFXPaginatedTableView<PhieuKhamBenh> pkb;

    private ObservableList<PhieuKhamBenh> pkb_list;

    //Ham khoi tao khi khoi dong
    public void initialize(URL location, ResourceBundle resources){
        //Khoi tao paginated tableview
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
        benh_nhan_root_node.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Kiem tra xem search_txtbox co dang duoc focus hay khong
                if (mouseEvent.getTarget() != search_txtbox) {
                    // Neu khong thi unfocus search_txtbox
                    benh_nhan_root_node.requestFocus();
                }
            }
        });

        // Cap nhat so luong benh nhan luc khoi tao
        soBenhNhan.setText(String.valueOf(pkb_list.size()));
    }

    //Ham khoi tao du lieu mau
    public void setData(){
        pkb_list = FXCollections.observableArrayList(
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter)),
                new PhieuKhamBenh("09010235718", "Nguyen Van A", "Ung thu co tu cung", "Dai ra mau", LocalDate.parse("12/07/2023", formatter))
        );
    }

    //Ham khoi tao paginated tableview
    private void setupPaginated() {

        //Tao cac cot cua tableview
        MFXTableColumn<PhieuKhamBenh> cccd = new MFXTableColumn<>("CCCD", false, Comparator.comparing(PhieuKhamBenh::getCccd));
        MFXTableColumn<PhieuKhamBenh> hoten = new MFXTableColumn<>("Họ tên", false, Comparator.comparing(PhieuKhamBenh::getHoTen));
        MFXTableColumn<PhieuKhamBenh> loaibenh = new MFXTableColumn<>("Loại bệnh", false, Comparator.comparing(PhieuKhamBenh::getLoaiBenh));
        MFXTableColumn<PhieuKhamBenh> trieuchung = new MFXTableColumn<>("Triệu chứng", false, Comparator.comparing(PhieuKhamBenh::getTrieuChung));
        MFXTableColumn<PhieuKhamBenh> ngaykham = new MFXTableColumn<>("Ngày khám", false, Comparator.comparing(PhieuKhamBenh::getNgayKham));

        //Tao cac dong cho cot cua tableview
        cccd.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(PhieuKhamBenh::getCccd));
        hoten.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(PhieuKhamBenh::getHoTen));
        loaibenh.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(PhieuKhamBenh::getLoaiBenh));
        trieuchung.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(PhieuKhamBenh::getTrieuChung));
        ngaykham.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(PhieuKhamBenh::getNgayKham_string));

        //Them cac cot vao tableview
        pkb.getTableColumns().addAll(cccd, hoten, loaibenh, trieuchung, ngaykham);

        //Them cac filter cho tableview
        pkb.getFilters().addAll(
                new StringFilter<>("CCCD", PhieuKhamBenh::getCccd),
                new StringFilter<>("Ho ten", PhieuKhamBenh::getHoTen),
                new StringFilter<>("Loai benh", PhieuKhamBenh::getLoaiBenh),
                new StringFilter<>("Trieu chung", PhieuKhamBenh::getTrieuChung),
                new StringFilter<>("Ngay kham", PhieuKhamBenh::getNgayKham_string)
        );

        //Them du lieu vao tableview
        setData();
        pkb.setItems(pkb_list);
    }
}
