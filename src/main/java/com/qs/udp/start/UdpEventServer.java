package com.qs.udp.start;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.qs.udp.pojo.ServicePojo;
import com.qs.udp.service.ServicesService;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

@Component
public class UdpEventServer implements ApplicationRunner {
	private Logger log = LoggerFactory.getLogger(UdpEventServer.class);

	
	private List<Integer> ports;

	@Autowired
	ServicesService servicesService;

	@Override
	public void run(ApplicationArguments args) {
		// 开启线程，执行接收处理方法
		// 获取启动端口
		String port = "";
		List<ServicePojo> list = servicesService.findService(port);
		SrartGetPortService sgs = new SrartGetPortService();
		Map<String, List<Integer>> mapPortList = sgs.getPort(list);
		// 获取水表协议端口号并且启动
		log.info("水表upd服务开始启动启动");
	
		System.out.println(mapPortList.get("waterPostList"));
		ports = mapPortList.get("waterPostList");
		ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("wbAdjust-%d").build();
		ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(1024), factory, new ThreadPoolExecutor.AbortPolicy());
		singleThreadPool.execute(this::doWork);
	}

	/**
	 * @return void
	 * @Author fxk
	 * @Param []
	 * @Description : 数据接收线程 方法实现udp上传数据的接收，并将实现数据处理handler
	 * @Date 2019/8/2 17:00
	 **/
	private void doWork() {
		try {
//			EventLoopGroup group = new NioEventLoopGroup();
//			Bootstrap b = new Bootstrap();
//			// 由于我们用的是UDP协议，所以要用NioDatagramChannel来创建
//			b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)// 支持广播
//					.handler(new ChannelInitializer<Channel>() {
//						@Override
//						protected void initChannel(Channel ch) throws Exception {
//							ChannelPipeline pipeline = ch.pipeline();
//							// 设置处理handler.执行具体处理方法
//							pipeline.addLast(new ChineseProverbServerHandler());
//						}
//					});
//
//			b.bind(port).sync().channel().closeFuture().await();
			EventLoopGroup group = new NioEventLoopGroup();
			Collection<Channel> channels = new ArrayList<>(ports.size());
			for (int port : ports) {
				Bootstrap b = new Bootstrap();
				b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)// 支持广播
						.handler(new ChannelInitializer<Channel>() {
							@Override
							protected void initChannel(Channel ch) throws Exception {
								ChannelPipeline pipeline = ch.pipeline();
								// 设置处理handler.执行具体处理方法
								pipeline.addLast(new ChineseProverbServerHandler());
							}
						});

				Channel serverChannel = b.bind(port).sync().channel();
				channels.add(serverChannel);
			}
			for (Channel ch : channels) {
				ch.closeFuture().sync();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error("执行udp接收服务出错" + e.getMessage());

		}
	}
}
