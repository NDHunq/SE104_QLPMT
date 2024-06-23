package com.example.qlpmt.KhamBenh;

import Model.TaiKhoanNP;
import com.example.qlpmt.DBConnection;
import com.example.qlpmt.HelloApplication;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.synedra.validatorfx.Validator;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddPhieuKBController implements Initializable {
    @FXML
    private VBox pkb_layout;
    private Validator validator = new Validator();
    @FXML
    private MFXButton HuyBtn;
    private StringBinding problemsText;

    @FXML
    private MFXButton XongBtn;
    @FXML
    private MFXButton add_butt;
    @FXML
    private ImageView close;

    @FXML
    private MFXTextField CCCDTxt;

    @FXML
    private MFXTextField HoTenTxt;
    @FXML
    private MFXComboBox<String> LoaiBenhCBB;

    @FXML
    private MFXDatePicker NgayKhamPicker;

    @FXML
    private MFXComboBox<TaiKhoanNP> NguoiKhamCBB;

    @FXML
    private MFXTextField TrieuChungTxt;
    @FXML
    private MFXTableView<ThuocPKB> table_thuoc;
    private ObservableList<ThuocPKB> list;
    double x,y=0;
    Connection con;
    private PreparedStatement pst=null;
    private ResultSet rs=null;

    String DSKB_id;
    String PKB_id;
    String loaiBenhID;
    kham_benhController controller=new kham_benhController();
    public String findUsername(String string) {
        // Tìm kiếm đối tượng TaiKhoanNP dựa trên tên người khám
        for (TaiKhoanNP tk : NguoiKhamCBB.getItems()) {
            if (tk.getHoTen().equals(string)) {
                return tk.getUsername();
            }
        }
        return null;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        con= DBConnection.getConnection();
        close.setOnMouseClicked(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            String sql = "DELETE FROM DSTHuoc_PKB WHERE PKB_ID = ?";
            try {
                pst = con.prepareStatement(sql);
                pst.setString(1, PKB_id);
                pst.executeUpdate();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        HuyBtn.setOnAction((ActionEvent event) -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            String sql = "DELETE FROM DSTHuoc_PKB WHERE PKB_ID = ?";
            try {
                pst = con.prepareStatement(sql);
                pst.setString(1, PKB_id);
                pst.executeUpdate();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        XongBtn.setOnAction((ActionEvent event) -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            if(validator.validate()){
                String sql = "INSERT INTO PKB(PKB_ID, DSKB_ID, TrieuChung, LoaiBenh_ID, NguoiKham,STT) VALUES(?, ?, ?, ?, ?, ?)";
                try {
                    pst = con.prepareStatement(sql);
                    pst.setString(1, PKB_id);
                    pst.setString(2, DSKB_id);
                    pst.setString(3, TrieuChungTxt.getText());
                    pst.setString(4, loaiBenhID);
                    pst.setString(3, findUsername(NguoiKhamCBB.getText()));
                    int stt = Integer.parseInt(PKB_id.substring(3)); // Extract the number part from PKB_id
                    pst.setInt(6, stt);
                    pst.executeUpdate();
                    stage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String sql2 = "UPDATE DSKB SET CoPKB = 1 WHERE DSKB_ID = ?";
                try {
                    pst = con.prepareStatement(sql2);
                    pst.setString(1, DSKB_id);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                LocalDate selectedDate = NgayKhamPicker.getValue();
                if (selectedDate != null) {
                    try {
                        String checkSql = "SELECT * FROM DoanhThu WHERE NgayDT = ?";
                        PreparedStatement pst = con.prepareStatement(checkSql);
                        pst.setDate(1, java.sql.Date.valueOf(selectedDate));
                        rs = pst.executeQuery();
                        if (rs.next()) {
                            // Ngày đã tồn tại trong bảng DoanhThu
                            String updateSql = "UPDATE DoanhThu SET DoanhThu = DoanhThu + ?, SoBenhNhan = SoBenhNhan + 1 WHERE NgayDT = ?";
                            pst = con.prepareStatement(updateSql);
                            pst.setDouble(1, calculateTotalCost());
                            pst.setDate(2, java.sql.Date.valueOf(selectedDate));
                            pst.executeUpdate();
                        } else {
                            // Ngày chưa tồn tại trong bảng DoanhThu
                            String insertSql = "INSERT INTO DoanhThu(NgayDT, DoanhThu, SoBenhNhan) VALUES(?, ?, 1)";
                            pst = con.prepareStatement(insertSql);
                            pst.setDate(1, java.sql.Date.valueOf(selectedDate));
                            pst.setDouble(2, calculateTotalCost());
                            pst.executeUpdate();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                stage.close();
            }
            else{
                System.out.println("Validation failed");
            }
            controller.refreshPage();

        });
        add_butt.setOnAction((ActionEvent event) -> {
            try {
                AddThuoc(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        LoaiBenhCBB.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            String selectedBenh = newValue;
            String sql = "SELECT LoaiBenh_ID FROM LoaiBenh WHERE TenBenh = ?";
            try {
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, selectedBenh);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    loaiBenhID = rs.getString("LoaiBenh_ID");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        setupContextMenu();
       setupValidator();

    }
    private double calculateTotalCost() {
        double totalCost = 0;
        for (ThuocPKB thuoc : list) {
            String sql = "SELECT GiaBan FROM Thuoc WHERE TenThuoc = ?";
            try {
                pst = con.prepareStatement(sql);
                pst.setString(1, thuoc.getTenThuoc());
                rs = pst.executeQuery();
                if (rs.next()) {
                    totalCost += rs.getDouble("GiaBan") * thuoc.getSoLuong();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return totalCost;
    }
    public void refreshPage() {
        initialize(null, null);
        setupPaginated();
    }
    public void setKhamBenhKBController(kham_benhController controller) {
        this.controller = controller;
    }
    public void loadData() {
        try {
            String sql = "SELECT TenBenh FROM LoaiBenh";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // 3. Get the result and add to the ComboBox
            while (resultSet.next()) {
                String t = resultSet.getString("TenBenh");
                LoaiBenhCBB.getItems().add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<TaiKhoanNP> nguoiKham_list = FXCollections.observableArrayList();
        String query2 = "SELECT * FROM TaiKhoan";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query2);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                TaiKhoanNP temp = new TaiKhoanNP(rs.getString("username"),rs.getString("HoTen"));
                nguoiKham_list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!nguoiKham_list.isEmpty()){
            NguoiKhamCBB.setItems(nguoiKham_list);
        }
        else{
            System.out.println("Nguoi kham is empty");
        }
    }
    public void setupPaginated() {
        //Tao cac cot cua tableview
        MFXTableColumn<ThuocPKB> stt = new MFXTableColumn<>("STT", false, Comparator.comparing(ThuocPKB::getStt));
        MFXTableColumn<ThuocPKB> tenthuoc = new MFXTableColumn<>("Thuốc", false, Comparator.comparing(ThuocPKB::getTenThuoc));
        MFXTableColumn<ThuocPKB> donvi = new MFXTableColumn<>("Đơn vị", false, Comparator.comparing(ThuocPKB::getDonVi));
        MFXTableColumn<ThuocPKB> soluong = new MFXTableColumn<>("Số lượng", false, Comparator.comparing(ThuocPKB::getSoLuong));
        MFXTableColumn<ThuocPKB> cachdung = new MFXTableColumn<>("Cách dùng", false, Comparator.comparing(ThuocPKB::getCachDung));

        stt.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocPKB::getStt));
        tenthuoc.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocPKB::getTenThuoc));
        donvi.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocPKB::getDonVi));
        soluong.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocPKB::getSoLuong));
        cachdung.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocPKB::getCachDung));

        if (table_thuoc.getTableColumns().isEmpty()) {
            table_thuoc.getTableColumns().addAll(stt, tenthuoc, donvi, soluong, cachdung);
        }
        //Them cac filter cho tableview
        table_thuoc.getFilters().addAll(
                //new StringFilter<>("STT", (ThuocPKB::getStt) ),
                new StringFilter<>("Thuốc", ThuocPKB::getTenThuoc),
                new StringFilter<>("Đơn vị", ThuocPKB::getDonVi),
                //new StringFilter<>("Số lượng", ThuocPKB::getSoLuong),
                new StringFilter<>("Cách dùng", ThuocPKB::getCachDung)
        );

        //Them du lieu vao tableview
        setData();
        loadData();
        table_thuoc.setItems(list);

        double tableViewWidth = table_thuoc.getPrefWidth();
        int numberOfColumns = table_thuoc.getTableColumns().size();
        for (MFXTableColumn column : table_thuoc.getTableColumns()) {
            column.setPrefWidth(tableViewWidth / numberOfColumns);
        }
    }

    public void SuaThuoc(ActionEvent events,ThuocPKB rowData ) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("sua_thuoc_pkb.fxml"));
        SuaThuocPKBController2 controller = new SuaThuocPKBController2();
        loader.setController(controller);
        SuaThuocPKBController2 controllerr = loader.getController();
        controllerr.InitData(PKB_id,rowData);
        controllerr.setAddPhieuKBController(this);
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
        Scene scene = new Scene(root, 320, 340);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();

    }
    public void AddThuoc(ActionEvent events) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("add_thuoc_pkb.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        AddThuocPKBController controller = loader.getController();
        controller.InitData(PKB_id);
        controller.setAddPhieuKBController(this);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        Scene scene = new Scene(root, 320, 340);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public void setupContextMenu(){
        MFXContextMenu contextMenu = new MFXContextMenu(table_thuoc);
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
                    MFXTableRow<ThuocPKB> row = (MFXTableRow<ThuocPKB>) contextMenu.getOwnerNode();
                    ThuocPKB rowData = row.getData();
                    SuaThuoc(event,rowData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MFXTableRow<ThuocPKB> row = (MFXTableRow<ThuocPKB>) contextMenu.getOwnerNode();
                ThuocPKB rowData = row.getData();
                String Thuoc_ID=fetchId("Thuoc","TenThuoc","Thuoc_ID",rowData.getTenThuoc());

                // Delete the record from the DSTHuoc_PKB table
                String sql = "DELETE FROM DSTHuoc_PKB WHERE PKB_ID = ? AND Thuoc_ID = ?";
                try {
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, PKB_id);
                    pst.setString(2, Thuoc_ID);
                    pst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                refreshPage();
                System.out.println("Da xoa<3");
            }
        });
        table_thuoc.setTableRowFactory(phieukhambenh -> {
            MFXTableRow<ThuocPKB> roww = new MFXTableRow<>(table_thuoc, new ThuocPKB(0, "", "", 0, ""));
            roww.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
                contextMenu.show(roww, event.getScreenX(), event.getScreenY());
                event.consume();
            });
            roww.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    contextMenu.hide();
                }
            });
            return roww;
        });
    }
    String fetchId(String tableName, String columnName, String idColumnName, String name) {
        String id = "";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT " + idColumnName + " FROM " + tableName + " WHERE " + columnName + " = '" + name + "'");
            if (rs.next()) {
                id = rs.getString(idColumnName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
    public void setData(){
        try {
            list= FXCollections.observableArrayList();
            String sql="SELECT Thuoc.TenThuoc, DonViThuoc.TenDVTHuoc as TenDV, DSTHuoc_PKB.SoLuong, CachDung.TenCachDung " +
                    "FROM DSTHuoc_PKB " +
                    "JOIN Thuoc ON DSTHuoc_PKB.Thuoc_ID = Thuoc.Thuoc_ID " +
                    "JOIN DonViThuoc ON Thuoc.DonViThuoc_ID = DonViThuoc.DVTHuoc_ID " +
                    "JOIN CachDung ON Thuoc.CachDung_ID = CachDung.CachDung_ID " +
                    "WHERE PKB_ID = ?";
            pst=con.prepareStatement(sql);
            pst.setString(1, PKB_id);
            rs=pst.executeQuery();
            int stt = 1;
            while(rs.next()){
                list.add(new ThuocPKB(stt++,rs.getString("TenThuoc"),rs.getString("TenDV"),rs.getInt("SoLuong"),rs.getString("TenCachDung")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void setupValidator(){
        //Validator cho căn cước công dân
        validator.createCheck()
                .withMethod(c -> {
                    if (CCCDTxt.getText().isEmpty() || CCCDTxt.getText() == null) {
                        CCCDTxt.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error("Căn cước công dân không được để trống!");
                    }
                    else{
                        if(!CCCDTxt.getText().matches("[0-9]+")){
                            CCCDTxt.setStyle("-fx-border-color: red; -fx-text-fill: red");
                            c.error("Căn cước công dân không hợp lệ!");
                        }
                        else {
                            if(CCCDTxt.getText().length() != 12){
                                CCCDTxt.setStyle("-fx-border-color: red; -fx-text-fill: red");
                                c.error("Căn cước công dân không hợp lệ!");
                            }
                            else{
                                CCCDTxt.setStyle("-fx-border-color: #2264D1; -fx-text-fill: black");
                            }

                        }
                    }
                })
                .dependsOn("cccd", CCCDTxt.textProperty())
                .decorates(CCCDTxt)
                .immediate();

        //Validator cho họ tên
        validator.createCheck()
                .withMethod(c -> {
                    if (HoTenTxt.getText().isEmpty() || HoTenTxt.getText() == null) {
                        HoTenTxt.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error("Họ tên không được để trống!");
                    }
                    else{
                        HoTenTxt.setStyle("-fx-border-color: #2264D1; -fx-text-fill: black");
                    }
                })
                .dependsOn("hoTen", HoTenTxt.textProperty())
                .decorates(HoTenTxt)
                .immediate();

        //Validator cho triệu chứng
        validator.createCheck()
                .withMethod(c -> {
                    if (TrieuChungTxt.getText().isEmpty() || TrieuChungTxt.getText() == null) {
                        TrieuChungTxt.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error("Triệu chứng không được để trống!");
                    }
                    else{
                        TrieuChungTxt.setStyle("-fx-border-color: #2264D1; -fx-text-fill: black");
                    }
                })
                .dependsOn("trieuChung", TrieuChungTxt.textProperty())
                .decorates(TrieuChungTxt)
                .immediate();

        //Validator cho ngày khám
        validator.createCheck()
                .withMethod(c -> {
                    if (NgayKhamPicker.getValue().isAfter(LocalDate.now())) {
                        NgayKhamPicker.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        NgayKhamPicker.lookup(".mfx-icon-wrapper .mfx-font-icon").setStyle("-mfx-color: red;");
                        NgayKhamPicker.lookup(".mfx-icon-wrapper .mfx-ripple-generator").setStyle("-mfx-ripple-color: #FF6961;");
                        c.error("Ngày khám không được lớn hơn ngày hiện tại!");
                    }
                    else{
                        NgayKhamPicker.setStyle("-fx-border-color: #2264D1; -fx-text-fill: black");
                        NgayKhamPicker.lookup(".mfx-icon-wrapper .mfx-font-icon").setStyle("-mfx-color: #2264D1;");
                        NgayKhamPicker.lookup(".mfx-icon-wrapper .mfx-ripple-generator").setStyle("-mfx-ripple-color: #D4F2FF;");
                    }
                })
                .dependsOn("ngayKham", NgayKhamPicker.valueProperty())
                .decorates(NgayKhamPicker)
                .immediate();
    }
    private TextArea createProblemOutput() {
        TextArea problems = new TextArea();
        problems.setEditable(false);
        problems.setPrefHeight(80);
        problems.setBackground(Background.EMPTY);
        problems.setFocusTraversable(false);
        problemsText = Bindings.createStringBinding(this::getProblemText, validator.validationResultProperty());
        problems.textProperty().bind(problemsText);
        return problems;
    }

    //Ham dung de lay ra loi khi validate
    private String getProblemText() {
        return validator.validationResultProperty().get().getMessages().stream()
                .map(msg -> msg.getSeverity().toString() + ": " + msg.getText())
                .collect(Collectors.joining("\n"));
    }
    public void InitData(KhamBenh kb, LocalDate ngayKham) {
        HoTenTxt.setText(kb.getHoTen());
        CCCDTxt.setText(kb.getCCCD());
        NgayKhamPicker.setValue(ngayKham);

        try {
            // Get DSKB_ID
            String sql = "SELECT DSKB_ID FROM DSKB WHERE NgayKham = ? AND STT = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, java.sql.Date.valueOf(ngayKham));
            pst.setInt(2, Integer.parseInt(kb.getSTT()));

            // Execute query and get the result
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                DSKB_id = rs.getString("DSKB_ID");
            }

            // Get the next PKB_id
            sql = "SELECT MAX(PKB_id) AS MaxPKB_id FROM PKB";
            pst = con.prepareStatement(sql);

            // Execute query and get the result
            rs = pst.executeQuery();
            if (rs.next()) {
                String maxPKB_id = rs.getString("MaxPKB_id");
                int num = Integer.parseInt(maxPKB_id.substring(3)); // Get the number part
                num++; // Increment the number
                PKB_id = "PKB" + String.format("%03d", num); // Format the new number with leading zeros and prepend "PKB"
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupPaginated();

    }

}
