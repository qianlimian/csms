package com.bycc.core.net.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpOutHandler extends ChannelOutboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInHandler.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);

        FullHttpResponse res = (FullHttpResponse) msg;
        logger.info("ip:{},write:{}", ctx.channel().remoteAddress(), res.content().toString(CharsetUtil.UTF_8));
    }
}
