package com.example.qlpmt;

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
import java.util.ResourceBundle;

public class cai_datController implements Initializable {
    @FXML
private Text chucvu;
    @FXML
    private Text hoten;
    @FXML
    private Text tienkham;
    @FXML
    private Text bntoida;
    @FXML
    public ImageView suachucvu;
    @FXML
    private ImageView suahoten;
    @FXML
    private ImageView suatienkham;
    @FXML
    private ImageView suabntoida;
   public void initialize(URL location, ResourceBundle resources){
       suachucvu.setOnMouseClicked(event -> {
           System.out.println("click");
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/suathongtin.fxml"));
               suathongtinController controller = new suathongtinController();
               loader.setController(controller);
               Scene scene = new Scene(loader.load());
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.show();
               controller.suathongtin.setText(chucvu.getText());
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
       suahoten.setOnMouseClicked(event -> {
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/suathongtin.fxml"));
               suathongtinController controller = new suathongtinController();
               loader.setController(controller);
               Scene scene = new Scene(loader.load());
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.show();
                controller.suathongtin.setText(hoten.getText());
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
       suatienkham.setOnMouseClicked(event -> {
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/suathongtin.fxml"));
               suathongtinController controller = new suathongtinController();
               loader.setController(controller);
               Scene scene = new Scene(loader.load());
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.show();
                controller.suathongtin.setText(tienkham.getText());
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
       suabntoida.setOnMouseClicked(event -> {
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/qlpmt/suathongtin.fxml"));
               suathongtinController controller = new suathongtinController();
               loader.setController(controller);
               Scene scene = new Scene(loader.load());
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.show();
                controller.suathongtin.setText(bntoida.getText());
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
   }
}
