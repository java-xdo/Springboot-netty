package com.qs.udp.voltmeter.controller;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {
	
	
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
		ByteBuf buf = (ByteBuf)msg;
		byte [] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println(body);
	}
	

}
