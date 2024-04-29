package com.example.qlpmt.KhamBenh;

import com.example.qlpmt.HelloApplication;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class kham_benhController implements Initializable {
    @FXML
    private VBox kb_layout;
    @FXML
    private ComboBox<String> sort_cbb;
    @FXML
    private Button add_butt;
    @FXML
    private MFXPaginatedTableView<KhamBenh> table_bn;
    double x,y=0;
    private ObservableList<KhamBenh> bn_list;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sort_cbb.getItems().add("Name: A-Z");
        sort_cbb.getItems().add("Name: Z-A");
        setupPaginated();

        //chia deu kich thuoc cac cot de vua voi chieu rong cua tableview
        double tableViewWidth = table_bn.getPrefWidth();
        int numberOfColumns = table_bn.getTableColumns().size();
        for (MFXTableColumn column : table_bn.getTableColumns()) {
            column.setPrefWidth(tableViewWidth / numberOfColumns);
        }

        //pkb.autosizeColumnsOnInitialization();

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
        Scene scene = new Scene(root, 400, 460);
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

        //Tao cac dong cho cot cua tableview
        stt.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getSTT));
        hoten.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getHoTen));
        cccd.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getCCCD));
        gioitinh.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getGioiTinh));
        namsinh.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getNamSinh));
        diachi.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getDiaChi));
        pkb.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getImgPkb));

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
                new KhamBenh("1", "Nguyen Van A", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img1"),
                new KhamBenh("2", "Nguyen Van B", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img2"),
                new KhamBenh("3", "Nguyen Van C", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img3"),
                new KhamBenh("4", "Nguyen Van D", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img4"),
                new KhamBenh("5", "Nguyen Van E", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img5"),
                new KhamBenh("6", "Nguyen Van F", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img6"),
                new KhamBenh("7", "Nguyen Van G", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img7"),
                new KhamBenh("8", "Nguyen Van H", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img8"),
                new KhamBenh("9", "Nguyen Van I", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img9"),
                new KhamBenh("10", "Nguyen Van K", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img10"),
                new KhamBenh("11", "Nguyen Van L", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img11"),
                new KhamBenh("12", "Nguyen Van M", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img12"),
                new KhamBenh("13", "Nguyen Van N", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img13"),
                new KhamBenh("14", "Nguyen Van O", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img14"),
                new KhamBenh("15", "Nguyen Van P", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img15"),
                new KhamBenh("16", "Nguyen Van Q", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img16"),
                new KhamBenh("17", "Nguyen Van R", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img17"),
                new KhamBenh("18", "Nguyen Van S", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img18"),
                new KhamBenh("19", "Nguyen Van T", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img19"),
                new KhamBenh("20", "Nguyen Van U", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img20"),
                new KhamBenh("21", "Nguyen Van V", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img21"),
                new KhamBenh("22", "Nguyen Van X", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img22"),
                new KhamBenh("23", "Nguyen Van Y", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img23"),
                new KhamBenh("24", "Nguyen Van Z", "09010235718", "Nam", "12/07/1999", "Ha Noi", "img24")
        );
    }
}
/*package com.example.qlpmt;

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
*/