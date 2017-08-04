package com.bycc.core.net.http;

import com.bycc.service.LocateServiceImpl;
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
import java.util.List;
import java.util.Map;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpHandler.class);

    private static HttpHeaders headers;
    private static HttpRequest request;
    private static FullHttpResponse fullResponse;
    private static FullHttpRequest fullRequest;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            try {
                request = (HttpRequest) msg;
                headers = request.headers();

                String uri = request.uri();
                if(uri.equals("/favicon.ico")){
                    return;
                }
                HttpMethod method = request.method();
                if (method.equals(HttpMethod.GET)) {
                	if (uri.equals("/locate/get")) {
						LocateServiceImpl service = new LocateServiceImpl();
						writeResponse(ctx, HttpResponseStatus.OK, service.get());
					} else if(uri.equals("/locate/alarm")) {
						LocateServiceImpl service = new LocateServiceImpl();
						writeResponse(ctx, HttpResponseStatus.OK, service.alarm());
					}
                } else if (method.equals(HttpMethod.POST)) {
                    //POST请求，由于你需要从消息体中获取数据，因此有必要把msg转换成FullHttpRequest
                    //fullRequest = (FullHttpRequest) msg;

                    //根据不同的 Content_Type 处理 body 数据
                    //dealWithContentType();

                } else {
                    //其他类型在此不做处理，需要的话可自己扩展
                }
            } catch (Exception e) {
            	logger.error(e.getMessage());
                writeResponse(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR, "ERROR");
            }
        }

    }

    /**
     * 简单处理常用几种 Content-Type 的 POST 内容（可自行扩展）
     */
    private void dealWithContentType() throws Exception {
        String typeStr = headers.get("Content-Type").toString();
        String[] list = typeStr.split(";");
        String contentType = list[0];

        if(contentType.equals("application/json")){
            String content = fullRequest.content().toString(CharsetUtil.UTF_8);
            System.out.println(JsonHelper.getJson(content));

        }else if(contentType.equals("application/x-www-form-urlencoded")){
			String content = fullRequest.content().toString(CharsetUtil.UTF_8);
			QueryStringDecoder queryDecoder = new QueryStringDecoder(content, false);
			Map<String, List<String>> uriAttributes = queryDecoder.parameters();
            for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
                for (String val : attr.getValue()) {
                    System.out.println(attr.getKey() + "=" + val);
                }
            }

        }else if(contentType.equals("multipart/form-data")){  //用于文件上传

        }else{

        }
    }

    public static void writeResponse(ChannelHandlerContext ctx, HttpResponseStatus status, Object msg) {
        String res;
        if (msg instanceof String) {
            res = (String) msg;
        } else {
            res = JsonHelper.getJson(msg);
        }
        writeJSON(ctx, status, Unpooled.copiedBuffer(res, CharsetUtil.UTF_8));
    }

    public static void writeResponse(ChannelHandlerContext ctx, Object msg) {
        String res;
        if (msg instanceof String) {
            res = (String) msg;
        } else {
            res = JsonHelper.getJson(msg);
        }
        writeJSON(ctx, HttpResponseStatus.OK, Unpooled.copiedBuffer(res, CharsetUtil.UTF_8));
    }

    private static void writeJSON(ChannelHandlerContext ctx, HttpResponseStatus status, ByteBuf content) {
        if (ctx.channel().isWritable()) {
            if (content != null) {
                fullResponse = new DefaultFullHttpResponse(HTTP_1_1, status, content);
                fullResponse.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");
            } else {
                fullResponse = new DefaultFullHttpResponse(HTTP_1_1, status);
            }
            if (fullResponse.content() != null) {
                fullResponse.headers().set(CONTENT_LENGTH, fullResponse.content().readableBytes());
            }

            if (HttpUtil.isKeepAlive(request)) {
                fullResponse.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                ctx.writeAndFlush(fullResponse);
            } else {
                ctx.writeAndFlush(fullResponse).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
