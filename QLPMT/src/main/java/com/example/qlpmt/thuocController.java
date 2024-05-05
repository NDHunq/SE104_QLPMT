package com.example.qlpmt;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import Model.Thuoc;


public class thuocController implements Initializable {
    private java.sql.Connection dbConnection = null;
    @FXML
    private MFXPaginatedTableView<Thuoc> thuoc ;
    private ObservableList<Thuoc> Thuoc_list;

    public void initialize(URL location, ResourceBundle resources){
         dbConnection = DBConnection.getConnection();
        try {

            setupPaginated();
            double tableViewWidth = thuoc.getPrefWidth();
            int numberOfColumns = thuoc.getTableColumns().size();
            for (MFXTableColumn column : thuoc.getTableColumns()) {
                column.setPrefWidth(tableViewWidth / numberOfColumns);
            }

            thuoc.autosizeColumnsOnInitialization();


            When.onChanged(thuoc.currentPageProperty())
                    .then((oldValue, newValue) -> {

                        thuoc.autosizeColumns();
                    })
                    .listen();
        } catch (Exception e) {
            System.out.println("An error occurred during initialization");
            e.printStackTrace();
        }
    }
    public void setData()
    {
        Thuoc_list = FXCollections.observableArrayList(
                new Thuoc(1,"Paracetamol", "Viên", "20", "3"),
                new Thuoc(1,"Paracetamol", "Viên", "20", "3"),
                new Thuoc(1,"Paracetamol", "Viên", "20", "3"),
                new Thuoc(1,"Paracetamol", "Viên", "20", "3"),
                new Thuoc(1,"Paracetamol", "Viên", "20", "3"),
                new Thuoc(1,"Paracetamol", "Viên", "20", "3"),
                new Thuoc(1,"Paracetamol", "Viên", "20", "3"),
                new Thuoc(1,"Paracetamol", "Viên", "20", "3")

        );

    }
    public void setupPaginated()
    {
        MFXTableColumn<Thuoc> stt = new MFXTableColumn<>("STT",false, Comparator.comparing(Thuoc::getStt));
        MFXTableColumn<Thuoc> ten_thuoc = new MFXTableColumn<>("Tên Thuốc",false, Comparator.comparing(Thuoc::getTenthuoc));
        MFXTableColumn<Thuoc> don_vi = new MFXTableColumn<>("Đơn Vị",false, Comparator.comparing(Thuoc::getDonvi));
        MFXTableColumn<Thuoc> so_luong = new MFXTableColumn<>("Số Lượng",false, Comparator.comparing(Thuoc::getSoluong));
        MFXTableColumn<Thuoc> solandung = new MFXTableColumn<>("Số lần dùng",false, Comparator.comparing(Thuoc::getSolandung));

        stt.setRowCellFactory(device-> new MFXTableRowCell<>(Thuoc::getStt));
        ten_thuoc.setRowCellFactory(device-> new MFXTableRowCell<>(Thuoc::getTenthuoc));
        don_vi.setRowCellFactory(device-> new MFXTableRowCell<>(Thuoc::getDonvi));
        so_luong.setRowCellFactory(device-> new MFXTableRowCell<>(Thuoc::getSoluong));
        solandung.setRowCellFactory(device-> new MFXTableRowCell<>(Thuoc::getSolandung));

        thuoc.getTableColumns().addAll(stt,ten_thuoc,don_vi,so_luong,solandung);
        thuoc.getFilters().addAll(
                new IntegerFilter<>("stt",Thuoc::getStt),
                new StringFilter<>("tenthuoc",Thuoc::getTenthuoc),
                new StringFilter<>("donvi",Thuoc::getDonvi),
                new StringFilter<>("soluong",Thuoc::getSoluong),
                new StringFilter<>("solandung",Thuoc::getSolandung)

        );
        setData();
        thuoc.setItems(Thuoc_list);
    }
}
