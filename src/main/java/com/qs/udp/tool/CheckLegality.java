package com.qs.udp.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qs.udp.start.ChineseProverbServerHandler;
import com.qs.udp.water.check.tool.WaterCheck;

//校验报文合法性
public class CheckLegality {
	private static Logger log = LoggerFactory.getLogger(CheckLegality.class);

	public static boolean checkMessage(byte[] data) {
		byte head01 = data[0]; // 第一个帧头位置
		byte len = data[14];// 得到控制码

		if (head01 == 0x68) {// 验证报文头部合法性
			if (WaterCheck.activeReport(data)) {
				log.info("报文不合法，丢弃");
				return true;

			}

//			if (len == 0x03) { // 代表设置结果包，表端主动上报
//
//				
//			} else if (len == 0x02) { // 代发起请求，表端主动发起
//
//			} else if (len == 0x01) {// 点抄指令执行，数据上报
//
//			} else if (len == 0x04) {// 阀门状态上报，表端主动上报
//
//			} else if (len == 0x81) {// 服务器回复，无任务
//
//			} else if (len == 0x83) {// 服务器回复，设置参数
//
//			} else if (len == 0x84) {// 服务器回复，阀门动作（下发指令）
//
//			} else if (len == 0x85) {// 服务器回复，点抄指令
//
//			}

		} else {
			return false;
		}
		return false;
	}

	public static void main(String[] args) {
		String feedback = "68000869975032169826000000000101000000000000000000000000000000000000000000000034011F6C0220200408063112046004083710902514364131389B0000000000000000009B0000000003020018661809C400000000001D16";
		// 由于16进制字符发送时只能发送字节，这里讲字符串转换成字节
		byte bytes[] = getHexBytes(feedback);
		CheckLegality.checkMessage(bytes);
		System.out.println(WaterCheck.CheckBit(bytes) + "");

	}

	// 将16进制的字符串转成字符数组
	public static byte[] getHexBytes(String str) {
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; i++) {
			String subStr = str.substring(i * 2, i * 2 + 2);
			bytes[i] = (byte) Integer.parseInt(subStr, 16);
		}
		return bytes;
	}

	// 将16进制的byte数组转换成字符串
	public static String getBufHexStr(byte[] raw) {
		String HEXES = "0123456789ABCDEF";
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}
}
