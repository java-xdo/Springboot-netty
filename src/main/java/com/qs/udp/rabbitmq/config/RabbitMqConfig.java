package com.qs.udp.rabbitmq.config;

import org.springframework.context.annotation.Configuration;

//每一次新添加队列需要再此注入一个新的队列，目前仅支持一对一，如后期有需求，可作为一对多，即：一个生产者，对应多个消费者
@Configuration
public class RabbitMqConfig {

	/* 电表支付信息 */
	public static final String PAY_MONEY = "pay_money";

}
