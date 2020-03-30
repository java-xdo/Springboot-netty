package com.qs.udp.rabbitmq.receiver;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者1
 */
@Component
public class FirstConsumer {

	/**
	 * queues 指定从哪个队列（queue）订阅消息
	 * 
	 * @param message
	 */
//	@RabbitListener(queues = { "pay_money" })
//	public void handleMessage(Message message) {
//		// 处理消息
//		System.out.println("我是消费者，我收到的消息为：" + message);
//	}

}
