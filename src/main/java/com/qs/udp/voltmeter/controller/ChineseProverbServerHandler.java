package com.qs.udp.voltmeter.controller;

import java.util.HashMap;
import java.util.Map;

import com.qs.udp.pojo.VoltmeterControllerPojo;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	
	static Map<String, Object> map = new HashMap<String, Object>();
	
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		VoltmeterControllerPojo.map.put("myctx", ctx);
		VoltmeterControllerPojo.map1.put("myPacket", packet);
		
		String req = packet.content().toString(CharsetUtil.UTF_8);// 通过content()来获取消息内容
		System.out.println("电表回执消息：" + req);
		ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("", CharsetUtil.UTF_8), packet.sender()));

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		cause.printStackTrace();
	}



}
