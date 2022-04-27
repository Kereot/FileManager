module gui.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires io.netty.transport;
    requires org.apache.commons.io;
    requires io.netty.codec;

    opens gui.client to javafx.fxml;
    exports gui.client;
}