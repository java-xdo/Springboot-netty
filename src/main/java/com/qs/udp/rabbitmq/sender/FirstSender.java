package com.qs.udp.rabbitmq.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qs.udp.rabbitmq.receiver.FirstConsumer;

/**
 * 消息发送 生产者
 */
@Component
public class FirstSender {
	private Logger log = LoggerFactory.getLogger(FirstConsumer.class);
	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 发送消息
	 * 
	 * @param         gatherkey，设备唯一标识
	 * @param message 消息
	 */
	public void send(String gatherkey, Object message) {
		CorrelationData correlationId = new CorrelationData(gatherkey);
		log.info("我是生产者我的消息为" + message);
		rabbitTemplate.convertAndSend("pay_money", message, correlationId);
	}
}
