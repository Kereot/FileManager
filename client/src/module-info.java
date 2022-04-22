module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;
    requires org.apache.commons.io;

    opens main.java.ru.gb.filemanager to javafx.fxml;
    exports main.java.ru.gb.filemanager;
}