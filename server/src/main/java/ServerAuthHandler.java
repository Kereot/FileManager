import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class ServerAuthHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(ServerMain.class.getName());

    private static final Path PATH = Paths.get("storage");
    private Path rootPath;
    private Path currentPath;

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        GenericRequest bb = (GenericRequest) msg;

        if (msg instanceof String message) { // нужно реализовать с помощью коллекции через Stream API, наверно
            if (message.startsWith("/auth")) {
                String[] token = message.split("@", 3); // это старая имплементация из чата
                if (token[1].equals("login") && token[2].equals("password")) { // сделать проверку по БД ещё надо, пока для тестов
                    Channel currentChannel = ctx.channel();
                    LOGGER.info("Client authenticated.");
                    rootPath = PATH.resolve(token[1]);
                    if (Files.notExists(rootPath)) {
                        Files.createDirectory(rootPath);
                    }
                    final String OK = "/auth_ok" + "@" + rootPath.toString();
                    currentChannel.writeAndFlush(OK);
                    LOGGER.info("Files list sent to client.");
                    return;
                }
            }
        }
        ctx.close();
    }
}
