package com.example.qlpmt;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;import java.awt.event.MouseEvent;

import javafx.fxml.Initializable;


import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;
import Model.Thuoc;
import javafx.scene.text.Text;
import javafx.scene.control.DatePicker;
import javafx.stage.Popup;
import javafx.util.StringConverter;


public class thuocController implements Initializable {

    DateTimeFormatter string_formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private MFXPaginatedTableView<Thuoc> thuoc = new MFXPaginatedTableView<>();
@FXML
    private ImageView lich;
@FXML
private Text hienlich;
    private DatePicker datePicker;
    private Popup popup;
    private ObservableList<Thuoc> Thuoc_list;
    private int check;
    public void initialize(URL location, ResourceBundle resources){
        setupPaginated();
        thuoc.autosizeColumnsOnInitialization();
        check = 0;

        When.onChanged(thuoc.currentPageProperty())
                .then((oldValue, newValue) -> thuoc.autosizeColumns())
                .listen();
        datePicker = new DatePicker();
        popup = new Popup();
        popup.getContent().add(datePicker);

        lich.setOnMouseClicked(event -> {
            check+=1;
            if(check%2!=0)
            {
                popup.show(lich, event.getScreenX()+50, event.getScreenY()-10);
                System.out.println("show lan:"+check);
            }

            if (popup.isShowing()&& check%2==0) {
                popup.hide();
                System.out.println("hide lan:"+check);
            }


        });
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                hienlich.setText(string_formatter.format(newValue));
            }
        });




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
