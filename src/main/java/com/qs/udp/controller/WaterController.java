package com.qs.udp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qs.udp.redis.util.WaterMeterUdp;
import com.qs.udp.service.DeviceBasicService;
import com.qs.udp.service.DeviceStatusService;
import com.qs.udp.service.VoltmeterService;
import com.qs.udp.start.ChineseProverbServerHandler;
import com.qs.udp.systematic.common.VoltmeterControllerPojo;
import com.qs.udp.tool.CheckLegality;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/*
 * 有关水表的相关操作动过http，将信息暴露出来
 * 
 * */
@RestController
@RequestMapping("/waterController")
public class WaterController {
	private Logger log = LoggerFactory.getLogger(WaterController.class);
	@Autowired
	VoltmeterService voltmeterService;

	@Autowired
	DeviceStatusService deviceStatusService;

	@Autowired
	DeviceBasicService deviceBasicService;

	// 对水表阀门的控制命令state:0 开阀，1 关阀，2 翻转阀门
	@PostMapping(value = "/waterValveControl")
	public Object voltmeterController(String state, String gatherKey) throws Exception {
		int head = WaterMeterUdp.head;
		int tail = WaterMeterUdp.tail;
		String msg = "";
		if (state.equals("0")) {// 开阀门
			String openSwitchBody = WaterMeterUdp.openSwitchBody;
			msg = head + openSwitchBody + tail;
			log.info(msg);
		} else if (state.equals("1")) {// 关阀门
			String openSwitchBody = WaterMeterUdp.shutSwitchBody;
			msg = head + openSwitchBody + tail;
			log.info(msg);
		} else if (state.equals("2")) {// 翻转阀门
			String openSwitchBody = WaterMeterUdp.flipSwitchBody;
			msg = head + openSwitchBody + tail;
			log.info(msg);
		} else {
			return false;
		}
		try {
			VoltmeterControllerPojo.mapContext.get(gatherKey)
					.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8),
							VoltmeterControllerPojo.mapPacket.get(gatherKey).sender()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("无法获取设备连接信息,连接设备上数后重试");
		}

		int status = Integer.parseInt(state);// 设备当前状态
		int res = deviceStatusService.updateDeviceStatus(status, gatherKey);
		if (res >= 0) {
			return true;
		} else {
			return false;
		}

	}

	// 对水表阀门的控制命令点钞指令
	@PostMapping(value = "/waterBanknote")
	public Object waterBanknote(String gatherKey) throws Exception {
		int head = WaterMeterUdp.head;
		int tail = WaterMeterUdp.tail;
		String adress = WaterMeterUdp.adress;
		String openSwitchBody = WaterMeterUdp.waterBanknote;
		String msg = head + adress + openSwitchBody + tail;


		try {
			ByteBuf bufff = Unpooled.buffer();// netty需要用ByteBuf传输
			bufff.writeBytes(hexString2Bytes(msg));// 对接需要16进制

			VoltmeterControllerPojo.mapContext.get(gatherKey).writeAndFlush(new DatagramPacket(
					Unpooled.copiedBuffer(bufff), VoltmeterControllerPojo.mapPacket.get(gatherKey).sender()));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("无法获取设备连接信息,连接设备上数后重试");
			return false;
		}

		return true;

	}

	public static byte[] hexString2Bytes(String src) {
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			ret[i] = (byte) Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
		}
		return ret;
	}

}
