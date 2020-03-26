package com.qs.udp.systematic.common;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public class VoltmeterControllerPojo {

	public static Map<String, ChannelHandlerContext> map = new HashMap<String, ChannelHandlerContext>();// 存放变量值

	public static Map<String, DatagramPacket> map1 = new HashMap<String, DatagramPacket>();// 存放变量值

}
