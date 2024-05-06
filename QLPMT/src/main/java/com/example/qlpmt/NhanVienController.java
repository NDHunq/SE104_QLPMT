package com.example.qlpmt;

import Model.NhanVien;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class NhanVienController implements Initializable {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
    @FXML
    private Text soNhanVien;
    @FXML
    private AnchorPane nhanvien_root;
    @FXML
    private MFXTextField search_txtbox;
    @FXML
    private MFXPaginatedTableView<NhanVien> pkb;
    @FXML
    private Button add;
    private ObservableList<NhanVien> pkb_list;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        setupPaginated();
        setupContextMenu();
        //chia deu kich thuoc cac cot de vua voi chieu rong cua tableview
        double tableViewWidth = pkb.getPrefWidth();
        int numberOfColumns = pkb.getTableColumns().size();
        for (MFXTableColumn column : pkb.getTableColumns()) {
            column.setPrefWidth(tableViewWidth / numberOfColumns);
        }

        ////Tu dong dieu chinh kich thuoc cac cot de phu hop voi noi dung khi vua khoi tao
        //pkb.autosizeColumnsOnInitialization();

        //Tu dong dieu chinh kich thuoc cac cot de phu hop voi noi dung khi co thay doi
        When.onChanged(pkb.currentPageProperty())
                .then((oldValue, newValue) -> pkb.autosizeColumns())
                .listen();

        // Them su kien unfocus cho search_txtbox khi click ra ngoai bang cach requestFocus den node khac
        nhanvien_root.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent mouseEvent) {
                // Kiem tra xem search_txtbox co dang duoc focus hay khong
                if (mouseEvent.getTarget() != search_txtbox) {
                    // Neu khong thi unfocus search_txtbox
                    nhanvien_root.requestFocus();
                }
            }
        });

        // Cap nhat so luong benh nhan luc khoi tao
        soNhanVien.setText(String.valueOf(pkb_list.size()));
        add.setOnAction(event -> {
            try {
                // Load the FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/signin.fxml"));

                // Create a new stage and set the scene
                Stage stage = new Stage(StageStyle.UNDECORATED);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);

                // Make the window draggable
                final double[] xOffset = new double[1];
                final double[] yOffset = new double[1];
                root.setOnMousePressed(mouseEvent -> {
                    xOffset[0] = mouseEvent.getSceneX();
                    yOffset[0] = mouseEvent.getSceneY();
                });
                root.setOnMouseDragged(mouseEvent -> {
                    stage.setX(mouseEvent.getScreenX() - xOffset[0]);
                    stage.setY(mouseEvent.getScreenY() - yOffset[0]);
                });

                // Set the title of the window
                stage.setTitle("Sign In");

                // Show the stage
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setupPaginated () {

        //Tao cac cot cua tableview

        MFXTableColumn<NhanVien> hoten = new MFXTableColumn<>("Ho Ten", false, Comparator.comparing(NhanVien::getHoten));
        MFXTableColumn<NhanVien> chucvu = new MFXTableColumn<>("Chuc vu", false, Comparator.comparing(NhanVien::getChucvu));
        MFXTableColumn<NhanVien> username = new MFXTableColumn<>("Username", false, Comparator.comparing(NhanVien::getUsername));
        MFXTableColumn<NhanVien> email = new MFXTableColumn<>("Email", false, Comparator.comparing(NhanVien::getEmail));

        //Tao cac dong cho cot cua tableview

        hoten.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(NhanVien::getHoten));
        chucvu.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(NhanVien::getChucvu));
        username.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(NhanVien::getUsername));
        email.setRowCellFactory(phieukhambenh -> new MFXTableRowCell<>(NhanVien::getEmail));

        //Them cac cot vao tableview
        pkb.getTableColumns().addAll(hoten, chucvu, username, email);

        //Them cac filter cho tableview
        pkb.getFilters().addAll(
                new StringFilter<>("Ho ten", NhanVien::getHoten),
                new StringFilter<>("Chuc vu", NhanVien::getChucvu),
                new StringFilter<>("Username", NhanVien::getUsername),
                new StringFilter<>("Email", NhanVien::getEmail)
        );

        //Them du lieu vao tableview
        setData();
        pkb.setItems(pkb_list);
    }

    public void setData () {
        pkb_list = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnectionQuyen.getConnection();
            String sql = "SELECT * FROM TaiKhoan";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                NhanVien nhanvien = new NhanVien(rs.getString("HoTen"), rs.getString("ChucVu"), rs.getString("username"), rs.getString("Email"));
                pkb_list.add(nhanvien);
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void setupContextMenu(){
        MFXContextMenu contextMenu = new MFXContextMenu(pkb);
        MFXContextMenuItem edit = new MFXContextMenuItem("Chỉnh sửa");
        MFXContextMenuItem delete = new MFXContextMenuItem("Xóa");
        contextMenu.getItems().addAll(edit, delete);
        edit.setStyle("-fx-text-fill: #2264D1; -fx-font-size: 16px; -fx-font-family: 'Times New Roman'");
        delete.setStyle("-fx-text-fill: red; -fx-font-size: 16px; -fx-font-family: 'Times New Roman'");

        delete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {


            }
        });

        pkb.setTableRowFactory(nhanvien -> {
            MFXTableRow<NhanVien> row = new MFXTableRow<>(pkb, new NhanVien("","","",""));
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

    private void deleteNhanVienFromDatabase(NhanVien nhanVien) {
        try {
            Connection connection = DBConnectionQuyen.getConnection();
            String sql = "DELETE FROM TaiKhoan WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nhanVien.getUsername());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
