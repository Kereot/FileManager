package gui.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;

public class ClientHandlerNetty extends SimpleChannelInboundHandler {

    private ActionEvent actionEvent;

    public ClientHandlerNetty(ActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof String message) { // нужно реализовать с помощью коллекции через Stream API
            if (message.equals("/auth_ok")) {
                Platform.runLater(() -> {
                    Controller.buildMainScene(actionEvent);
                    return;
                });
            }
        }
        ctx.close();
    }
}
