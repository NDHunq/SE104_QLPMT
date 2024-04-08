module com.example.qlpmt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.qlpmt to javafx.fxml;
    exports com.example.qlpmt;
    exports com.example.qlpmt.KhamBenh;
    opens com.example.qlpmt.KhamBenh to javafx.fxml;
}