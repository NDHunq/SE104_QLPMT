package com.example.qlpmt;

import Model.PhieuKhamBenh;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

public class benh_nhanController implements Initializable{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
    DateTimeFormatter string_formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private MFXPaginatedTableView<PhieuKhamBenh> pkb;

    private ObservableList<PhieuKhamBenh> pkb_list;

    //Ham khoi tao khi khoi dong
    public void initialize(URL location, ResourceBundle resources){
        setupPaginated();

        //chia deu kich thuoc cac cot de vua voi chieu rong cua tableview
        double tableViewWidth = pkb.getPrefWidth();
        int numberOfColumns = pkb.getTableColumns().size();
        for (MFXTableColumn column : pkb.getTableColumns()) {
            column.setPrefWidth(tableViewWidth / numberOfColumns);
        }

        //pkb.autosizeColumnsOnInitialization();

        //Tu dong dieu chinh kich thuoc cac cot de phu hop voi noi dung
        When.onChanged(pkb.currentPageProperty())
                .then((oldValue, newValue) -> pkb.autosizeColumns())
                .listen();
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
        MFXTableColumn<PhieuKhamBenh> hoten = new MFXTableColumn<>("Ho Ten", false, Comparator.comparing(PhieuKhamBenh::getHoTen));
        MFXTableColumn<PhieuKhamBenh> loaibenh = new MFXTableColumn<>("Loai benh", false, Comparator.comparing(PhieuKhamBenh::getLoaiBenh));
        MFXTableColumn<PhieuKhamBenh> trieuchung = new MFXTableColumn<>("Trieu chung", false, Comparator.comparing(PhieuKhamBenh::getTrieuChung));
        MFXTableColumn<PhieuKhamBenh> ngaykham = new MFXTableColumn<>("Ngay kham", false, Comparator.comparing(PhieuKhamBenh::getNgayKham_string));

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
