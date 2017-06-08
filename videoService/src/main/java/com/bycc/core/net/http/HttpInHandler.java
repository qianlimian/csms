package com.bycc.core.net.http;

import com.bycc.mgr.Dispatcher;
import com.bycc.utils.JsonHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpInHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInHandler.class);

    private Dispatcher dispatcher;

    public HttpInHandler() {
        this.dispatcher = new Dispatcher();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException, InterruptedException {
        if (msg instanceof FullHttpRequest) {
            dispatcher.route(ctx, (FullHttpRequest) msg); //HTTP请求分发
        }
    }

    public static void writeJSON(ChannelHandlerContext ctx, HttpResponseStatus status, Object msg) {
        String res = null;
        if (msg instanceof String) {
            res = (String) msg;
        } else {
            res = JsonHelper.getJson(msg);
        }
        writeJSON(ctx, status, Unpooled.copiedBuffer(res, CharsetUtil.UTF_8));
    }

    public static void writeJSON(ChannelHandlerContext ctx, Object msg) {
        String res = null;
        if (msg instanceof String) {
            res = (String) msg;
        } else {
            res = JsonHelper.getJson(msg);
        }
        writeJSON(ctx, HttpResponseStatus.OK, Unpooled.copiedBuffer(res, CharsetUtil.UTF_8));
    }

    private static void writeJSON(ChannelHandlerContext ctx, HttpResponseStatus status, ByteBuf content) {
        if (ctx.channel().isWritable()) {
            FullHttpResponse msg = null;
            if (content != null) {
                msg = new DefaultFullHttpResponse(HTTP_1_1, status, content);
                msg.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");
            } else {
                msg = new DefaultFullHttpResponse(HTTP_1_1, status);
            }
            if (msg.content() != null) {
                msg.headers().set(CONTENT_LENGTH, msg.content().readableBytes());
            }

//            if (HttpUtil.isKeepAlive(req)) {
//                res.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//                ctx.writeAndFlush(res);
//            } else {
//                ctx.writeAndFlush(res);
//                ctx.channel().writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
//            }

            // not keep-alive
            ctx.write(msg).addListener(ChannelFutureListener.CLOSE);
            ctx.flush();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
