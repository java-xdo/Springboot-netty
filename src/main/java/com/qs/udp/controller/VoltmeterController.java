package com.qs.udp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.qs.udp.pojo.DeviceBasicPojo;
import com.qs.udp.pojo.Voltmeter;
import com.qs.udp.service.DeviceBasicService;
import com.qs.udp.service.DeviceStatusService;
import com.qs.udp.service.VoltmeterService;
import com.qs.udp.systematic.common.VoltmeterControllerPojo;

import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/*
 * 有关电表的相关操作动过http，将信息暴露出来
 * 
 * */
@RestController
@RequestMapping("/VoltmeterController")
public class VoltmeterController {

	@Autowired
	VoltmeterService voltmeterService;

	@Autowired
	DeviceStatusService deviceStatusService;

	@Autowired
	DeviceBasicService deviceBasicService;

	@PostMapping(value = "/deleteByPrimaryKey")
	public Object deleteByPrimaryKey(int id) {
		return voltmeterService.deleteByPrimaryKey(id);
	}

	@PostMapping(value = "/insert")
	public Object insert(Voltmeter record) {
		return voltmeterService.insert(record);
	}

	@PostMapping(value = "/selectByPrimaryKey")
	public Object selectByPrimaryKey(Integer id) {
		return voltmeterService.selectByPrimaryKey(id);
	}

	@PostMapping(value = "/updateByPrimaryKey")
	public Object updateByPrimaryKey(Voltmeter record) {
		return voltmeterService.updateByPrimaryKey(record);
	}

	// 对电表的控制命令state:状态，time上传周期；deviceBasicId设备id
	@PostMapping(value = "/voltmeterEquipmentControl")
	public Object voltmeterController(String state, String time, String gatherKey) throws Exception {
		JsonObject object = new JsonObject();

		if (state != null && state != "") {
			object.addProperty("S", state);
		}
		if (time != null && time != "") {
			object.addProperty("L", time);
		}
		String content = object.toString();// 组织发送的内容

		

			VoltmeterControllerPojo.map.get(gatherKey)
					.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(content, CharsetUtil.UTF_8),
							VoltmeterControllerPojo.map1.get(gatherKey).sender()));
			int status = Integer.parseInt(state);// 设备当前状态
			 deviceStatusService.updateDeviceStatus(status, gatherKey);
		

		return content;
	}
}
