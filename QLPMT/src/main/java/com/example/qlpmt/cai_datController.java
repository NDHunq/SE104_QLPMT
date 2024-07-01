package com.example.qlpmt;
import Model.DonViThuocTable;
import Model.LoaiBenhTable;
import Model.Thuoc;
import com.example.qlpmt.Share;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Comparator;
import java.util.ResourceBundle;
import Model.CachDungtable;
import net.synedra.validatorfx.Validator;

import static com.example.qlpmt.loginController.role;
import static com.example.qlpmt.loginController.username;

public class cai_datController implements Initializable {
    @FXML
    private Text chucvu;
    @FXML
    public Text hoten;
    @FXML
    public Text tienkham;
    @FXML
    public Text bntoida;
    @FXML
    public Text tentaikhoan;
    @FXML
    public Text email;
    //    @FXML
//    public ImageView suachucvu;
    @FXML
    private ImageView suahoten;
    @FXML
    public ImageView suatienkham;
    @FXML
    private ImageView suabntoida;
    @FXML
    private MFXPaginatedTableView<CachDungtable> tb_cd;
    @FXML
    private MFXPaginatedTableView<DonViThuocTable> tb_dvt;
    @FXML
    private MFXPaginatedTableView<LoaiBenhTable> tb_benh ;
    @FXML
    private Button themcd;
    @FXML
    private Button themdvt;
    @FXML
    private Button thembenh;
    @FXML
    private MFXButton doimk;
    @FXML
    private HBox tg_benh;
    @FXML
    private HBox tg_cd;
    @FXML
    private HBox tg_dvt;
    @FXML
    private Text show1;
    @FXML
    private Text show2;
    @FXML
    private Text show3;

    private java.sql.Connection dbConnection = null;
    private ObservableList<CachDungtable> cachdung_list;
    private ObservableList<DonViThuocTable> dvt_list;
    private ObservableList<LoaiBenhTable> loaibenh_list;
    private Validator validator = new Validator();

    private
    ThemcachdungController controller = new ThemcachdungController(this);
    SuacachdungController suacachdungController = new SuacachdungController(this);


    boolean validation ;


    public void Validation()
    {
        validator.createCheck()
                .withMethod(c -> {
                    if (controller.them.getText().equals("")
                    ) {
                        controller.them.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error(" Không được để trống thông tin!");
                    }
                    else {
                        controller.them.setStyle("-fx-border-color: #2264D1; -fx-text-fill: black");
                    }
                })
                .dependsOn("them", controller.them.textProperty())
                .decorates(controller.them)
                .immediate();
        validator.createCheck()
                .withMethod(c -> {
                    int checkcd=1;
                    for(int i=0;i<cachdung_list.size();i++)
                    {
                        if(controller.them.getText().equals(cachdung_list.get(i).getTenCachDung())&&Share.getInstance().getSharedVariable().equals("11"))
                        {
                            checkcd=0;
                            break;
                        }
                    }
                    if(checkcd==0)
                    {
                        controller.them.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error("Tên cách dùng đã tồn tại!");
                    }
                    else {
                        controller.them.setStyle("-fx-border-color: #2264D1; -fx-text-fill: black");
                    }
                })
                .dependsOn("them", controller.them.textProperty())
                .decorates(controller.them)
                .immediate();
        validator.createCheck()
                .withMethod(c -> {
                    int checkdvt=1;
                    for(int i=0;i<dvt_list.size();i++)
                    {
                        if(controller.them.getText().equals(dvt_list.get(i).getTenDonVi())&&Share.getInstance().getSharedVariable().equals("12"))
                        {
                            checkdvt=0;
                            break;
                        }
                    }
                    if(checkdvt==0)
                    {
                        controller.them.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error("Tên đơn vị thuốc đã tồn tại!");
                    }
                    else {
                        controller.them.setStyle("-fx-border-color: #2264D1; -fx-text-fill: black");
                    }
                })
                .dependsOn("them", controller.them.textProperty())
                .decorates(controller.them)
                .immediate();
        validator.createCheck()
                .withMethod(c -> {
                    int checkbenh=1;
                    for(int i=0;i<loaibenh_list.size();i++)
                    {
                        if(controller.them.getText().equals(loaibenh_list.get(i).getTenLoaiBenh())&&Share.getInstance().getSharedVariable().equals("13") )
                        {
                            checkbenh=0;
                            break;
                        }
                    }
                    if(checkbenh==0)
                    {
                        controller.them.setStyle("-fx-border-color: red; -fx-text-fill: red");
                        c.error("Tên bệnh đã tồn tại!");
                    }
                    else {
                        controller.them.setStyle("-fx-border-color: #2264D1; -fx-text-fill: black");
                    }
                })
                .dependsOn("them", controller.them.textProperty())
                .decorates(controller.them)
                .immediate();
        validation = validator.validate();
        System.out.println(validation);

    }

    public void initialize(URL location, ResourceBundle resources){
        System.out.println(username);
        System.out.println(role);
        dbConnection = DBConnection.getConnection();
        String sql0 = "Select * from taikhoan where username = '"+username+"'";
        String sql = "SELECT * FROM ThongTinPK WHERE IdTT='TT01'";
        String sql1 = "SELECT * FROM ThongTinPK WHERE IdTT='TT02'";
        Statement statement = null;
        try {
            statement = dbConnection.createStatement();
            ResultSet resultSet0 = statement.executeQuery(sql0);
            if (resultSet0.next()) {
                hoten.setText(resultSet0.getString("HoTen"));
                chucvu.setText(resultSet0.getString("ChucVu"));
                tentaikhoan.setText(resultSet0.getString("username"));
                email.setText(resultSet0.getString("Email"));

            } else {
                System.out.println("Không tìm thấy hàng nào với IdTT='TT01'");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            Statement statement1 = dbConnection.createStatement();
            ResultSet resultSet = statement1.executeQuery(sql);

            Statement statement2 = dbConnection.createStatement();
            ResultSet resultSet1 = statement2.executeQuery(sql1);



            if (resultSet.next() && resultSet1.next()) {
                tienkham.setText(resultSet.getString("Gtri"));
                bntoida.setText(resultSet1.getString("Gtri"));
            } else {
                System.out.println("Không tìm thấy hàng nào với IdTT='TT01'");
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        try {
            setData();
            setupPaginated();
            //Cach dung
            double tableViewWidth = tb_cd.getPrefWidth();
            int numberOfColumns = tb_cd.getTableColumns().size();
            for (MFXTableColumn column : tb_cd.getTableColumns()) {
                column.setPrefWidth(tableViewWidth / numberOfColumns);
            }
            tb_cd.autosizeColumnsOnInitialization();
            When.onChanged(tb_cd.currentPageProperty())
                    .then((oldValue, newValue) -> {

                        tb_cd.autosizeColumns();
                    })
                    .listen();
            //Don vi thuoc
            double tableViewWidth1 = tb_dvt.getPrefWidth();
            int numberOfColumns1 = tb_dvt.getTableColumns().size();
            for (MFXTableColumn column : tb_dvt.getTableColumns()) {
                column.setPrefWidth(tableViewWidth1 / numberOfColumns1);
            }
            tb_dvt.autosizeColumnsOnInitialization();
            When.onChanged(tb_dvt.currentPageProperty())
                    .then((oldValue, newValue) -> {

                        tb_dvt.autosizeColumns();
                    })
                    .listen();
            //Loai benh
            double tableViewWidth2 = tb_benh.getPrefWidth();
            int numberOfColumns2 = tb_benh.getTableColumns().size();
            for (MFXTableColumn column : tb_benh.getTableColumns()) {
                column.setPrefWidth(tableViewWidth2 / numberOfColumns2);
            }
            tb_benh.autosizeColumnsOnInitialization();
            When.onChanged(tb_benh.currentPageProperty())
                    .then((oldValue, newValue) -> {

                        tb_benh.autosizeColumns();
                    })
                    .listen();
        } catch (Exception e) {
            System.out.println("An error occurred during initialization");
            e.printStackTrace();
        }

        suahoten.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/suathongtin.fxml"));
                suathongtinController controller = new suathongtinController(this);
                loader.setController(controller);
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                AppUtils.setIcon(stage);
                stage.show();
                controller.suathongtin.setText(hoten.getText());
                Share.getInstance().setSharedVariable("2");
                System.out.println(Share.getInstance().getSharedVariable());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        suatienkham.setOnMouseClicked(event -> {
            if(role.equals("Quản lý"))
            {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/suathongtin.fxml"));
                    suathongtinController controller = new suathongtinController(this);
                    loader.setController(controller);
                    Scene scene = new Scene(loader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    AppUtils.setIcon(stage);
                    stage.show();
                    controller.suathongtin.setText(tienkham.getText());
                    controller.suathongtin.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*")|| newValue.equals("0")) {
                            controller.suathongtin.setText(oldValue);
                        }
                    });
                    Share.getInstance().setSharedVariable("3");
                    System.out.println(Share.getInstance().getSharedVariable());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Ban không có quyền chỉnh sửa thông tin này");
                alert.showAndWait();
            }
        });
        suabntoida.setOnMouseClicked(event -> {
            if(role.equals("Quản lý"))
            {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/suathongtin.fxml"));
                    suathongtinController controller = new suathongtinController(this);
                    loader.setController(controller);
                    Scene scene = new Scene(loader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    AppUtils.setIcon(stage);
                    stage.show();
                    controller.suathongtin.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*")|| newValue.equals("0")) {
                            controller.suathongtin.setText(oldValue);
                        }
                    });
                    controller.suathongtin.setText(bntoida.getText());
                    Share.getInstance().setSharedVariable("4");
                    System.out.println(Share.getInstance().getSharedVariable());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Ban không có quyền chỉnh sửa thông tin này");
                alert.showAndWait();
            }
        });
        doimk.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/doimatkhau.fxml"));
                doimatkhauController controller = new doimatkhauController();
                loader.setController(controller);
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                AppUtils.setIcon(stage);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        tg_cd.setOnMouseClicked(event4 -> {
            if(tb_cd.isVisible())
            {
                show3.setText("Hiện");
            }
            else
            {
                show3.setText("Thu gọn");
            }
            tb_cd.setVisible(!tb_cd.isVisible());
            tb_cd.setManaged(tb_cd.isVisible());

        });
        tg_dvt.setOnMouseClicked(event4 -> {
            if(tb_dvt.isVisible())
            {
                show2.setText("Hiện");
            }
            else
            {
                show2.setText("Thu gọn");
            }
            tb_dvt.setVisible(!tb_dvt.isVisible());
            tb_dvt.setManaged(tb_dvt.isVisible());
        });
        tg_benh.setOnMouseClicked(event4 -> {
            if(tb_benh.isVisible())
            {
                show1.setText("Hiện");
            }
            else
            {
                show1.setText("Thu gọn");
            }
            tb_benh.setVisible(!tb_benh.isVisible());
            tb_benh.setManaged(tb_benh.isVisible());

        });
        themcd.setOnMouseClicked(event -> {
            if(role.equals("Quản lý"))
            {
                Share.getInstance().setSharedVariable("11");
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/themcachdung.fxml"));
                    loader.setController(controller);
                    Scene scene = new Scene(loader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene); AppUtils.setIcon(stage);

                    stage.show();
                    controller.xong.setOnMouseClicked(event1 -> {

                        Validation();
                        System.out.println("click vao button xong");
                        try {

                            for(int i=0;i<cachdung_list.size();i++)
                            {
                                if(controller.them.getText().equals(cachdung_list.get(i).getTenCachDung()))
                                {
//                                  check=0;
                                    break;
                                }
                            }
                            String soluongAsString = "CD";
                            if(validation) {
                                try {
                                    String str = "";
                                    int soluong = 0;
                                    str = "Select count(*) as total from CachDung";
                                    PreparedStatement pst = dbConnection.prepareStatement(str);
                                    ResultSet rs = pst.executeQuery();
                                    while (rs.next()) {
                                        soluong = rs.getInt("total");

                                    }
                                    System.out.println(soluong);
                                    if (soluong < 9) {
                                        soluongAsString += "0";
                                        soluongAsString += Integer.toString(soluong + 1);
                                    } else
                                        soluongAsString += Integer.toString(soluong + 1);


                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    PreparedStatement pstmt = dbConnection.prepareStatement("INSERT INTO CachDung VALUES (?,?)");
                                    pstmt.setString(1, soluongAsString);
                                    pstmt.setString(2, controller.them.getText());
                                    pstmt.executeUpdate();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                setData();
                                tb_cd.setItems(cachdung_list);
                                stage.close();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });


                    controller.huy.setOnMouseClicked(event1 -> {

                        stage.close();
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Ban không có quyền chỉnh sửa thông tin này");
                alert.showAndWait();
            }
        });
        themdvt.setOnMouseClicked(event -> {
            if(role.equals("Quản lý")) {
                Share.getInstance().setSharedVariable("12");
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/themcachdung.fxml"));
                    loader.setController(controller);
                    Scene scene = new Scene(loader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    AppUtils.setIcon(stage);
                    stage.show();
                    controller.xong.setOnMouseClicked(event1 -> {
                        Validation();
                        System.out.println("click vao button xong");
                        try {

                            for (int i = 0; i < dvt_list.size(); i++) {
                                if (controller.them.getText().equals(dvt_list.get(i).getTenDonVi())) {
//                                   check = 0;
                                    break;
                                }
                            }
                            String soluongAsString = "DV";
                            if(validation) {
                                try {
                                    int soluong = 0;

                                    String str="Select count(*) as total from DonViThuoc";
                                    PreparedStatement pst = dbConnection.prepareStatement(str);
                                    ResultSet rs = pst.executeQuery();
                                    while (rs.next()){
                                        soluong = rs.getInt("total");

                                    }
                                    System.out.println("so luong don vi la:"+soluong);
                                    if (soluong < 9) {
                                        soluongAsString += "0";
                                        soluongAsString += Integer.toString(soluong + 1);
                                    } else
                                        soluongAsString += Integer.toString(soluong + 1);



                                }
                                catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                                try {
                                    PreparedStatement pstmt = dbConnection.prepareStatement("INSERT INTO DonViThuoc VALUES (?,?)");
                                    pstmt.setString(1, soluongAsString);
                                    pstmt.setString(2, controller.them.getText());
                                    pstmt.executeUpdate();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                setData();
                                tb_dvt.setItems(dvt_list);
                                stage.close();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                    controller.huy.setOnMouseClicked(event1 -> {

                        stage.close();
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Ban không có quyền chỉnh sửa thông tin này");
                alert.showAndWait();
            }
        });
        thembenh.setOnMouseClicked(event -> {
            if(role.equals("Quản lý")) {
Share.getInstance().setSharedVariable("13");
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/themcachdung.fxml"));
                    loader.setController(controller);
                    Scene scene = new Scene(loader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    AppUtils.setIcon(stage);
                    stage.show();
                    controller.xong.setOnMouseClicked(event1 -> {
                        Validation();
                        System.out.println("click vao button xong");
                        try {

                            for (int i = 0; i < loaibenh_list.size(); i++) {
                                if (controller.them.getText().equals(loaibenh_list.get(i).getTenLoaiBenh())) {
//                               check = 0;
                                    break;
                                }
                            }
                            String soluongAsString = "LB";
                            if(validation) {
                                try {

                                    int soluong = 0;
                                    String str="Select count(*) as total from LoaiBenh";
                                    PreparedStatement pst = dbConnection.prepareStatement(str);
                                    ResultSet rs = pst.executeQuery();
                                    while (rs.next()){
                                        soluong = rs.getInt("total");

                                    }
                                    System.out.println("so luong benh la:"+soluong);
                                    if (soluong < 9) {
                                        soluongAsString += "0";
                                        soluongAsString += Integer.toString(soluong + 1);
                                    } else
                                        soluongAsString += Integer.toString(soluong + 1);


                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    PreparedStatement pstmt = dbConnection.prepareStatement("INSERT INTO LoaiBenh VALUES (?,?)");
                                    pstmt.setString(1, soluongAsString);
                                    pstmt.setString(2, controller.them.getText());
                                    pstmt.executeUpdate();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                setData();
                                tb_benh.setItems(loaibenh_list);
                                stage.close();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    controller.huy.setOnMouseClicked(event1 -> {

                        stage.close();
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Ban không có quyền chỉnh sửa thông tin này");
                alert.showAndWait();
            }
        });
    }

    public void setData()
    {
        dvt_list = FXCollections.observableArrayList();
        loaibenh_list = FXCollections.observableArrayList();
        cachdung_list = FXCollections.observableArrayList();
        int STT1=0;
        int STT2=0;
        int STT3=0;
        String sql="";
        String sql1="";
        String sql2 = "";
        PreparedStatement pst=null;
        PreparedStatement pst1=null;
        PreparedStatement pst2=null;
        ResultSet rs=null;
        ResultSet rs1=null;
        ResultSet rs2=null;

        try {

            sql="Select*from CachDung";
            sql1="Select*from DonViThuoc";
            sql2="Select*from LoaiBenh";

            pst=dbConnection.prepareStatement(sql);
            pst1=dbConnection.prepareStatement(sql1);
            pst2=dbConnection.prepareStatement(sql2);

            System.out.println("Prepared the SQL statement and set the parameters...");
            rs=pst.executeQuery();
            rs1=pst1.executeQuery();
            rs2=pst2.executeQuery();
            System.out.println("Executed the query...");
            while (rs.next())
            {
                String xoa = "Sửa";
                STT1++;

                cachdung_list.add(new CachDungtable(STT1,rs.getString("TenCachDung"),xoa));

            }
            while (rs1.next())
            {
                String xoa = "Sửa";
                STT2++;

                dvt_list.add(new DonViThuocTable(STT2,rs1.getString("TenDVTHuoc"),xoa  ));
            }
            while (rs2.next())
            {
                STT3++;
                String xoa = "Sửa";
                loaibenh_list.add(new LoaiBenhTable(STT3,rs2.getString("TenBenh"),xoa));
            }

        } catch (SQLException e) {
            System.out.println("An error occurred:");
            e.printStackTrace();
        }
    }
    public void refreshPage() {
        initialize(null, null);
        setupPaginated();
    }
    public void setupPaginated()
    {
        //Cach dung
        MFXTableColumn<CachDungtable> stt = new MFXTableColumn<>("STT",false, Comparator.comparing(CachDungtable::getSTT));
        MFXTableColumn<CachDungtable> ten_cachdung = new MFXTableColumn<>("Tên Cách Dùng",false, Comparator.comparing(CachDungtable::getTenCachDung));
        MFXTableColumn<CachDungtable> xoa = new MFXTableColumn<>("Sửa",false, Comparator.comparing(CachDungtable::getXoa));
        stt.setRowCellFactory(device-> new MFXTableRowCell<>(CachDungtable::getSTT));
        ten_cachdung.setRowCellFactory(device-> new MFXTableRowCell<>(CachDungtable::getTenCachDung));
        xoa.setRowCellFactory(device -> {
            MFXTableRowCell<CachDungtable, String> cell = new MFXTableRowCell<>(CachDungtable::getXoa);
            cell.setTextFill(Color.BLUE);
            cell.setCursor(Cursor.HAND);
            cell.setUnderline(true);

            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    if (role.equals("Quản lý")) {
                        CachDungtable rowData = tb_cd.getSelectionModel().getSelectedValues().get(0);
                        String thongtin = rowData.getTenCachDung();
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/Suacachdung.fxml"));
                            loader.setController(suacachdungController);
                            Scene scene = new Scene(loader.load());
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            AppUtils.setIcon(stage);
                            stage.show();
                            suacachdungController.xong.setOnMouseClicked(event1 -> {
                                Share.getInstance().setSharedVariable("8");
                                System.out.println(Share.getInstance().getSharedVariable());
                                suacachdungController.Validator();
                                if (suacachdungController.validationResult) {
                                    try {
                                        PreparedStatement pstmt = dbConnection.prepareStatement("UPDATE CachDung SET TenCachDung = ? WHERE TenCachDung = ?");
                                        pstmt.setString(1, suacachdungController.suathongtin.getText());
                                        pstmt.setString(2, thongtin);
                                        pstmt.executeUpdate();
                                        setData();
                                        tb_cd.setItems(cachdung_list);
                                        stage.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Cảnh báo");
                        alert.setHeaderText(null);
                        alert.setContentText("Ban không có quyền chỉnh sửa thông tin này");
                        alert.showAndWait();
                    }
                }
            });
            return cell;
        });

        tb_cd.getTableColumns().addAll(stt,ten_cachdung,xoa);
        tb_cd.setItems(cachdung_list);
        //don vi thuoc
        MFXTableColumn<DonViThuocTable> stt1 = new MFXTableColumn<>("STT",false, Comparator.comparing(DonViThuocTable::getSTT));
        MFXTableColumn<DonViThuocTable> ten_donvi = new MFXTableColumn<>("Tên Đơn Vị",false, Comparator.comparing(DonViThuocTable::getTenDonVi));
        MFXTableColumn<DonViThuocTable> xoa2 = new MFXTableColumn<>("Sửa",false, Comparator.comparing(DonViThuocTable::getXoa));
        stt1.setRowCellFactory(device-> new MFXTableRowCell<>(DonViThuocTable::getSTT));
        ten_donvi.setRowCellFactory(device-> new MFXTableRowCell<>(DonViThuocTable::getTenDonVi));
        xoa2.setRowCellFactory(device -> {
            MFXTableRowCell<DonViThuocTable, String> cell = new MFXTableRowCell<>(DonViThuocTable::getXoa);
            cell.setTextFill(Color.BLUE);
            cell.setCursor(Cursor.HAND);
            cell.setUnderline(true);
            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    if(role.equals("Quản lý"))
                    {
                        Share.getInstance().setSharedVariable("9");
                        DonViThuocTable rowData = tb_dvt.getSelectionModel().getSelectedValues().get(0);
                        String thongtin = rowData.getTenDonVi();
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/Suacachdung.fxml"));
                            loader.setController(suacachdungController);
                            Scene scene = new Scene(loader.load());
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            AppUtils.setIcon(stage);
                            stage.show();
                            suacachdungController.xong.setOnMouseClicked(event1 -> {
                                suacachdungController.Validator();
                                if (suacachdungController.validationResult) {
                                    try {
                                        PreparedStatement pstmt = dbConnection.prepareStatement("UPDATE DonViThuoc SET TenDVTHuoc = ? WHERE TenDVTHuoc = ?");
                                        pstmt.setString(1, suacachdungController.suathongtin.getText());
                                        pstmt.setString(2, thongtin);
                                        pstmt.executeUpdate();
                                        setData();
                                        tb_dvt.setItems(dvt_list);
                                        stage.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Cảnh báo");
                        alert.setHeaderText(null);
                        alert.setContentText("Ban không có quyền chỉnh sửa thông tin này");
                        alert.showAndWait();
                    }

                }
            });

            return cell;

        });
        tb_dvt.getTableColumns().addAll(stt1,ten_donvi,xoa2);
        tb_dvt.setItems(dvt_list);
        //loai benh
        MFXTableColumn<LoaiBenhTable> stt2 = new MFXTableColumn<>("STT",false, Comparator.comparing(LoaiBenhTable::getSTT));
        MFXTableColumn<LoaiBenhTable> ten_benh = new MFXTableColumn<>("Tên Bệnh",false, Comparator.comparing(LoaiBenhTable::getTenLoaiBenh));
        MFXTableColumn<LoaiBenhTable> xoa1 = new MFXTableColumn<>("Sửa",false, Comparator.comparing(LoaiBenhTable::getXoa));
        stt2.setRowCellFactory(device-> new MFXTableRowCell<>(LoaiBenhTable::getSTT));
        ten_benh.setRowCellFactory(device-> new MFXTableRowCell<>(LoaiBenhTable::getTenLoaiBenh));
        tb_benh.getTableColumns().addAll(stt2,ten_benh,xoa1);
        tb_benh.setItems(loaibenh_list);
        xoa1.setRowCellFactory(device -> {
            MFXTableRowCell<LoaiBenhTable, String> cell = new MFXTableRowCell<>(LoaiBenhTable::getXoa);
            cell.setTextFill(Color.BLUE);
            cell.setCursor(Cursor.HAND);
            cell.setUnderline(true);
            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    if(role.equals("Quản lý")) {
                        Share.getInstance().setSharedVariable("10");
                        LoaiBenhTable rowData = tb_benh.getSelectionModel().getSelectedValues().get(0);
                        String thongtin = rowData.getTenLoaiBenh();
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/Suacachdung.fxml"));
                            loader.setController(suacachdungController);
                            Scene scene = new Scene(loader.load());
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            AppUtils.setIcon(stage);
                            stage.show();
                            suacachdungController.xong.setOnMouseClicked(event1 -> {
                                suacachdungController.Validator();
                                if (suacachdungController.validationResult) {
                                    try {
                                        PreparedStatement pstmt = dbConnection.prepareStatement("UPDATE LoaiBenh SET TenBenh = ? WHERE TenBenh = ?");
                                        pstmt.setString(1, suacachdungController.suathongtin.getText());
                                        pstmt.setString(2, thongtin);
                                        pstmt.executeUpdate();
                                        setData();
                                        tb_benh.setItems(loaibenh_list);
                                        stage.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Cảnh báo");
                        alert.setHeaderText(null);
                        alert.setContentText("Ban không có quyền chỉnh sửa thông tin này");
                        alert.showAndWait();
                    }

                }
            });
            return cell;
        });
    }

}
