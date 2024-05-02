package com.example.qlpmt;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;
import Model.KhoThuoc;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class kho_thuocController implements Initializable {
@FXML
    public ImageView Add;

    DateTimeFormatter string_formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private MFXPaginatedTableView<KhoThuoc> khothuoc = new MFXPaginatedTableView<>();



    private ObservableList<KhoThuoc> KhoThuoc_list;
    public void initialize(URL location, ResourceBundle resources){
        setupPaginated();
        khothuoc.autosizeColumnsOnInitialization();

        When.onChanged(khothuoc.currentPageProperty())
                .then((oldValue, newValue) -> khothuoc.autosizeColumns())
                .listen();
        Add.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/ThemThuoc.fxml"));
                ThemThuocController controller = new ThemThuocController();
                loader.setController(controller);
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void setData()
    {

        KhoThuoc_list = FXCollections.observableArrayList(
                new KhoThuoc(1,"Paracetamol", "Viên", 40, "5"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5")

        );

    }
    public void setupPaginated()
    {
        MFXTableColumn<KhoThuoc> stt = new MFXTableColumn<>("STT",false, Comparator.comparing(KhoThuoc::getStt));
        MFXTableColumn<KhoThuoc> tenthuoc = new MFXTableColumn<>("Tên Thuốc",false, Comparator.comparing(KhoThuoc::getTenthuoc));
        MFXTableColumn<KhoThuoc> don_vi = new MFXTableColumn<>("Đơn Vị",false, Comparator.comparing(KhoThuoc::getDonvi));
        MFXTableColumn<KhoThuoc> so_luong = new MFXTableColumn<>("Số Lượng",false, Comparator.comparing(KhoThuoc::getSoluong));
        MFXTableColumn<KhoThuoc> dongia = new MFXTableColumn<>("Đơn giá nhập",false, Comparator.comparing(KhoThuoc::getDongia));


        stt.setRowCellFactory(khoThuoc -> {
            MFXTableRowCell<KhoThuoc, Integer> cell = new MFXTableRowCell<>(KhoThuoc::getStt);
            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    KhoThuoc rowData = khoThuoc;
                    // rowData is the KhoThuoc object of the clicked row
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/Sua_Thuoc.fxml"));
                        Sua_ThuocController controller = new Sua_ThuocController();
                        loader.setController(controller);
                        Scene scene = new Scene(loader.load());
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        controller.text_tenthuoc.setText(rowData.getTenthuoc());
                        controller.text_donvi.setText(rowData.getDonvi());
                        controller.text_soluong.setText(rowData.getSoluong().toString());
                        controller.text_dongia.setText(rowData.getDongia());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Now you can use these variables as needed
                }
            });
            return cell;
        });
        tenthuoc.setRowCellFactory(khoThuoc -> {
            MFXTableRowCell<KhoThuoc,String> cell = new MFXTableRowCell<>(KhoThuoc::getTenthuoc);

            return cell;
        });
        don_vi.setRowCellFactory(khoThuoc -> {
            MFXTableRowCell<KhoThuoc,String> cell = new MFXTableRowCell<>(KhoThuoc::getDonvi);

            return cell;
        });
        so_luong.setRowCellFactory(khoThuoc -> {
            MFXTableRowCell<KhoThuoc,Integer> cell = new MFXTableRowCell<>(KhoThuoc::getSoluong);

            return cell;
        });
        dongia.setRowCellFactory(khoThuoc -> {
            MFXTableRowCell<KhoThuoc,String> cell = new MFXTableRowCell<>(KhoThuoc::getDongia);

            return cell;
        });

        khothuoc.getTableColumns().addAll(stt,tenthuoc,don_vi,so_luong,dongia);
        khothuoc.getFilters().addAll(
                new IntegerFilter<>("stt",KhoThuoc::getStt),
                new StringFilter<>("tenthuoc",KhoThuoc::getTenthuoc),
                new StringFilter<>("donvi",KhoThuoc::getDonvi),
                new IntegerFilter<>("soluong",KhoThuoc::getSoluong),
                new StringFilter<>("dongia",KhoThuoc::getDongia)

        );
        setData();
        khothuoc.setItems(KhoThuoc_list);
    }

}
