import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerAuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        GenericRequest bb = (GenericRequest) msg;

        if (msg instanceof String message) { // нужно реализовать с помощью коллекции через Stream API, наверно
            if (message.startsWith("/auth")) {
                String[] token = message.split("@", 3);
                if (token[1].equals("login") && token[2].equals("password")) {
                    Channel currentChannel = ctx.channel();
                    final String OK = "/auth_ok";
                    currentChannel.writeAndFlush(OK);
                    return;
                }
            }
        }
        ctx.close();
    }
}
