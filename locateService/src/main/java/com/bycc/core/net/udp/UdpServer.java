package com.bycc.core.net.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * Created by Administrator on 2017/6/16.
 */
public class UdpServer {

    private Integer port;

    public UdpServer(int port) {
        this.port = port;
    }

    public void run(){

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                        @Override
                        protected void initChannel(NioDatagramChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 业务逻辑处理
                            pipeline.addLast(new UdpHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind(port).sync();

            System.out.println("Netty-UDP server listening on port " + port);

            future.channel().closeFuture().sync();
        } catch(Exception e) {
        	e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
