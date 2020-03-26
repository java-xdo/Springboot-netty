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

	static Map<String, Object> map = new HashMap<String, Object>();
	// 用来注入bean
	public static ChineseProverbServerHandler chineseProverbServerHandler;

	@Autowired
	private DeviceOriginalOataService DeviceOriginalOataService;

	@Autowired
	private DeviceBasicMapper deviceBasicMapper;

	@Autowired
	private DeviceDataMapper deviceDataMapper;

	@Override
	public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		// 将参数存入系统变量中，方便通过http请求获取，
		String req = packet.content().toString(CharsetUtil.UTF_8);// 通过content()来获取消息内容
		//将json转化map
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map = gson.fromJson(req, map.getClass());
		
		String deviceNum = map.get("dkey").toString();
		VoltmeterControllerPojo.map.put(deviceNum, ctx);
		VoltmeterControllerPojo.map1.put(deviceNum, packet);
		// 将获取到的消息，转化成map

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

			// Long deviceBasicId = 869975030499233L;
			List<DeviceBasicPojo> listDeviceBasic = chineseProverbServerHandler.deviceBasicMapper
					.findDeviceBasicId(deviceNum);// 获取设备id

			Long deviceBasicId = listDeviceBasic.get(0).getId();
			// 获取主键
			IdWorker iw1 = new IdWorker();
			long id = iw1.nextId();
			// 判断是否查找到设备
			if (listDeviceBasic.size() > 0) {
				chineseProverbServerHandler.DeviceOriginalOataService.insertDeviceOriginalOataValue(id, deviceBasicId,
						req);
				if (req.length() > 50) {

					AnalyticalData(Constants.ELECTRIC_ENERGY_METER, map, id, deviceBasicId);
				}
			}
		}
	}

	// 处理解析后的数据 type:哪个协议，map：接收到的Sting数据转换成map，deviceOriginalOateId：原始数据id
	public void AnalyticalData(int type, Map<String, Object> map, long deviceOriginalOateId, long deviceBasicId) {

		String HC = map.get("HC").toString();// 存历史电量
		chineseProverbServerHandler.deviceDataMapper.insertDeviceDataValue(deviceBasicId, "HC", HC,
				deviceOriginalOateId);
	}
}
