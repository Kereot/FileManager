package gui.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.nio.file.Path;

public class ClientHandlerNetty extends SimpleChannelInboundHandler {

    private ActionEvent actionEvent;

    public ClientHandlerNetty(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof String message) { // нужно реализовать с помощью коллекции через Stream API
            if (message.startsWith("/auth_ok")) {
                String[] token = message.split("@", 2);
                Platform.runLater(() -> {
                    Controller.buildMainScene(actionEvent, Path.of(token[1]));
                });
            }
        }
        ctx.close();
    }
}
