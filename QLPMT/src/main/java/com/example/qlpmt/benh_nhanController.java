package com.example.qlpmt;

import Model.LoaiBenh;
import Model.PhieuKhamBenh;
import com.example.qlpmt.KhamBenh.EditPhieuKBController;
import com.example.qlpmt.KhamBenh.KhamBenh;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.dialogs.MFXDialogs;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class benh_nhanController implements Initializable{

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
    @FXML
    private Text soBenhNhan = new Text();
    @FXML
    private AnchorPane benh_nhan_root_node = new AnchorPane();
    @FXML
    private MFXTextField search_txtbox = new MFXTextField();
    @FXML
    private MFXPaginatedTableView<PhieuKhamBenh> pkb = new MFXPaginatedTableView<>();
    @FXML
    private ImageView info_icon = new ImageView();

    private ObservableList<PhieuKhamBenh> pkb_list = FXCollections.observableArrayList();
    private double x,y=0;
    PreparedStatement pst = null;
    ResultSet rs = null;
    //Ham khoi tao khi khoi dong
    public void initialize(URL location, ResourceBundle resources){
        //Khoi tao paginated tableview
        setupPaginated();
        setupContextMenu();
        Connection con= DBConnection.getConnection();

        //chia deu kich thuoc cac cot de vua voi chieu rong cua tableview
//        double tableViewWidth = pkb.getPrefWidth();
//        int numberOfColumns = pkb.getTableColumns().size();
//        for (MFXTableColumn column : pkb.getTableColumns()) {
//            column.setPrefWidth(tableViewWidth / numberOfColumns);
//        }



//        //Tu dong dieu chinh kich thuoc cac cot de phu hop voi noi dung khi vua khoi tao
//        pkb.autosizeColumnsOnInitialization();

        //Tu dong dieu chinh kich thuoc cac cot de phu hop voi noi dung khi co thay doi
        When.onChanged(pkb.currentPageProperty())
                .then((oldValue, newValue) -> pkb.autosizeColumns())
                .listen();

        // Ham tim kiem khi go vao search_txtbox
        search_txtbox.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            String searchText = search_txtbox.getText();
            if (!searchText.isEmpty()) {
                try {
                    pkb_list = FXCollections.observableArrayList();
                    String sql = "SELECT DSKB.CCCD, DSKB.HoTen, LoaiBenh.TenBenh, PKB.TrieuChung, DSKB.NgayKham " +
                            "FROM DSKB " +
                            "JOIN PKB ON DSKB.DSKB_ID = PKB.DSKB_ID " +
                            "JOIN LoaiBenh ON PKB.LoaiBenh_ID = LoaiBenh.LoaiBenh_ID " +
                            "WHERE DSKB.HoTen LIKE ? OR DSKB.CCCD LIKE ? " +
                            "ORDER BY DSKB.HoTen, DSKB.CCCD";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, "%" + searchText + "%");
                    pst.setString(2, "%" + searchText + "%");
                    rs = pst.executeQuery();
                    int stt = 1;
                    while (rs.next()) {
                        pkb_list.add(new PhieuKhamBenh((stt),  rs.getString("CCCD"),rs.getString("HoTen"), rs.getString("TenBenh"), rs.getString("TrieuChung"), rs.getDate("NgayKham").toLocalDate()));
                        stt++;
                    }
                    pkb.setItems(pkb_list);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                reloadTableView();
                pkb.setItems(pkb_list);
            }
        });

        // Them su kien cho nut search_txtbox de focus vao search_txtbox
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

    //Ham khoi tao paginated tableview
    private void setupPaginated() {

        //Tao cac cot cua tableview
        MFXTableColumn<PhieuKhamBenh> stt = new MFXTableColumn<>("STT", false, Comparator.comparing(PhieuKhamBenh::getStt));
        MFXTableColumn<PhieuKhamBenh> cccd = new MFXTableColumn<>("CCCD", false, Comparator.comparing(PhieuKhamBenh::getCccd));
        MFXTableColumn<PhieuKhamBenh> hoten = new MFXTableColumn<>("Họ tên", false, Comparator.comparing(PhieuKhamBenh::getHoTen));
        MFXTableColumn<PhieuKhamBenh> loaibenh = new MFXTableColumn<>("Loại bệnh", false, Comparator.comparing(PhieuKhamBenh::getTenBenh));
        MFXTableColumn<PhieuKhamBenh> trieuchung = new MFXTableColumn<>("Triệu chứng", false, Comparator.comparing(PhieuKhamBenh::getTrieuChung));
        MFXTableColumn<PhieuKhamBenh> ngaykham = new MFXTableColumn<>("Ngày khám", false, Comparator.comparing(PhieuKhamBenh::getNgayKham));

        //Tao cac dong cho cot cua tableview
        stt.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(PhieuKhamBenh::getStt));
        cccd.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(PhieuKhamBenh::getCccd));
        hoten.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(PhieuKhamBenh::getHoTen));
        loaibenh.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(PhieuKhamBenh::getTenBenh));
        trieuchung.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(PhieuKhamBenh::getTrieuChung));
        ngaykham.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(PhieuKhamBenh::getNgayKham_string));

        //Them cac cot vao tableview
        pkb.getTableColumns().addAll(stt, cccd, hoten, loaibenh, trieuchung, ngaykham);

        //Them cac filter cho tableview
        pkb.getFilters().addAll(
                new IntegerFilter<>("STT", PhieuKhamBenh::getStt),
                new StringFilter<>("CCCD", PhieuKhamBenh::getCccd),
                new StringFilter<>("Họ tên", PhieuKhamBenh::getHoTen),
                new StringFilter<>("Loại Bệnh", PhieuKhamBenh::getTenBenh),
                new StringFilter<>("Triệu chứng", PhieuKhamBenh::getTrieuChung),
                new StringFilter<>("Ngày khám", PhieuKhamBenh::getNgayKham_string)
        );

        //Them du lieu vao tableview
        LoadTableView();

        //Chia kich thuoc cua cot
        stt.autosize();
        cccd.setPrefWidth(167);
        ngaykham.autosize();
        hoten.setPrefWidth((pkb.getPrefWidth() - (stt.getPrefWidth() + cccd.getPrefWidth() + ngaykham.getPrefWidth())) / 3);
        loaibenh.setPrefWidth(hoten.getPrefWidth());
        trieuchung.setPrefWidth(hoten.getPrefWidth());
    }

    //Ham khoi tao context menu
    public void setupContextMenu(){
        MFXContextMenu contextMenu = new MFXContextMenu(pkb);
        MFXContextMenuItem edit = new MFXContextMenuItem("Chỉnh sửa");
        MFXContextMenuItem delete = new MFXContextMenuItem("Xóa");
        contextMenu.getItems().addAll(edit, delete);
        edit.setStyle("-fx-text-fill: #2264D1; -fx-font-size: 16px; -fx-font-family: 'Times New Roman'");
        delete.setStyle("-fx-text-fill: red; -fx-font-size: 16px; -fx-font-family: 'Times New Roman'");

        // Them su kien cho nut chinh sua
        edit.setOnAction(event -> {
            MFXTableRow<PhieuKhamBenh> row = (MFXTableRow<PhieuKhamBenh>) contextMenu.getOwnerNode();
            PhieuKhamBenh rowData = row.getData();
            EditPKB(rowData);
        });

        delete.setOnAction(event -> {
            MFXTableRow<PhieuKhamBenh> row = (MFXTableRow<PhieuKhamBenh>) contextMenu.getOwnerNode();
            PhieuKhamBenh rowData = row.getData();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType buttonTypeYes = new ButtonType("Có");
            ButtonType buttonTypeNo = new ButtonType("Không");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.setTitle("Xác nhận");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xóa thuốc này không?");
            Window window = alert.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(e -> alert.close());
            ButtonType result = alert.showAndWait().orElse(buttonTypeNo);

            if (result == buttonTypeYes) {
                String query = "DELETE FROM PKB WHERE PKB_ID = ?";
                String query2 = "DELETE FROM DSThuoc_PKB WHERE PKB_ID = ?";
                String query3 = "DELETE FROM HD WHERE PKB_ID = ?";

                if(checkPKBIDExists(rowData.getIdPKB(), "DSThuoc_PKB")){
                    try(Connection conn = DBConnection.getConnection();
                        PreparedStatement pst = conn.prepareStatement(query2)) {
                        pst.setString(1, rowData.getIdPKB());
                        pst.executeUpdate();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                if(checkPKBIDExists(rowData.getIdPKB(), "HD")){
                    try(Connection conn = DBConnection.getConnection();
                        PreparedStatement pst = conn.prepareStatement(query3)) {
                        pst.setString(1, rowData.getIdPKB());
                        pst.executeUpdate();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                try(Connection conn = DBConnection.getConnection();
                    PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setString(1, rowData.getIdPKB());
                    pst.executeUpdate();

                    reloadTableView();
                    System.out.println("Xoa thanh cong");
                }catch (Exception e){
                    e.printStackTrace();
                }
                reloadTableView();
            }
        });

        // Them menu context o moi dong cho paginated tableview
        pkb.setTableRowFactory(phieukhambenh -> {
            MFXTableRow<PhieuKhamBenh> row = new MFXTableRow<>(pkb, new PhieuKhamBenh(-1, "","","","", LocalDate.now()));
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

    public void EditPKB(PhieuKhamBenh rowData){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("edit_phieukb.fxml"));

        EditPhieuKBController controller = new EditPhieuKBController(rowData);
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(root == null){
            System.out.println("ERROR: Khong the load file fxml");
        }else{
            System.out.println("SUCCESS: Load file fxml thanh cong");
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
        Scene scene = new Scene(root, 684, 539);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.initModality(Modality.APPLICATION_MODAL);

        //reload lai table view sau khi update
        stage.setOnHidden(e -> reloadTableView());
        stage.setScene(scene);
        AppUtils.setIcon(stage);
        stage.show();

    }

    public void LoadTableView(){
        String query = "SELECT PKB.PKB_ID, PKB.LoaiBenh_ID, PKB.DSKB_ID, NguoiKham ,PKB.STT, CCCD, DSKB.HoTen, TenBenh, TrieuChung, TaiKhoan.HoTen AS NGUOIKHAM,NgayKham FROM PKB INNER JOIN LoaiBenh ON PKB.LoaiBenh_ID = LoaiBenh.LoaiBenh_ID INNER JOIN DSKB ON DSKB.DSKB_ID = PKB.DSKB_ID JOIN TaiKhoan ON PKB.NguoiKham = TaiKhoan.username";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                pkb_list.add(new PhieuKhamBenh(rs.getString("PKB_ID"),rs.getString("LoaiBenh_ID"),rs.getString("DSKB_ID"),rs.getString("NguoiKham"), rs.getInt("STT"), rs.getString("CCCD"), rs.getNString("HoTen"), rs.getNString("TenBenh"), rs.getNString("TrieuChung"),rs.getNString("NGUOIKHAM") ,rs.getDate("NgayKham").toLocalDate()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(pkb_list != null){
            pkb.setItems(pkb_list);
        }
        else{
            System.out.println("ERROR: Khong co du lieu");
        }
    }

    public void reloadTableView() {
        pkb_list.clear(); // Xóa dữ liệu hiện tại
        LoadTableView(); // Load lại dữ liệu từ cơ sở dữ liệu
    }

    public boolean checkPKBIDExists(String pkbID, String tableName) {
        String query = "SELECT 1 FROM "  + tableName + " WHERE PKB_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, pkbID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true; // PKB_ID tồn tại trong bảng
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // PKB_ID không tồn tại trong bảng
    }
}
