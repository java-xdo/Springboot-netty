package com.qs.udp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.qs.udp.pojo.Voltmeter;
import com.qs.udp.pojo.VoltmeterControllerPojo;
import com.qs.udp.service.VoltmeterService;
import com.qs.udp.voltmeter.controller.ChineseProverbServerHandler;

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
	// 定义的电表端口号
	@Value("${voltmeterPort}")
	private String voltmeterPort;

	// @PostMapping(value = "/test")
	@ResponseBody
	@RequestMapping("/test")
	public Object test() {
		System.out.println(voltmeterPort);
		return voltmeterPort;
	}

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

	// 对电表的控制命令
	@PostMapping(value = "/voltmeterEquipmentControl")
	public Object voltmeterController(String state, String time) throws Exception {
		JsonObject object = new JsonObject();

		if (state != null && state != "") {
			object.addProperty("S", state);
		}
		if (time != null && time != "") {
			object.addProperty("L", time);
		}
		String content = object.toString();// 组织发送的内容

		VoltmeterControllerPojo.map.get("myctx")
				.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(content, CharsetUtil.UTF_8),
						VoltmeterControllerPojo.map1.get("myPacket").sender()));

		return content;
	}
}
