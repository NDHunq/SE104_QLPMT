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
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class kho_thuocController implements Initializable {
@FXML
    public Button Add;

    DateTimeFormatter string_formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private MFXPaginatedTableView<KhoThuoc> khothuoc = new MFXPaginatedTableView<>();



    private ObservableList<KhoThuoc> KhoThuoc_list;
    public void initialize(URL location, ResourceBundle resources){
        setupPaginated();
        double tableViewWidth = khothuoc.getPrefWidth();
        int numberOfColumns = khothuoc.getTableColumns().size();
        System.out.println(numberOfColumns);
        for (MFXTableColumn column : khothuoc.getTableColumns()) {
            column.setPrefWidth(tableViewWidth / numberOfColumns);
        }
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
                new KhoThuoc(1,"Paracetamol", "Viên", 40, "5","Sửa"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5","Sửa"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5","Sửa"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5","Sửa"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5","Sửa"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5","Sửa"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5","Sửa"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5","Sửa"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5","Sửa"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5","Sửa"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5","Sửa"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5","Sửa"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5","Sửa"),
                new KhoThuoc(2,"Panadol", "Viên", 20, "2.5","Sửa")

        );

    }
    public void setupPaginated()
    {
        MFXTableColumn<KhoThuoc> stt = new MFXTableColumn<>("STT",false, Comparator.comparing(KhoThuoc::getStt));
        MFXTableColumn<KhoThuoc> tenthuoc = new MFXTableColumn<>("Tên Thuốc",false, Comparator.comparing(KhoThuoc::getTenthuoc));
        MFXTableColumn<KhoThuoc> don_vi = new MFXTableColumn<>("Đơn Vị",false, Comparator.comparing(KhoThuoc::getDonvi));
        MFXTableColumn<KhoThuoc> so_luong = new MFXTableColumn<>("Số Lượng",false, Comparator.comparing(KhoThuoc::getSoluong));
        MFXTableColumn<KhoThuoc> dongia = new MFXTableColumn<>("Đơn giá nhập",false, Comparator.comparing(KhoThuoc::getDongia));
        MFXTableColumn<KhoThuoc> img = new MFXTableColumn<>("Chỉnh sửa",false, Comparator.comparing(KhoThuoc::getImgThuoc));


        stt.setRowCellFactory(khoThuoc ->  new MFXTableRowCell<>(KhoThuoc::getStt));
        tenthuoc.setRowCellFactory(khoThuoc ->  new MFXTableRowCell<>(KhoThuoc::getTenthuoc));
        don_vi.setRowCellFactory(khoThuoc -> new MFXTableRowCell<>(KhoThuoc::getDonvi));
        so_luong.setRowCellFactory(khoThuoc ->  new MFXTableRowCell<>(KhoThuoc::getSoluong));
        dongia.setRowCellFactory(khoThuoc ->  new MFXTableRowCell<>(KhoThuoc::getDongia));
        img.setRowCellFactory(khoThuoc -> {
            MFXTableRowCell<KhoThuoc, String> cell = new MFXTableRowCell<>(KhoThuoc::getImgThuoc); cell.setTextFill(Color.BLUE);
            cell.setCursor(Cursor.HAND);
            cell.setUnderline(true);

            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
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

                }
            });
            return cell;
        });
        khothuoc.getTableColumns().addAll(stt,tenthuoc,don_vi,so_luong,dongia,img);
        khothuoc.getFilters().addAll(
                new StringFilter<>("Tên Thuốc",KhoThuoc::getTenthuoc),
                new StringFilter<>("Đơn vị",KhoThuoc::getDonvi),
                new IntegerFilter<>("Số lượng",KhoThuoc::getSoluong),
                new StringFilter<>("Đơn giá",KhoThuoc::getDongia)

        );
        setData();
        khothuoc.setItems(KhoThuoc_list);
    }

}
