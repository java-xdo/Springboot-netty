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
	public Object deleteByPrimaryKey() {

		List<ServicePojo> list = servicesService.findService();

		return servicesService.findService();
	}

}
