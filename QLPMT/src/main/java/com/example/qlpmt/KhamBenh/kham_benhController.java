package com.example.qlpmt.KhamBenh;

import com.example.qlpmt.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class kham_benhController implements Initializable {
    @FXML
    private VBox kb_layout;
    @FXML
    private ComboBox<String> sort_cbb;

    @FXML
    private Button add_butt;
    double x,y=0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sort_cbb.getItems().add("Name: A-Z");
        sort_cbb.getItems().add("Name: Z-A");
        List<KhamBenh> ls=new ArrayList<>(lst_kb());
        for (int i=0;i<ls.size();i++){
            String color;
            if(i%2==0){
                color="-fx-background-color: #EEEEEE;";
            }else{
                color="-fx-background-color: #FFFFFF;";
            }
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("kham_benh_item.fxml"));
            try {

                HBox hBox=loader.load();
                KhamBenhItemController item=loader.getController();
                item.SetData(ls.get(i),color);
                kb_layout.getChildren().add(hBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public List<KhamBenh> lst_kb(){
        List<KhamBenh> ls=new ArrayList<>();
        ls.add(new KhamBenh("001","Nguyễn Văn A","084204011380","Nam","2004","Hà Nội","/com/example/qlpmt/images/plus.png"));
        ls.add(new KhamBenh("002","Nguyễn Văn B","084204011381","Nam","2004","Hà Nội","/com/example/qlpmt/images/eye.png"));
        ls.add(new KhamBenh("003","Nguyễn Văn C","084204011382","Nam","2004","Hà Nội","/com/example/qlpmt/images/eye.png"));
        ls.add(new KhamBenh("004","Nguyễn Văn D","084204011383","Nam","2004","Hồ Chí Minh","/com/example/qlpmt/images/eye.png"));
        ls.add(new KhamBenh("005","Nguyễn Văn E","084204011384","Nữ","2004","Hồ Chí Minh","/com/example/qlpmt/images/plus.png"));
        ls.add(new KhamBenh("006","Nguyễn Văn F","084204011385","Nam","2004","Hồ Chí Minh","/com/example/qlpmt/images/eye.png"));
        ls.add(new KhamBenh("007","Nguyễn Văn G","084204011386","Nam","2004","Hà Nội","/com/example/qlpmt/images/eye.png"));
        ls.add(new KhamBenh("008","Nguyễn Văn H","084204011387","Nam","2004","Hà Nội","/com/example/qlpmt/images/plus.png"));
        ls.add(new KhamBenh("009","Nguyễn Văn I","084204011388","Nam","2004","Hà Nội","/com/example/qlpmt/images/eye.png"));
        ls.add(new KhamBenh("010","Nguyễn Văn K","084204011389","Nam","2004","Hà Nội","/com/example/qlpmt/images/plus.png"));
        ls.add(new KhamBenh("011","Nguyễn Văn L","084204011390","Nam","2004","Hà Nội","/com/example/qlpmt/images/eye.png"));
        ls.add(new KhamBenh("012","Nguyễn Văn M","084204011391","Nam","2004","Hà Nội","/com/example/qlpmt/images/eye.png"));
        ls.add(new KhamBenh("013","Nguyễn Văn N","084204011392","Nam","2004","Hà Nội","/com/example/qlpmt/images/plus.png"));
        System.out.println(ls.size());
        return ls;
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
        Scene scene = new Scene(root, 400, 460);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
