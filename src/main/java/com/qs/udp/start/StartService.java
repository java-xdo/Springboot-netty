package com.qs.udp.start;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.qs.udp.voltmeter.controller.ChineseProverbServer;

/**
 * 继承Application接口后项目启动时会按照执行顺序执行run方法 通过设置Order的value来指定执行的顺序 设置电表udp服务自启动
 */
@Component
@Order(value = 2)
public class StartService implements ApplicationRunner {

	@Value("${voltmeterPort}")
	private String voltmeterPort;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 获取配置文件端口号
		System.out.println("电表upd服务开始启动启动，端口号为：" + voltmeterPort);
		int port = Integer.valueOf(voltmeterPort);
		ChineseProverbServer cps = new ChineseProverbServer();
		cps.run(port);

	}

}
