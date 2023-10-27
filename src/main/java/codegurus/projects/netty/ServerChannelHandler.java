package codegurus.projects.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static codegurus.projects.netty.NettyUtils.channelId;


@ChannelHandler.Sharable
public class ServerChannelHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger LOG = LoggerFactory.getLogger(ServerChannelHandler.class);

    private static final ByteBuf HEARTBEAT = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.UTF_8));

    private Map<String, Integer> temp = new HashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("channelActive "+channelId(ctx));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String channelId = channelId(ctx);
        LOG.info("channelInactive "+channelId + " "+temp);
        temp.remove(channelId);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String input) throws Exception {
        String channelId = channelId(ctx);
        Integer integer = null;
        if (temp.containsKey(channelId)) {
            integer = temp.get(channelId);
        } else {
            integer = new Integer(0);
        }

        integer = integer+1;
        temp.put(channelId, integer);

        int index = input.indexOf("_");

        String _msg = null;
        if (index == -1) {
            _msg = input;
        } else {
            _msg = input.substring(0, index);
        }

        ChannelFuture channelFuture = ctx.channel().writeAndFlush(NettyUtils.appendNewLine(_msg+"_"+integer));
        if (integer % 100000 == 0) {
            LOG.info("channelRead0 "+input);
            channelFuture.addListener(ChannelFutureListener.CLOSE);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error(cause.getMessage(),cause);
        ctx.close();
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.writeAndFlush(HEARTBEAT.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } else {
            super.userEventTriggered(ctx,evt);
        }
    }
}
