package com.example.qlpmt;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;
import Model.Thuoc;
import java.time.LocalDate;

public class thuocController implements Initializable {
    private ObservableList<String> month_List;
    private ObservableList<String> year_List;

    @FXML
    private MFXComboBox<String> month_combobox;

    @FXML
    private MFXComboBox<String> year_combobox;
    private java.sql.Connection dbConnection = null;
    @FXML
    private MFXPaginatedTableView<Thuoc> thuoc ;
    private ObservableList<Thuoc> Thuoc_list;
    public PreparedStatement pst=null;
    private ResultSet rs=null;
    String selectedMonth ;
    String selectedYear ;
    LocalDate currentDate = LocalDate.now();


    public void initialize(URL location, ResourceBundle resources){
        dbConnection = DBConnection.getConnection();


      setComboBox();


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
    { selectedMonth = month_combobox.getSelectedItem();
        System.out.println("Month selected: " + selectedMonth);
        selectedYear = year_combobox.getSelectedItem();
        System.out.println("Year selected: " + selectedYear);
             int STT=0;

        try {
            Thuoc_list = FXCollections.observableArrayList();
            String sql="Select*from BCT inner join Thuoc on BCT.Thuoc_ID=Thuoc.Thuoc_ID" +
                    " inner join DonViThuoc on Thuoc.DonViThuoc_ID=DonViThuoc.DVTHuoc_ID Where Thang=? and Nam=?";
            pst=dbConnection.prepareStatement(sql);
            pst.setString(1,selectedMonth);
            pst.setString(2,selectedYear);
            rs=pst.executeQuery();
            System.out.println("execute query");
            while (rs.next())
            {
                STT++;
                Thuoc_list.add(new Thuoc(STT,rs.getString("TenThuoc"),rs.getString("TenDVTHuoc"),rs.getString("SoLuong"),rs.getString("SoLanDung")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
                new StringFilter<>("tenthuoc",Thuoc::getTenthuoc),
                new StringFilter<>("donvi",Thuoc::getDonvi),
                new StringFilter<>("soluong",Thuoc::getSoluong),
                new StringFilter<>("solandung",Thuoc::getSolandung)

        );

        thuoc.setItems(Thuoc_list);
    }
    public void setComboBox(){
        month_List = FXCollections.observableArrayList(
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "Tất cả"
        );

        year_List = FXCollections.observableArrayList(
                "2021", "2022", "2023", "2024", "2025"
        );

        month_combobox.setItems(month_List);
        year_combobox.setItems(year_List);
        month_combobox.selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            setData();
            thuoc.setItems(Thuoc_list);
        });
        year_combobox.selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            setData();
            thuoc.setItems(Thuoc_list);
        });

    }}


