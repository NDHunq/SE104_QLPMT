package com.example.qlpmt;

import Model.DoanhThu;
import Model.PhieuKhamBenh;
import Model.TongDoanhThu;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.FloatFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

public class doanh_thuController implements Initializable {

    private boolean isBangSoLieu = true;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);

    //Tao cac truc toa do cho bieu do
    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();

    private ObservableList<String> month_List = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
    private ObservableList<String> year_List = FXCollections.observableArrayList();

    @FXML
    private MFXComboBox<String> month_combobox;

    @FXML
    private MFXComboBox<String> year_combobox;

    @FXML
    private MFXComboBox<String> chart_month_combobox;

    @FXML
    private MFXComboBox<String> chart_year_combobox;

    @FXML
    private AnchorPane doanh_thu_root_node;

    @FXML
    private Pane line_chart_pane;

    @FXML
    private MFXButton bangSoLieu;

    @FXML
    private MFXButton bieuDo;

    @FXML
    private MFXPaginatedTableView<DoanhThu> doanhThu;

    @FXML
    private MFXTextField search_txtbox;

    @FXML
    private LineChart<String, Number> doanhThu_line_chart;

    private ObservableList<DoanhThu> doanhThuList = FXCollections.observableArrayList();
    private ObservableList<DoanhThu> chart_doanhThuList = FXCollections.observableArrayList();
    private ObservableList<TongDoanhThu> tongDoanhThuList = FXCollections.observableArrayList();

    //Ham khoi tao
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButton();
        setVisible();
        buttonClickEvent();
        setComboBox();
        setupChart(Integer.parseInt(month_combobox.getText()), Integer.parseInt(year_combobox.getText()));

        //Khoi tao paginated tableview
        setupPaginated();

        //chia deu kich thuoc cac cot de vua voi chieu rong cua tableview
        double tableViewWidth = doanhThu.getPrefWidth();
        int numberOfColumns = doanhThu.getTableColumns().size();
        for (MFXTableColumn column : doanhThu.getTableColumns()) {
            column.setPrefWidth(tableViewWidth / numberOfColumns);
        }

        ////Tu dong dieu chinh kich thuoc cac cot de phu hop voi noi dung khi vua khoi tao
        //pkb.autosizeColumnsOnInitialization();

        //Tu dong dieu chinh kich thuoc cac cot de phu hop voi noi dung khi co thay doi
        When.onChanged(doanhThu.currentPageProperty())
                .then((oldValue, newValue) -> doanhThu.autosizeColumns())
                .listen();

        // Them su kien unfocus cho search_txtbox khi click ra ngoai bang cach requestFocus den node khac
        doanh_thu_root_node.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Kiem tra xem search_txtbox co dang duoc focus hay khong
                if (mouseEvent.getTarget() != search_txtbox) {
                    // Neu khong thi unfocus search_txtbox
                    doanh_thu_root_node.requestFocus();
                }
            }
        });

        month_combobox.setOnAction(event -> {
            int month = Integer.parseInt(month_combobox.getSelectionModel().getSelectedItem());
            reloadTableView(month, Integer.parseInt(year_combobox.getText()));
        });

        year_combobox.setOnAction(event -> {
            int year = Integer.parseInt(year_combobox.getSelectionModel().getSelectedItem());
            reloadTableView(Integer.parseInt(month_combobox.getText()), year);
        });

        chart_month_combobox.setOnAction(event -> {
            int month = Integer.parseInt(chart_month_combobox.getSelectionModel().getSelectedItem());
            reloadChart(month, Integer.parseInt(chart_year_combobox.getText()));
        });

        chart_year_combobox.setOnAction(event -> {
            int year = Integer.parseInt(chart_year_combobox.getSelectionModel().getSelectedItem());
            reloadChart(Integer.parseInt(chart_month_combobox.getText()), year);
        });
    }

    //Doi mau button khi chon giua cac tab
    public void setButton() {
        if(isBangSoLieu){
            bangSoLieu.setStyle("-fx-background-color: #2264D1; -fx-text-fill: #FFFFFF");
            bieuDo.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2264d1");
        } else {
            bieuDo.setStyle("-fx-background-color: #2264D1; -fx-text-fill: #FFFFFF");
            bangSoLieu.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #2264d1");
        }
    }

    //Xu ly su kien khi click button
    public void buttonClickEvent(){
        bangSoLieu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!isBangSoLieu){
                    isBangSoLieu = !isBangSoLieu;
                    setButton();
                    setVisible();
                }
            }
        });

        bieuDo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(isBangSoLieu){
                    isBangSoLieu = !isBangSoLieu;
                    setButton();
                    setVisible();
                }
            }
        });
    }

    //Hien thi hoac an cac node cua tung tab
    public void setVisible(){
        if(isBangSoLieu){
            doanhThu.setVisible(true);
            search_txtbox.setVisible(true);
            doanhThu_line_chart.setVisible(false);
            line_chart_pane.setVisible(false);
            month_combobox.setVisible(true);
            year_combobox.setVisible(true);
            chart_month_combobox.setVisible(false);
            chart_year_combobox.setVisible(false);
        } else {
            doanhThu.setVisible(false);
            search_txtbox.setVisible(false);
            doanhThu_line_chart.setVisible(true);
            line_chart_pane.setVisible(true);
            month_combobox.setVisible(false);
            year_combobox.setVisible(false);
            chart_month_combobox.setVisible(true);
            chart_year_combobox.setVisible(true);
        }
    }

    public void setupPaginated(){
        //Tao cac cot cua tableview
        MFXTableColumn<DoanhThu> stt = new MFXTableColumn<>("STT", false, Comparator.comparing(DoanhThu::getId));
        MFXTableColumn<DoanhThu> ngaykham = new MFXTableColumn<>("Ngày khám", false, Comparator.comparing(DoanhThu::getNgayKham));
        MFXTableColumn<DoanhThu> doanhthu = new MFXTableColumn<>("Doanh thu", false, Comparator.comparing(DoanhThu::getDoanhThu));
        MFXTableColumn<DoanhThu> soluong = new MFXTableColumn<>("Số lượng bệnh nhân", false, Comparator.comparing(DoanhThu::getSoLuongBenhNhan));
        MFXTableColumn<DoanhThu> tile = new MFXTableColumn<>("Tỉ lệ", false, Comparator.comparing(DoanhThu::getTiLe));

        //Tao cac dong cho cot cua tableview
        stt.setRowCellFactory(doanh_thu -> new MFXTableRowCell<>(DoanhThu::getId));
        ngaykham.setRowCellFactory(doanh_thu -> new MFXTableRowCell<>(DoanhThu::getNgayKham_string));
        doanhthu.setRowCellFactory(doanh_thu -> new MFXTableRowCell<>(DoanhThu::getDoanhThu));
        soluong.setRowCellFactory(doanh_thu -> new MFXTableRowCell<>(DoanhThu::getSoLuongBenhNhan));
        tile.setRowCellFactory(doanh_thu -> new MFXTableRowCell<>(DoanhThu::getTiLe));

        //Them cac cot vao tableview
        doanhThu.getTableColumns().addAll(stt, ngaykham, doanhthu, soluong, tile);

        //Them cac filter cho tableview
        doanhThu.getFilters().addAll(
                new IntegerFilter<>("STT", DoanhThu::getId),
                new StringFilter<>("Ngày khám", DoanhThu::getNgayKham_string),
                new StringFilter<>("Doanh thu", DoanhThu::getDoanhThu),
                new IntegerFilter<>("Số lượng bệnh nhân", DoanhThu::getSoLuongBenhNhan),
                new FloatFilter<>("Tỉ lệ", DoanhThu::getTiLe)
        );

        //Them du lieu vao tableview
        LoadTableView(Integer.parseInt(month_combobox.getText()), Integer.parseInt(year_combobox.getText()));
        doanhThu.setItems(doanhThuList);
    }

    public void setupChart(int month, int year){
//        try{
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        xAxis.setLabel("Thời gian (Ngày)");
        yAxis.setLabel("Doanh thu (VND)");

        doanhThu_line_chart = new LineChart<String, Number>(xAxis, yAxis);
        line_chart_pane.getChildren().add(doanhThu_line_chart);
        doanhThu_line_chart.setPrefSize(883.0, 419.0);
        doanhThu_line_chart.setLayoutY(15.0);

        doanhThu_line_chart.setTitle("Biểu đồ doanh thu Tháng " + month + "/" + year);

        XYChart.Series<String, Number> series= new XYChart.Series<String, Number>();
        series.setName("Doanh thu");

        try{
            String query = "SELECT * FROM DoanhThu WHERE MONTH(NgayDT) = ? AND YEAR(NgayDT) = ?";
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                chart_doanhThuList.add(new DoanhThu(1, rs.getDate(1).toLocalDate(), rs.getInt(2), rs.getString(3), 1));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        try{
            int count = 0;
            for (int i = 1; i <= 31; i++){
                if(count < chart_doanhThuList.size()){
                    if(chart_doanhThuList.get(count).getNgayKham().getDayOfMonth() == i){
                        series.getData().add(new XYChart.Data<String, Number>(Integer.toString(i), Double.parseDouble(chart_doanhThuList.get(count).getDoanhThu())));
                        count++;
                    }
                    else
                        series.getData().add(new XYChart.Data<String, Number>(Integer.toString(i), 0));
                }
                else
                    series.getData().add(new XYChart.Data<String, Number>(Integer.toString(i), 0));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        doanhThu_line_chart.getData().add(series);
    }

    public void setComboBox(){
        try{
            String query = "SELECT DISTINCT YEAR(NgayDT) AS Nam FROM DoanhThu";
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                year_List.add(rs.getString(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        month_combobox.setItems(month_List);
        month_combobox.setText("3");

        year_combobox.setText(Integer.toString(currentYear));
        year_combobox.setItems(year_List);

        chart_month_combobox.setItems(month_List);
        chart_month_combobox.setText("3");

        chart_year_combobox.setText(Integer.toString(currentYear));
        chart_year_combobox.setItems(year_List);
    }

    public void LoadTableView(int month, int year){
        //Tinh tong doanh thu theo thang va nam
        try{
            String query = " SELECT MONTH(NgayDT) AS Thang, YEAR(NgayDT) AS Nam, SUM(DoanhThu) AS TongDT  \n" +
                    " FROM DoanhThu\n" +
                    " GROUP BY MONTH(NgayDT), YEAR(NgayDT)";
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                tongDoanhThuList.add(new TongDoanhThu(rs.getInt("Thang"), rs.getInt("Nam"), rs.getString("TongDT")));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        try{
            String query = "SELECT * FROM DoanhThu WHERE MONTH(NgayDT) = ? AND YEAR(NgayDT) = ?";
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();
            int id = 1;
            while (rs.next()){
                float tiLe = 0;

                //Tinh ti le doanh thu cua tung ngay do so voi tong doanh thu cua thang do
                for(TongDoanhThu tongDoanhThu : tongDoanhThuList){
                    if(tongDoanhThu.getThang() == month && tongDoanhThu.getNam() == year){
                        tiLe = Math.round((float) (rs.getDouble("DoanhThu") / Double.parseDouble(tongDoanhThu.getTongDoanhThu())  * 100) * 100.0f) / 100.0f ;}
                }
                doanhThuList.add(new DoanhThu(id, rs.getDate(1).toLocalDate(), rs.getInt(2), rs.getString(3), tiLe));
                id++;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void reloadTableView(int month, int year){
        doanhThuList.clear();
        tongDoanhThuList.clear();
        LoadTableView(month, year);
        doanhThu.setItems(doanhThuList);
        doanhThu.setCurrentPage(0);
    }

    public void reloadChart(int month, int year){
        chart_doanhThuList.clear();
        line_chart_pane.getChildren().clear();
        setupChart(month, year);
    }
}
