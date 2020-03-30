package com.qs.udp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qs.udp.pojo.ServicePojo;
import com.qs.udp.service.DeviceBasicService;
import com.qs.udp.service.ServicesService;
import com.qs.udp.voltmeter.controller.ChineseProverbServer;

/*
 * 查询需要开启的服务
 * 
 * */
@RestController
@RequestMapping("/serveiceController")
public class ServeiceController {

	@Autowired
	ServicesService servicesService;

	@Autowired
	DeviceBasicService deviceBasicService;

	@ResponseBody
	@RequestMapping("/test")
	public Object test(String deviceNum) {

		return deviceBasicService.findDeviceBasicId(deviceNum);
	}

	@PostMapping(value = "/findService")
	public Object findService() {
		String port = "";
		List<ServicePojo> list = servicesService.findService(port);
		return servicesService.findService(port);
	}

	@PostMapping(value = "/serviceStart")
	public Object serviceStart(String port, boolean status) {

		// 查询端口是否已经存在
		List<ServicePojo> list = servicesService.findService(port);
		if (list.size() != 0) {// 代表端口已经存在，不做处理
			return "false";

		} else {// 端口未存在，开启对应端口
			if (status) {

				int port1 = Integer.parseInt(port);

				ChineseProverbServer cps = new ChineseProverbServer();
				System.out.println("新增端口号" + port1);
				try {
					cps.runPort(port1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return "ok";

	}

}
