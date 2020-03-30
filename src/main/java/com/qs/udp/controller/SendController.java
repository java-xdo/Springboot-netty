package com.qs.udp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qs.udp.rabbitmq.sender.FirstSender;

/**
 * 消息队列生产者消费测试demo
 */
@RestController
public class SendController {

	@Autowired
	private FirstSender firstSender;

	@GetMapping("/send")
	public String send() {
		String message = "CH_1000_MY_123";
		String uuid = "qskj_HC_UDP_1_161711";
		firstSender.send(uuid, message);
		return uuid;
	}

}
