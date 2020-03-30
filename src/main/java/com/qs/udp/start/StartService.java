package com.qs.udp.start;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.qs.udp.pojo.ServicePojo;
import com.qs.udp.service.ServicesService;
import com.qs.udp.voltmeter.controller.ChineseProverbServer;

/**
 * 继承Application接口后项目启动时会按照执行顺序执行run方法 通过设置Order的value来指定执行的顺序 设置电表udp服务自启动
 */
@Component
@Order(value = 2)
public class StartService implements ApplicationRunner {

	@Autowired
	ServicesService servicesService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 从数据库中查询应该启动的服务端口
		String port = "";
		List<ServicePojo> list = servicesService.findService(port);
		List portList = StartService.getPort(list);
		ChineseProverbServer cps = new ChineseProverbServer();
		System.out.println("电表upd服务开始启动启动");

		cps.run(portList);

	}

	// 拼接端口数据
	public static List<Integer> getPort(List<ServicePojo> list) {
		List<Integer> postList = new ArrayList();
		int port;
		boolean status;
		for (int i = 0; i < list.size(); i++) {
			status = list.get(i).isStatus();
			if (status) {
				port = list.get(i).getPort();
				postList.add(port);
			}

		}
		System.out.println(postList.toString());
		return postList;
	}

}
