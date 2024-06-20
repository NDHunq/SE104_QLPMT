package com.example.qlpmt;
import Model.Thuoc;
import com.example.qlpmt.KhamBenh.KhamBenh;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableRow;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public PreparedStatement pst=null;
    private ResultSet rs=null;
    private java.sql.Connection dbConnection = null;

    @FXML
   public MFXTextField search_txtbox;


    private ObservableList<KhoThuoc> KhoThuoc_list;
    public void initialize(URL location, ResourceBundle resources){
        search_txtbox.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
          setData();
            khothuoc.setItems(KhoThuoc_list);
//            khothuoc.setCurrentPage(0);

            // Your code here
        });
        dbConnection = DBConnection.getConnection();
        setupPaginated();
        double tableViewWidth = khothuoc.getPrefWidth();
        int numberOfColumns = khothuoc.getTableColumns().size();
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
int STT=0;
String sql="";
        String search = search_txtbox.getText();
        try {
            KhoThuoc_list = FXCollections.observableArrayList();
            if(search=="")
            {
                sql="Select*from Thuoc inner join DonViThuoc on Thuoc.DonViThuoc_ID=DonViThuoc.DVTHuoc_ID";
            }
            else
            {
                 sql="Select*from Thuoc inner join DonViThuoc on Thuoc.DonViThuoc_ID=DonViThuoc.DVTHuoc_ID Where TenThuoc like '%"+search+"%'";
            }

            pst=dbConnection.prepareStatement(sql);

            rs=pst.executeQuery();
            System.out.println("execute query");
            while (rs.next())
            {
                STT++;
                String img="Sửa";
                KhoThuoc thuoc=new KhoThuoc(STT,rs.getString("TenThuoc"),rs.getString("TenDVTHuoc"),rs.getInt("TonKho"),rs.getString("GiaBan"),img);
                thuoc.setId(rs.getString("Thuoc_ID"));
                thuoc.setDonViThuoc_ID(rs.getString("DonViTHuoc_ID"));
                KhoThuoc_list.add(thuoc);
            }
        }
        catch (Exception e) {
            System.out.println("An error occurred during data retrieval");
            e.printStackTrace();
        }


    }
    public void setupPaginated() {
        MFXTableColumn<KhoThuoc> stt = new MFXTableColumn<>("STT", false, Comparator.comparing(KhoThuoc::getStt));
        MFXTableColumn<KhoThuoc> tenthuoc = new MFXTableColumn<>("Tên Thuốc", false, Comparator.comparing(KhoThuoc::getTenthuoc));
        MFXTableColumn<KhoThuoc> don_vi = new MFXTableColumn<>("Đơn Vị", false, Comparator.comparing(KhoThuoc::getDonvi));
        MFXTableColumn<KhoThuoc> so_luong = new MFXTableColumn<>("Số Lượng", false, Comparator.comparing(KhoThuoc::getSoluong));
        MFXTableColumn<KhoThuoc> dongia = new MFXTableColumn<>("Đơn giá bán", false, Comparator.comparing(KhoThuoc::getDongia));
        MFXTableColumn<KhoThuoc> img = new MFXTableColumn<>("Chỉnh sửa", false, Comparator.comparing(KhoThuoc::getImgThuoc));


        stt.setRowCellFactory(khoThuoc -> new MFXTableRowCell<>(KhoThuoc::getStt));
        tenthuoc.setRowCellFactory(khoThuoc -> new MFXTableRowCell<>(KhoThuoc::getTenthuoc));
        don_vi.setRowCellFactory(khoThuoc -> new MFXTableRowCell<>(KhoThuoc::getDonvi));
        so_luong.setRowCellFactory(khoThuoc -> new MFXTableRowCell<>(KhoThuoc::getSoluong));
        dongia.setRowCellFactory(khoThuoc -> new MFXTableRowCell<>(KhoThuoc::getDongia));
        img.setRowCellFactory(khoThuoc -> {
            MFXTableRowCell<KhoThuoc, String> cell = new MFXTableRowCell<>(KhoThuoc::getImgThuoc);
            cell.setTextFill(Color.BLUE);
            cell.setCursor(Cursor.HAND);
            cell.setUnderline(true);

            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    KhoThuoc rowData=khothuoc.getSelectionModel().getSelectedValues().get(0);
                    System.out.println("Row data: "+rowData.getTenthuoc());
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
                        System.out.println("Ten:"+rowData.getTenthuoc());
                        controller.text_donvi.setText(rowData.getDonvi());
                        controller.text_soluong.setText(rowData.getSoluong().toString());
                        controller.text_dongia.setText(rowData.getDongia());
                        controller.Thuoc_ID = rowData.getId();
                        controller.DonViThuoc_ID = rowData.getDonViThuoc_ID();
                        controller.suathuoc.setOnMouseClicked(event1 -> {
                                String dvthuocid = "";
                            try {
//
                                String sql = "update Thuoc set TenThuoc=?,TonKho=?,GiaBan=? where Thuoc_ID=?";
                                pst = dbConnection.prepareStatement(sql);
                                pst.setString(1, controller.text_tenthuoc.getText());
                                pst.setString(2, controller.text_soluong.getText());
                                pst.setString(3, controller.text_dongia.getText());
                                pst.setString(4, rowData.getId());
                                int rowsAffected = pst.executeUpdate();
                                if (rowsAffected > 0) {
                                    System.out.println("The SQL statement was executed successfully.");
                                } else {
                                    System.out.println("The SQL statement did not affect any rows.");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
//
                                String sql = "select DVTHuoc_ID from DonViThuoc where TenDVTHuoc=?";
                                pst = dbConnection.prepareStatement(sql);
                                pst.setString(1, controller.text_donvi.getText());
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    dvthuocid = rs.getString("DVTHuoc_ID");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                String sql = "update Thuoc set DonViThuoc_ID=? where Thuoc_ID=?";
                                pst = dbConnection.prepareStatement(sql);
                                pst.setString(1, dvthuocid);
                                pst.setString(2, rowData.getId());
                                int rowsAffected = pst.executeUpdate();
                                if (rowsAffected > 0) {
                                    System.out.println("The SQL statement was executed successfully.");
                                } else {
                                    System.out.println("The SQL statement did not affect any rows.");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            setData();
                            khothuoc.setItems(KhoThuoc_list);
                            controller.suathuoc.getScene().getWindow().hide();
                            System.out.println("so luong:" + rowData.getSoluong());


                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return cell;
        });
        khothuoc.getTableColumns().addAll(stt, tenthuoc, don_vi, so_luong, dongia, img);
        khothuoc.getFilters().addAll(
                new StringFilter<>("Tên Thuốc", KhoThuoc::getTenthuoc),
                new StringFilter<>("Đơn vị", KhoThuoc::getDonvi),
                new IntegerFilter<>("Số lượng", KhoThuoc::getSoluong),
                new StringFilter<>("Đơn giá", KhoThuoc::getDongia)

        );
        setData();
        khothuoc.setItems(KhoThuoc_list);
//        khothuoc.setCurrentPage(0);
        Add.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/ThemThuoc.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
