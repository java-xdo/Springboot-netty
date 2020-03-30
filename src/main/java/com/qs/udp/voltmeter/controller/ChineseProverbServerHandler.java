package com.qs.udp.voltmeter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.qs.udp.mapper.DeviceBasicMapper;
import com.qs.udp.mapper.DeviceDataMapper;
import com.qs.udp.pojo.DeviceBasicPojo;
import com.qs.udp.redis.util.RedisUtil;
import com.qs.udp.service.DeviceOriginalOataService;
import com.qs.udp.systematic.common.Constants;
import com.qs.udp.systematic.common.VoltmeterControllerPojo;
import com.qs.udp.tool.IdWorker;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

@Component
public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	// 用来注入bean
	public static ChineseProverbServerHandler chineseProverbServerHandler;

	@Autowired
	private DeviceOriginalOataService DeviceOriginalOataService;


	@Autowired
	private DeviceDataMapper deviceDataMapper;

	@Override
	public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		// 将参数存入系统变量中，方便通过http请求获取，
		String req = packet.content().toString(CharsetUtil.UTF_8);// 通过content()来获取消息内容
		// 将json转化map
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map = gson.fromJson(req, map.getClass());

		String deviceNum = map.get("dkey").toString();// 代替换为gatherkey
		String gatherKey = getGatherKey(deviceNum);

		VoltmeterControllerPojo.map.put(gatherKey, ctx);
		VoltmeterControllerPojo.map1.put(gatherKey, packet);
	

		processingData(Constants.ELECTRIC_ENERGY_METER, req);// 处理设备原始数据
		// DeviceOriginalOataService.insertDeviceOriginalOataValue(deviceBasicId, req);
		System.out.println("设备回执消息：" + req);

		ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("", CharsetUtil.UTF_8), packet.sender()));

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		cause.printStackTrace();
	}

	// 注入bean
	@PostConstruct
	public void init() {
		chineseProverbServerHandler = this;

	}

	// 处理原始数据数据逻辑
	public void processingData(int type, String req) throws Exception {
		if (type == 0001) {// 电子式电能表电表逻辑处理
			Gson gson = new Gson();
			Map<String, Object> map = new HashMap<String, Object>();
			map = gson.fromJson(req, map.getClass());
			String deviceNum = map.get("dkey").toString();
			String gatherKey = getGatherKey(deviceNum);
			// 获取主键
			IdWorker iw1 = new IdWorker();
			long id = iw1.nextId();

			chineseProverbServerHandler.DeviceOriginalOataService.insertDeviceOriginalOataValue(id, gatherKey, req);// 原始报文数据插入
			if (req.length() > 50) {

				AnalyticalData(Constants.ELECTRIC_ENERGY_METER, map, id, gatherKey);
			}

		}
	}

	// 处理解析后的数据 type:哪个协议，map：接收到的Sting数据转换成map，deviceOriginalOateId：原始数据id
	public void AnalyticalData(int type, Map<String, Object> map, long deviceOriginalOateId, String gatherKey) {

		String HC = map.get("HC").toString();// 存历史电量
		chineseProverbServerHandler.deviceDataMapper.insertDeviceDataValue(gatherKey, "HC", HC, deviceOriginalOateId);
	}

	public String getGatherKey(String deviceNum) {
	
		String gatherKey = RedisUtil.getValue(deviceNum);

		if(gatherKey!=null) {
			return gatherKey;
		}else {
			return "";
		}
		
	}
}
