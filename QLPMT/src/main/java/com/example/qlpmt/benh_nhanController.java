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

    public void initialize(URL location, ResourceBundle resources){
        setupPaginated();

        pkb.autosizeColumnsOnInitialization();

        When.onChanged(pkb.currentPageProperty())
                .then((oldValue, newValue) -> pkb.autosizeColumns())
                .listen();
    }

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


    private void setupPaginated() {
        MFXTableColumn<PhieuKhamBenh> cccd = new MFXTableColumn<>("CCCD", false, Comparator.comparing(PhieuKhamBenh::getCccd));
        MFXTableColumn<PhieuKhamBenh> hoten = new MFXTableColumn<>("Ho Ten", false, Comparator.comparing(PhieuKhamBenh::getHoTen));
        MFXTableColumn<PhieuKhamBenh> loaibenh = new MFXTableColumn<>("Loai benh", false, Comparator.comparing(PhieuKhamBenh::getLoaiBenh));
        MFXTableColumn<PhieuKhamBenh> trieuchung = new MFXTableColumn<>("Trieu chung", false, Comparator.comparing(PhieuKhamBenh::getTrieuChung));
        MFXTableColumn<PhieuKhamBenh> ngaykham = new MFXTableColumn<>("Ngay kham", false, Comparator.comparing(PhieuKhamBenh::getNgayKham_string));

        cccd.setRowCellFactory(device -> new MFXTableRowCell<>(PhieuKhamBenh::getCccd));
        hoten.setRowCellFactory(device -> new MFXTableRowCell<>(PhieuKhamBenh::getHoTen));
        loaibenh.setRowCellFactory(device -> new MFXTableRowCell<>(PhieuKhamBenh::getLoaiBenh));
        trieuchung.setRowCellFactory(device -> new MFXTableRowCell<>(PhieuKhamBenh::getTrieuChung));
        ngaykham.setRowCellFactory(device -> new MFXTableRowCell<>(PhieuKhamBenh::getNgayKham_string));

        pkb.getTableColumns().addAll(cccd, hoten, loaibenh, trieuchung, ngaykham);
        pkb.getFilters().addAll(
                new StringFilter<>("CCCD", PhieuKhamBenh::getCccd),
                new StringFilter<>("Ho ten", PhieuKhamBenh::getHoTen),
                new StringFilter<>("Loai benh", PhieuKhamBenh::getLoaiBenh),
                new StringFilter<>("Trieu chung", PhieuKhamBenh::getTrieuChung),
                new StringFilter<>("Ngay kham", PhieuKhamBenh::getNgayKham_string)
        );
        setData();
        pkb.setItems(pkb_list);
    }
}
