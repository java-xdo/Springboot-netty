package com.qs.udp.voltmeter.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class ChineseProverbServer {
	
	//启动udp端口号方法：传入参数List<Integer> ports;例如：List<Integer> ports = Arrays.asList(9999, 8888);
	public void run(List<Integer> ports) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		Collection<Channel> channels = new ArrayList<>(ports.size());
		for (int port : ports) {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)// 支持广播
					.handler(new ChineseProverbServerHandler()).handler(new TimeServerHandler());// ChineseProverbServerHandler是业务处理类

			Channel serverChannel = b.bind(port).sync().channel();
			channels.add(serverChannel);
		}
		for (Channel ch : channels) {
			ch.closeFuture().sync();
		}

// 单个端口开启代码，没有任何问题		
//		EventLoopGroup group = new NioEventLoopGroup();
//		Bootstrap b = new Bootstrap();
//		
//		b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)// 支持广播
//				.handler(new ChineseProverbServerHandler());// ChineseProverbServerHandler是业务处理类
//		b.bind(port).sync().channel().closeFuture().await();
	}

	public static void main(String[] args) throws Exception {
		int port = 9999;
		List<Integer> ports = Arrays.asList(8080, 8081);
		 new ChineseProverbServer().run(ports);
	}
	
	public String runPort(int port) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		
		b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)// 支持广播
				.handler(new ChineseProverbServerHandler());// ChineseProverbServerHandler是业务处理类
		b.bind(port).sync().channel().closeFuture().await();
		return "ok";
	}
}
