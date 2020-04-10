package com.qs.udp.rabbitmq.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.qs.udp.controller.WaterController;

/**
 * 消息消费者1
 */
@Component
public class FirstConsumer {
	private Logger log = LoggerFactory.getLogger(FirstConsumer.class);
	/**
	 * queues 指定从哪个队列（queue）订阅消息
	 * @param message
	 */
	//@RabbitListener(queues = { "" })
	public void handleMessage(Message message) {
		// 处理消息
		log.info("我是消费者，我收到的消息为：" + message);
		
	}

}
