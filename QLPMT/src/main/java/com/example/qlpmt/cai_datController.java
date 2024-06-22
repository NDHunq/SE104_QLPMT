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
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Comparator;
import java.util.ResourceBundle;
import Model.CachDungtable;

public class cai_datController implements Initializable {
    @FXML
private Text chucvu;
    @FXML
    public Text hoten;
    @FXML
    public Text tienkham;
    @FXML
    public Text bntoida;
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
    private ImageView themcd;
    @FXML
    private ImageView themdvt;
    @FXML
    private ImageView thembenh;
    private java.sql.Connection dbConnection = null;
    private ObservableList<CachDungtable> cachdung_list;
    private ObservableList<DonViThuocTable> dvt_list;
    private ObservableList<LoaiBenhTable> loaibenh_list;
    public cai_datController() {
        dbConnection = DBConnection.getConnection();
        tb_cd = new MFXPaginatedTableView<CachDungtable>();
        tb_dvt = new MFXPaginatedTableView<>();
        tb_benh = new MFXPaginatedTableView<>();
    }

   public void initialize(URL location, ResourceBundle resources){


       dbConnection = DBConnection.getConnection();
         String sql0 = "SELECT * FROM TaiKhoan WHERE IdTT='TT01'";
       String sql = "SELECT * FROM ThongTinPK WHERE IdTT='TT01'";
       String sql1 = "SELECT * FROM ThongTinPK WHERE IdTT='TT02'";
       Statement statement = null;
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
               stage.show();
                controller.suathongtin.setText(hoten.getText());
               Share.getInstance().setSharedVariable("2");
               System.out.println(Share.getInstance().getSharedVariable());
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
       suatienkham.setOnMouseClicked(event -> {
           try {

               FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/suathongtin.fxml"));
               suathongtinController controller = new suathongtinController(this);
               loader.setController(controller);
               Scene scene = new Scene(loader.load());
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.show();
                controller.suathongtin.setText(tienkham.getText());
               Share.getInstance().setSharedVariable("3");
               System.out.println(Share.getInstance().getSharedVariable());
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
       suabntoida.setOnMouseClicked(event -> {
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/suathongtin.fxml"));
               suathongtinController controller = new suathongtinController(this);
               loader.setController(controller);
               Scene scene = new Scene(loader.load());
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.show();
               controller.suathongtin.setText(bntoida.getText());
               Share.getInstance().setSharedVariable("4");
               System.out.println(Share.getInstance().getSharedVariable());
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
         themcd.setOnMouseClicked(event -> {
              try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/themcachdung.fxml"));
                ThemcachdungController controller = new ThemcachdungController();
                loader.setController(controller);
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                  Share.getInstance().setSharedVariable("5");
                  System.out.println(Share.getInstance().getSharedVariable());

                  controller.huy.setOnMouseClicked(event1 -> {

                      stage.close();
                  });
              } catch (IOException e) {
                e.printStackTrace();
              }
         });
       themdvt.setOnMouseClicked(event -> {
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/themcachdung.fxml"));
               ThemcachdungController controller = new ThemcachdungController();
               loader.setController(controller);
               Scene scene = new Scene(loader.load());
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.show();
               Share.getInstance().setSharedVariable("6");
               System.out.println(Share.getInstance().getSharedVariable());

               controller.huy.setOnMouseClicked(event1 -> {

                   stage.close();
               });
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
       thembenh.setOnMouseClicked(event -> {
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/themcachdung.fxml"));
               ThemcachdungController controller = new ThemcachdungController();
               loader.setController(controller);
               Scene scene = new Scene(loader.load());
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.show();
               Share.getInstance().setSharedVariable("7");
               System.out.println(Share.getInstance().getSharedVariable());

               controller.huy.setOnMouseClicked(event1 -> {

                   stage.close();
               });
           } catch (IOException e) {
               e.printStackTrace();
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
                STT1++;
                System.out.println("Adding data to the list...");
                cachdung_list.add(new CachDungtable(STT1,rs.getString("TenCachDung")));

            }
            while (rs1.next())
            {
                STT2++;
                System.out.println("Adding data to the list...");
                dvt_list.add(new DonViThuocTable(STT2,rs1.getString("TenDVTHuoc")));
            }
            while (rs2.next())
            {
                STT3++;
                System.out.println("Adding data to the list...");
                loaibenh_list.add(new LoaiBenhTable(STT3,rs2.getString("TenBenh")));
            }

        } catch (SQLException e) {
            System.out.println("An error occurred:");
            e.printStackTrace();
        }
    }
    public void LoadData()
    {
            setData();
            setupPaginated();
    }
    public void setupPaginated()
    {
        //Cach dung
        MFXTableColumn<CachDungtable> stt = new MFXTableColumn<>("STT",false, Comparator.comparing(CachDungtable::getSTT));
        MFXTableColumn<CachDungtable> ten_cachdung = new MFXTableColumn<>("Tên Cách Dùng",false, Comparator.comparing(CachDungtable::getTenCachDung));
        stt.setRowCellFactory(device-> new MFXTableRowCell<>(CachDungtable::getSTT));
        ten_cachdung.setRowCellFactory(device-> new MFXTableRowCell<>(CachDungtable::getTenCachDung));
        tb_cd.getTableColumns().addAll(stt,ten_cachdung);
        tb_cd.setItems(cachdung_list);
        //don vi thuoc
        MFXTableColumn<DonViThuocTable> stt1 = new MFXTableColumn<>("STT",false, Comparator.comparing(DonViThuocTable::getSTT));
        MFXTableColumn<DonViThuocTable> ten_donvi = new MFXTableColumn<>("Tên Đơn Vị",false, Comparator.comparing(DonViThuocTable::getTenDonVi));
        stt1.setRowCellFactory(device-> new MFXTableRowCell<>(DonViThuocTable::getSTT));
        ten_donvi.setRowCellFactory(device-> new MFXTableRowCell<>(DonViThuocTable::getTenDonVi));
        tb_dvt.getTableColumns().addAll(stt1,ten_donvi);
        tb_dvt.setItems(dvt_list);
        //loai benh
        MFXTableColumn<LoaiBenhTable> stt2 = new MFXTableColumn<>("STT",false, Comparator.comparing(LoaiBenhTable::getSTT));
        MFXTableColumn<LoaiBenhTable> ten_benh = new MFXTableColumn<>("Tên Đơn Vị",false, Comparator.comparing(LoaiBenhTable::getTenLoaiBenh));
        stt2.setRowCellFactory(device-> new MFXTableRowCell<>(LoaiBenhTable::getSTT));
        ten_benh.setRowCellFactory(device-> new MFXTableRowCell<>(LoaiBenhTable::getTenLoaiBenh));
        tb_benh.getTableColumns().addAll(stt2,ten_benh);
        tb_benh.setItems(loaibenh_list);
    }
}
