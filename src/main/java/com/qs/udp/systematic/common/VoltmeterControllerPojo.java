package com.qs.udp.systematic.common;


import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public class VoltmeterControllerPojo {

	public static Map<String, DatagramPacket> mapPacket = new HashMap<String, DatagramPacket>();// 存放回复消息变量值

	public static Map<String, ChannelHandlerContext> mapContext = new HashMap<String, ChannelHandlerContext>();// 存放水表变量值

}
