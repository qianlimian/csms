package com.bycc.net.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Netty Http server
 */
public class HttpServer {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private Integer port;

	private ChannelFuture channelFuture;

	public HttpServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							ChannelPipeline pipeline = socketChannel.pipeline();
							/* http request瑙ｇ爜 */
							pipeline.addLast("http-decoder", new HttpRequestDecoder());
							pipeline.addLast("http-aggregator", new HttpObjectAggregator(65536));
							/* http response 缂栫爜 */
							pipeline.addLast("http-encoder", new HttpResponseEncoder());
							pipeline.addLast("chunk-write-handler", new ChunkedWriteHandler());
							/* http request handler */
							pipeline.addLast("http-handler", new HttpHandler());
						}
					});

			channelFuture = bootstrap.bind(port).sync();
			logger.info("http服务启动，监听 {} 端口", port);
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public void close() {
		channelFuture.channel().close();
		logger.info("http服务停止");
	}
}
