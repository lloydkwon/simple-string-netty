package codegurus.projects.netty;

import io.netty.channel.ChannelHandlerContext;

public class NettyUtils {
    private NettyUtils(){}

    public static String appendNewLine(String msg) {
        return msg + '\n';
//        return msg + "\r\n";
//        return msg + Delimiters.lineDelimiter();
    }
    public static String channelId(ChannelHandlerContext ctx) {
        return ctx.channel().id().asShortText();
    }
}
