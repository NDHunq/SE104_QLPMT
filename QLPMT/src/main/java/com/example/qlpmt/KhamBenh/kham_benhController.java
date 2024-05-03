package com.example.qlpmt.KhamBenh;

import Model.PhieuKhamBenh;
import com.example.qlpmt.HelloApplication;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class kham_benhController implements Initializable {
    @FXML
    private VBox kb_layout;

    @FXML
    private Button add_butt;
    @FXML
    private MFXPaginatedTableView<KhamBenh> table_bn;
    double x,y=0;
    private ObservableList<KhamBenh> bn_list;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupContextMenu();
        setupPaginated();
        //chia deu kich thuoc cac cot de vua voi chieu rong cua tableview
        double tableViewWidth = table_bn.getPrefWidth();
        int numberOfColumns = table_bn.getTableColumns().size();
        for (MFXTableColumn column : table_bn.getTableColumns()) {
            column.setPrefWidth(tableViewWidth / numberOfColumns);
        }

        table_bn.autosizeColumnsOnInitialization();

        //Tu dong dieu chinh kich thuoc cac cot de phu hop voi noi dung
        When.onChanged(table_bn.currentPageProperty())
                .then((oldValue, newValue) -> table_bn.autosizeColumns())
                .listen();


    }

    public void add(ActionEvent events) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("add_khambenh_stage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        Scene scene = new Scene(root, 330, 440);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public void Sua(ActionEvent events) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("sua_khambenh.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        Scene scene = new Scene(root, 330, 440);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public void XemPKB(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view_phieukb.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        Scene scene = new Scene(root, 680, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public void AddPKB(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("add_phieukb.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        Scene scene = new Scene(root, 680, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void setupPaginated() {

        //Tao cac cot cua tableview
        MFXTableColumn<KhamBenh> stt = new MFXTableColumn<>("STT", false, Comparator.comparing(KhamBenh::getSTT));
        MFXTableColumn<KhamBenh> hoten = new MFXTableColumn<>("Họ tên", false, Comparator.comparing(KhamBenh::getHoTen));
        MFXTableColumn<KhamBenh> cccd = new MFXTableColumn<>("CCCD", false, Comparator.comparing( KhamBenh::getCCCD));
        MFXTableColumn<KhamBenh> gioitinh = new MFXTableColumn<>("Giới tính", false, Comparator.comparing( KhamBenh::getGioiTinh));
        MFXTableColumn<KhamBenh> namsinh = new MFXTableColumn<>("Năm sinh", false, Comparator.comparing( KhamBenh::getNamSinh));
        MFXTableColumn<KhamBenh> diachi = new MFXTableColumn<>("Địa chỉ", false, Comparator.comparing( KhamBenh::getDiaChi));
        MFXTableColumn<KhamBenh> pkb = new MFXTableColumn<>("Phiếu khám bệnh", false, Comparator.comparing( KhamBenh::getImgPkb));
        MFXTableColumn<KhamBenh> imageCol = new MFXTableColumn<>("Image");

        stt.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getSTT));
        hoten.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getHoTen));
        cccd.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getCCCD));
        gioitinh.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getGioiTinh));
        namsinh.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getNamSinh));
        diachi.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getDiaChi));
        pkb.setRowCellFactory(khambenh -> {
            MFXTableRowCell<KhamBenh, String> cell = new MFXTableRowCell<>(KhamBenh::getImgPkb);
            cell.setTextFill(Color.BLUE);
            cell.setCursor(Cursor.HAND);
            cell.setUnderline(true);
            cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 1) {
                        if(cell.getText()=="Tạo"){
                            AddPKB();
                        }
                        if(cell.getText()=="Xem"){
                            XemPKB();
                        }
                    }
                }
            });
            return cell;
        });


        //Them cac cot vao tableview
        table_bn.getTableColumns().addAll(stt, hoten, cccd, gioitinh, namsinh, diachi, pkb);

        //Them cac filter cho tableview
        table_bn.getFilters().addAll(
                new StringFilter<>("CCCD", KhamBenh::getCCCD),
                new StringFilter<>("Ho ten",  KhamBenh::getHoTen),
                new StringFilter<>("Gioi tinh",  KhamBenh::getGioiTinh),
                new StringFilter<>("Nam sinh",  KhamBenh::getNamSinh),
                new StringFilter<>("Dia chi",  KhamBenh::getDiaChi),
                new StringFilter<>("Phieu kham benh",  KhamBenh::getImgPkb)
        );

        //Them du lieu vao tableview
        setData();
        table_bn.setItems(bn_list);


    }
    public void setData(){
        bn_list = FXCollections.observableArrayList(
                new KhamBenh("1", "Nguyen Van A", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Tạo"),
                new KhamBenh("2", "Nguyen Van B", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Xem"),
                new KhamBenh("3", "Nguyen Van C", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Tạo"),
                new KhamBenh("4", "Nguyen Van D", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Xem"),
                new KhamBenh("5", "Nguyen Van E", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Tạo"),
                new KhamBenh("6", "Nguyen Van F", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Tạo"),
                new KhamBenh("7", "Nguyen Van G", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Xem"),
                new KhamBenh("8", "Nguyen Van H", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Xem"),
                new KhamBenh("9", "Nguyen Van I", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Xem"),
                new KhamBenh("10", "Nguyen Van K", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Tạo"),
                new KhamBenh("11", "Nguyen Van L", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Xem"),
                new KhamBenh("12", "Nguyen Van M", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Tạo"),
                new KhamBenh("13", "Nguyen Van N", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Xem"),
                new KhamBenh("14", "Nguyen Van O", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Xem"),
                new KhamBenh("15", "Nguyen Van P", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Tạo"),
                new KhamBenh("16", "Nguyen Van Q", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Tạo"),
                new KhamBenh("17", "Nguyen Van R", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Tạo"),
                new KhamBenh("18", "Nguyen Van S", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Xem"),
                new KhamBenh("19", "Nguyen Van T", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Tạo"),
                new KhamBenh("20", "Nguyen Van U", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Tạo"),
                new KhamBenh("21", "Nguyen Van V", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Xem"),
                new KhamBenh("22", "Nguyen Van X", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Tạo"),
                new KhamBenh("23", "Nguyen Van Y", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Xem"),
                new KhamBenh("24", "Nguyen Van Z", "09010235718", "Nam", "12/07/1999", "Ha Noi", "Tạo")
        );
    }
    public void setupContextMenu(){
        MFXContextMenu contextMenu = new MFXContextMenu(table_bn);
        MFXContextMenuItem edit = new MFXContextMenuItem("Chỉnh sửa");
        MFXContextMenuItem delete = new MFXContextMenuItem("Xóa");
        contextMenu.getItems().addAll(edit, delete);
        edit.setStyle("-fx-text-fill: #2264D1; -fx-font-size: 16px; -fx-font-family: 'Times New Roman'");
        delete.setStyle("-fx-text-fill: red; -fx-font-size: 16px; -fx-font-family: 'Times New Roman'");
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Handle the click event here
                try {
                    Sua(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Handle the click event here
                System.out.println("Xóa clicked");
            }
        });
        table_bn.setTableRowFactory(phieukhambenh -> {
            MFXTableRow<KhamBenh> row = new MFXTableRow<>(table_bn, new KhamBenh("", "", "", "", "", "", ""));
            row.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
                contextMenu.show(row, event.getScreenX(), event.getScreenY());
                event.consume();
            });
            row.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    contextMenu.hide();
                }
            });
            return row;
        });
    }
}