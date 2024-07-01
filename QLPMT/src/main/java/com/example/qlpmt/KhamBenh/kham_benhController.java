package com.example.qlpmt.KhamBenh;

import Model.PhieuKhamBenh;
import com.example.qlpmt.AppUtils;
import com.example.qlpmt.DBConnection;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class kham_benhController implements Initializable {
    @FXML
    private VBox kb_layout;
    @FXML
    private Button add_butt;
    @FXML
    private MFXDatePicker NgayKhamPicker;
    @FXML
    private MFXTextField search_txtbox;
    @FXML
    private MFXPaginatedTableView<KhamBenh> table_bn;
    double x,y=0;
    private ObservableList<KhamBenh> bn_list;
    private Connection con=null;
    private PreparedStatement pst=null;
    private ResultSet rs=null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        con= DBConnection.getConnection();
        NgayKhamPicker.setValue(LocalDate.now());        setupContextMenu();
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
        NgayKhamPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoadData();
                table_bn.setItems(bn_list);
            }
        });
        search_txtbox.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            String searchText = search_txtbox.getText();
            LocalDate selectedDate = NgayKhamPicker.getValue();
            if (!searchText.isEmpty() && selectedDate != null) {
                try {
                    bn_list = FXCollections.observableArrayList();
                    String sql = "SELECT * FROM DSKB WHERE (HoTen LIKE ? OR CCCD LIKE ?) AND NgayKham = ?";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, "%" + searchText + "%");
                    pst.setString(2, "%" + searchText + "%");
                    pst.setDate(3, java.sql.Date.valueOf(selectedDate));
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        bn_list.add(new KhamBenh(String.valueOf(rs.getInt("STT")), rs.getString("HoTen"), rs.getString("CCCD"), rs.getInt("GioiTinh"), String.valueOf(rs.getInt("NamSinh")), rs.getString("DiaChi"), (rs.getInt("CoPkb"))));
                    }
                    table_bn.setItems(bn_list);
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else {
                LoadData();
                table_bn.setItems(bn_list);
            }
        });
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
        Scene scene = new Scene(root, 330, 380);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        AppUtils.setIcon(stage);
        stage.centerOnScreen();
        stage.show();
    }
    public void Sua(ActionEvent events, String DSKB_id,KhamBenh kb) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("sua_khambenh.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        SuaKhamBenhController controller = loader.getController();
        controller.InitData(DSKB_id,kb,this);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        Scene scene = new Scene(root, 330, 380);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        AppUtils.setIcon(stage);
        stage.centerOnScreen();
        stage.show();
    }
    public void XemPKB(KhamBenh kb, LocalDate ngaykham){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("view_phieukb.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ViewPhieuKBController controller = loader.getController();
        controller.InitData(kb,ngaykham);
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
        stage.initModality(Modality.APPLICATION_MODAL);
        AppUtils.setIcon(stage);
        stage.centerOnScreen();
        stage.show();
    }
    public void AddPKB(KhamBenh kb, LocalDate ngaykham){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("add_phieukb.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddPhieuKBController controller = loader.getController();
        controller.InitData(kb,ngaykham);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        controller.setKhamBenhKBController(this);
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
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        AppUtils.setIcon(stage);
        stage.centerOnScreen();
        stage.show();
    }
    public void refreshPage() {
        initialize(null, null);
        setupPaginated();
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

        stt.setRowCellFactory(khambenh -> new MFXTableRowCell<>(KhamBenh::getSTT));
//        hoten.setRowCellFactory(khambenh -> {
//            MFXTableRowCell<KhamBenh, String> cell = new MFXTableRowCell<>(KhamBenh::getHoTen);
//            Tooltip tooltip = new Tooltip(cell.getText());
//            cell.setTooltip(tooltip);
//             return cell;
//        });
        hoten.setRowCellFactory(khambenh -> {
            MFXTableRowCell<KhamBenh, String> cell = new MFXTableRowCell<>(KhamBenh::getHoTen);
            Tooltip tooltip = new Tooltip();
            cell.textProperty().addListener((obs, oldText, newText) -> {
                tooltip.setText(newText);
            });
            cell.setTooltip(tooltip);
            return cell;
        });
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
                        KhamBenh kb=table_bn.getSelectionModel().getSelectedValues().get(0);
                        if(cell.getText()=="Tạo"){
                            AddPKB(kb,NgayKhamPicker.getValue());
                        }
                        if(cell.getText()=="Xem"){
                            XemPKB(kb,NgayKhamPicker.getValue());
                        }
                    }
                }
            });
            return cell;
        });


        //Them cac cot vao tableview
        if(table_bn.getTableColumns().isEmpty())
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
        LoadData();
        table_bn.setItems(bn_list);
    }
    public String getDSKB_id(KhamBenh kb) {
        String DSKB_id = "";
        try {
            String sql = "SELECT DSKB_id FROM DSKB WHERE CCCD = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, kb.getCCCD());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                DSKB_id = rs.getString("DSKB_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return DSKB_id;
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
                //KhamBenh kb=table_bn.getSelectionModel().getSelectedValues().get(0);
                MFXTableRow<KhamBenh> row = (MFXTableRow<KhamBenh>) contextMenu.getOwnerNode();
                KhamBenh kb = row.getData();
                // Handle the click event here
                try {
                    Sua(event, getDSKB_id(kb),kb);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                ButtonType buttonTypeYes = new ButtonType("Có");
                ButtonType buttonTypeNo = new ButtonType("Không");
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                alert.setTitle("Xác nhận");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc muốn xóa thông tin khách hàng này không?");
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                InputStream iconStream = AppUtils.class.getResourceAsStream("/com/example/qlpmt/images/cong.png");
                Image image = new Image(iconStream);
                alertStage.getIcons().add(image);
                Window window = alert.getDialogPane().getScene().getWindow();
                window.setOnCloseRequest(e -> alert.close());
                ButtonType result = alert.showAndWait().orElse(buttonTypeNo);

                if(result == buttonTypeYes){
                    MFXTableRow<KhamBenh> row = (MFXTableRow<KhamBenh>) contextMenu.getOwnerNode();
                    KhamBenh kb = row.getData();
                    String DSKB_id = getDSKB_id(kb);

                    try {
                        con.setAutoCommit(false); // Start transaction

                        // Delete from DSTHuoc_PKB
                        String sql = "DELETE FROM DSTHuoc_PKB WHERE PKB_id IN (SELECT PKB_id FROM PKB WHERE DSKB_id = ?)";
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.setString(1, DSKB_id);
                        pst.executeUpdate();

                        // Delete from PKB
                        sql = "DELETE FROM PKB WHERE DSKB_id = ?";
                        pst = con.prepareStatement(sql);
                        pst.setString(1, DSKB_id);
                        pst.executeUpdate();

                        // Delete from DSKB
                        sql = "DELETE FROM DSKB WHERE DSKB_id = ?";
                        pst = con.prepareStatement(sql);
                        pst.setString(1, DSKB_id);
                        pst.executeUpdate();

                        con.commit(); // Commit transaction
                    } catch (Exception e) {
                        try {
                            con.rollback(); // Rollback transaction on error
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    } finally {
                        try {
                            con.setAutoCommit(true); // End transaction
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    refreshPage();

                }
                else{
                    alert.close();
                }


            }
        });
        table_bn.setTableRowFactory(phieukhambenh -> {
            MFXTableRow<KhamBenh> row = new MFXTableRow<>(table_bn, new KhamBenh("", "", "", 0, "", "", 0));
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
    public void LoadData(){
        try {
            bn_list= FXCollections.observableArrayList();
            String sql="select * from DSKB where NgayKham=?";
            pst=con.prepareStatement(sql);
            java.sql.Date sqlDate = java.sql.Date.valueOf(NgayKhamPicker.getValue());
            pst.setDate(1, sqlDate);
            rs=pst.executeQuery();
            while(rs.next()){
                bn_list.add(new KhamBenh(String.valueOf(rs.getInt("STT")), rs.getString("HoTen"), rs.getString("CCCD"), rs.getInt("GioiTinh"), String.valueOf(rs.getInt("NamSinh")), rs.getString("DiaChi"), (rs.getInt("CoPkb"))));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }}