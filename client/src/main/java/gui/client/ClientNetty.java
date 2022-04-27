package gui.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import javafx.event.ActionEvent;

public class ClientNetty {

    private static Channel channel;
    private static EventLoopGroup elg;

    public static boolean hasConnected() {
        return hasConnected;
    }

    private static boolean hasConnected;

    private static final int PORT = 45001;

    public static void connect(String authRequestBuilder, ActionEvent actionEvent) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.remoteAddress("localhost", PORT);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                socketChannel.pipeline().addLast(
                        new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                        new ObjectEncoder(),
                        new ClientHandlerNetty(actionEvent)
                );
            }
        });
        ChannelFuture channelFuture = bootstrap.connect().sync();
        channelFuture.channel().writeAndFlush(authRequestBuilder);
        channel = channelFuture.channel();
        elg = eventLoopGroup;
        hasConnected = true;

//        channelFuture.channel().closeFuture().sync();
    }

    public static void disconnect() {
        try {
            channel.close().sync();
            elg.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
