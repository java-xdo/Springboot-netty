package com.qs.udp.water.check.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qs.udp.voltmeter.controller.ValueController;

public class WaterCheck {
	private static Logger log = LoggerFactory.getLogger(WaterCheck.class);

	public static boolean activeReport(byte[] data) {// 代表设置结果包，表端主动上报

		byte len = data[39];// 得到数据域长度
		int num = 39 + len + 1 + 1;
		int end = data[num]; // 结束码的位置
		if (end == 0x16) {
			// 验证码校验

			if (data[num - 1] == CheckBit(data)) {

				return true;
			} else {
				log.info("验证码不正确");
				return false;
			}
		} else {
			log.info("结束码不正确");
			return false;
		}

	}

	/**
	 * 计算校验码
	 * 
	 * @param bytes
	 * @return
	 */
	public static int CheckBit(byte[] bytes) {
		byte a = bytes[0];
		for (int i = 1; i < bytes.length - 2; i++) {
			byte b = bytes[i];
			a = (byte) (a + b);

		}
		// log.info("=====>>> CheckBit is : " + TypeChangeLib.byteToHex(a));

		return a;
	}
}
