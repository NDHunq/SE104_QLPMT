package com.example.qlpmt.KhamBenh;

import com.example.qlpmt.AppUtils;
import com.example.qlpmt.DBConnection;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class HoaDonController implements Initializable {

    @FXML
    private MFXTextField HoTenTxt;

    @FXML
    private MFXButton InBtn;

    @FXML
    private MFXDatePicker NgayKhamPicker;

    @FXML
    private MFXTextField SDTTxt;

    @FXML
    private Label TienKhamLbl;

    @FXML
    private Label TienThuocLbl;

    @FXML
    private Label TongCongLbl;

    @FXML
    private ImageView close;

    @FXML
    private MFXTableView<ThuocHD> table_thuoc;
    private ObservableList<ThuocHD> list;
    Connection connection = null;
    PreparedStatement prs = null;
    String sql = null;
    String Pkb_id = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = DBConnection.getConnection();
        close.setOnMouseClicked(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        InBtn.setOnAction((ActionEvent event) -> {
            exportToPDF();
            showAlert();
        });


    }
    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Hóa đơn đã được lưu vào thư mục Downloads");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        InputStream iconStream = AppUtils.class.getResourceAsStream("/com/example/qlpmt/images/cong.png");
        Image image = new Image(iconStream);
        alertStage.getIcons().add(image);

        alert.showAndWait();
    }
    public void InitData(String Pkb_id,String DSKB_id)
    {
        try {
            // Query to get HoTen, SDT, NgayKham from DSKB
            String sql1 = "SELECT HoTen, CCCD, NgayKham FROM DSKB WHERE DSKB_ID = ?";
            PreparedStatement pst1 = connection.prepareStatement(sql1);
            pst1.setString(1, DSKB_id);
            ResultSet rs1 = pst1.executeQuery();
            if (rs1.next()) {
                HoTenTxt.setText(rs1.getString("HoTen"));
                SDTTxt.setText(rs1.getString("CCCD"));
                NgayKhamPicker.setValue(rs1.getDate("NgayKham").toLocalDate());
            }

            // Query to get GiaTri from TTphongkham where TenTT is 'Tiền khám'
            String sql2 = "SELECT Gtri FROM ThongTinPK WHERE TenTT = 'Tiền khám'";
            PreparedStatement pst2 = connection.prepareStatement(sql2);
            ResultSet rs2 = pst2.executeQuery();
            if (rs2.next()) {
                TienKhamLbl.setText("Tiền khám: "+rs2.getInt("Gtri"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        setupPaginated(Pkb_id);
    }
    private void setupPaginated(String Pkb_id) {

        //Tao cac cot cua tableview
        MFXTableColumn<ThuocHD> stt = new MFXTableColumn<>("STT", false, Comparator.comparing(ThuocHD::getStt));
        MFXTableColumn<ThuocHD> tenthuoc = new MFXTableColumn<>("Thuốc", false, Comparator.comparing(ThuocHD::getTenThuoc));
        MFXTableColumn<ThuocHD> donvi = new MFXTableColumn<>("Đơn vị", false, Comparator.comparing(ThuocHD::getDonVi));
        MFXTableColumn<ThuocHD> soluong = new MFXTableColumn<>("Số lượng", false, Comparator.comparing(ThuocHD::getSoLuong));
        MFXTableColumn<ThuocHD> dongia = new MFXTableColumn<>("Đơn giá", false, Comparator.comparing(ThuocHD::getDonGia));
        MFXTableColumn<ThuocHD> thanhtien = new MFXTableColumn<>("Thành tiền", false, Comparator.comparing(ThuocHD::getThanhTien));


        stt.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocHD::getStt));
        tenthuoc.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocHD::getTenThuoc));
        donvi.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocHD::getDonVi));
        soluong.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocHD::getSoLuong));
        dongia.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocHD::getDonGia));
        thanhtien.setRowCellFactory(khambenh -> new MFXTableRowCell<>(ThuocHD::getThanhTien));

        //Them cac cot vao tableview
        table_thuoc.getTableColumns().addAll(stt, tenthuoc, donvi, soluong, dongia, thanhtien);

        //Them cac filter cho tableview
        table_thuoc.getFilters().addAll(
                //new StringFilter<>("STT", (ThuocPKB::getStt) ),
                new StringFilter<>("Thuốc", ThuocHD::getTenThuoc),
                new StringFilter<>("Đơn vị", ThuocHD::getDonVi),
                new StringFilter<>("Đơn giá", ThuocHD::getDonGia),
                new StringFilter<>("Thành tiền", ThuocHD::getThanhTien)

        );

        //Them du lieu vao tableview
        setData( Pkb_id);
        this.Pkb_id = Pkb_id;
        table_thuoc.setItems(list);
        table_thuoc.autosizeColumnsOnInitialization();
        table_thuoc.getTableColumns().get(4).setPrefWidth(200);

    }
    public void setData(String Pkb_id){
        list= FXCollections.observableArrayList();
        try {
            String sql = "SELECT thuoc.TenThuoc, donvithuoc.TenDVThuoc, DSThuoc_pkb.SoLuong, thuoc.GiaBan FROM DSThuoc_pkb JOIN thuoc ON DSThuoc_pkb.Thuoc_ID = thuoc.Thuoc_ID JOIN donvithuoc ON thuoc.DonViThuoc_ID = donvithuoc.DVThuoc_ID WHERE DSThuoc_pkb.PKB_ID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, Pkb_id);
            ResultSet rs = pst.executeQuery();
            int stt = 1;
            while (rs.next()) {
                String tenThuoc = rs.getString("TenThuoc");
                String donVi = rs.getString("TenDVThuoc");
                int soLuong = rs.getInt("SoLuong");
                String donGia = Integer.toString(rs.getBigDecimal("GiaBan").intValueExact());
                String thanhTien = Integer.toString(soLuong * Integer.parseInt(donGia));
                list.add(new ThuocHD(stt, tenThuoc, donVi, soLuong, donGia, thanhTien));
                stt++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int tienThuoc = 0;
        for (ThuocHD thuoc : list) {
            tienThuoc += Integer.parseInt(thuoc.getThanhTien());
        }
        TienThuocLbl.setText("Tiền thuốc: " + tienThuoc);
        int TongCong = tienThuoc + Integer.parseInt(TienKhamLbl.getText().split(": ")[1]);
        TongCongLbl.setText("Tổng cộng: " + TongCong);
    }
    public void exportToPDF() {
        Document document = new Document();
        try {
            String home = System.getProperty("user.home");
            PdfWriter.getInstance(document, new FileOutputStream(home + "/Downloads/HoaDon_" + Pkb_id + ".pdf"));
            document.open();
            document.add(new Paragraph("Ho ten: " + removeAccent(HoTenTxt.getText())));
            document.add(new Paragraph("CCCD: " + SDTTxt.getText()));
            document.add(new Paragraph("Ngay kham: " + NgayKhamPicker.getValue().toString()));
            document.add(new Paragraph("Chi tiet thuoc:"));

            // Create a table with 6 columns
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100); // Full Width
            table.setSpacingBefore(10f); // Space before table
            table.setSpacingAfter(10f); // Space after table

            // Add column headers
            Stream.of("STT", "Ten thuoc", "Don vi", "So luong", "Don gia", "Thanh tien")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(columnTitle));
                        table.addCell(header);
                    });

            // Add row data
            for (ThuocHD thuoc : list) {
                table.addCell(String.valueOf(thuoc.getStt()));
                table.addCell(thuoc.getTenThuoc());
                table.addCell(thuoc.getDonVi());
                table.addCell(String.valueOf(thuoc.getSoLuong()));
                table.addCell(thuoc.getDonGia());
                table.addCell(thuoc.getThanhTien());
            }

            document.add(table);

            document.add(new Paragraph(removeAccent(TienKhamLbl.getText())));
            document.add(new Paragraph(removeAccent(TienThuocLbl.getText())));
            document.add(new Paragraph(removeAccent(TongCongLbl.getText())));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
    public String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
