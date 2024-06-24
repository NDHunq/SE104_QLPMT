package com.example.qlpmt;
import com.example.qlpmt.Share;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
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
import java.util.ResourceBundle;

public class cai_datController implements Initializable {
    @FXML
private Text chucvu;
    @FXML
    private Text hoten;
    @FXML
    public Text tienkham;
    @FXML
    public Text bntoida;
    @FXML
    public ImageView suachucvu;
    @FXML
    private ImageView suahoten;
    @FXML
    public ImageView suatienkham;
    @FXML
    private ImageView suabntoida;
    private java.sql.Connection dbConnection = null;
   public void initialize(URL location, ResourceBundle resources){
       dbConnection = DBConnection.getConnection();
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
       suachucvu.setOnMouseClicked(event -> {
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/suathongtin.fxml"));
               suathongtinController controller = new suathongtinController(this);
               loader.setController(controller);
               Scene scene = new Scene(loader.load());
               Stage stage = new Stage();
               stage.setScene(scene);
               AppUtils.setIcon(stage);
               stage.show();
               Share.getInstance().setSharedVariable("1");
               System.out.println(Share.getInstance().getSharedVariable());
               controller.suathongtin.setText(chucvu.getText());
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
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
               AppUtils.setIcon(stage);
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
               AppUtils.setIcon(stage);
               stage.show();
                controller.suathongtin.setText(bntoida.getText());
               Share.getInstance().setSharedVariable("4");
               System.out.println(Share.getInstance().getSharedVariable());
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
   }
}
