package com.qs.udp.voltmeter.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qs.udp.mapper.DeviceBasicMapper;
import com.qs.udp.mapper.DeviceDataMapper;
import com.qs.udp.pojo.DeviceBasicPojo;
import com.qs.udp.rabbitmq.receiver.FirstConsumer;
import com.qs.udp.redis.util.RedisUtil;
import com.qs.udp.redis.util.WaterMeterUdp;
import com.qs.udp.service.DeviceOriginalOataService;
import com.qs.udp.systematic.common.VoltmeterControllerPojo;
import com.qs.udp.tool.IdWorker;
import com.qs.udp.tool.StrWithHexTransform;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;

@Component
public class ValueController {

	private Logger log = LoggerFactory.getLogger(ValueController.class);

	String msg = "";
	// 用来注入bean
	public static ValueController ValueController;

	@Autowired
	private DeviceOriginalOataService DeviceOriginalOataService;

	@Autowired
	private DeviceDataMapper deviceDataMapper;

	@Autowired
	private DeviceBasicMapper deviceBasicMapper;

	// 注入bean
	@PostConstruct
	public void init() {
		ValueController = this;

	}

	// 处理原始数据数据逻辑
	public void processingData(String req) throws Exception {
		// 获取设备唯一标识
		msg = req;
		String deviceNum = req.substring(5, 20);
		log.info(deviceNum);

		String gatherKey = getGatherKey(deviceNum);
		// 获取主键
		IdWorker iw1 = new IdWorker();
		long id = iw1.nextId();
		int tableName=DateTimeSectionUtil.getTableName();
		ValueController.DeviceOriginalOataService.insertDeviceOriginalOataValue(tableName,id, gatherKey, req);// 原始报文数据插入
		log.info("插入原始数据成功");
		// 得到控制码
		byte[] bytes = StrWithHexTransform.getHexBytes(req);
		byte len = bytes[14];// 控制码
		controlWater(len, bytes, id, gatherKey);
	}

	public void controlWater(byte len, byte[] bytes, long id, String gatherKey) {
		log.info(len + "控制码");

		if (len == 0x01) {// 如果控制码等于01则执行 解析抄表回复的命令
			log.info("执行解析抄表命令");
			String val = msg.substring(128, 130);
			log.info(val);
			// 计算真实电压
			double num = (double) Integer.parseInt(val, 16);
			String va = (num + 200) / 100 + "";
			log.info(va);
			AnalyticalData(va, "Voltage", id, gatherKey);
			// 计算累计脉冲数
			String num1 = msg.substring(151, 159);
			AnalyticalData(num1, "Sumpulse", id, gatherKey);
			log.info("插入解析数据成功");
		}
	}

	// 处理解析后的数据 type:哪个协议，map：接收到的Sting数据转换成map，deviceOriginalOateId：原始数据id
	public void AnalyticalData(String value, String key, long deviceOriginalOateId, String gatherKey) {

		ValueController.deviceDataMapper.insertDeviceDataValue(gatherKey, key, value, deviceOriginalOateId);
	}

	public String getGatherKey(String deviceNum) {
		log.info("获取gatherkey");

		String gatherKey = RedisUtil.getValue(deviceNum);

		if (gatherKey != null) {
			log.info("redis获取");
			return gatherKey;
		} else {
			log.info("数据库获取" + deviceNum);

			List<DeviceBasicPojo> list = ValueController.deviceBasicMapper.findGatherKey(deviceNum);
			RedisUtil.setValue(deviceNum, list.get(0).getGather_key());
			log.info("数据库获取" + deviceNum + list.get(0).getGather_key());

			return list.get(0).getGather_key();
		}

	}

	// String转byte
	public static byte[] hexString2Bytes(String src) {
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			ret[i] = (byte) Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
		}
		return ret;
	}
}
