package com.qs.udp.tool;

public class StrWithHexTransform {

	public static void main(String[] args) {

	}

	/*截取字符串
	 * src：byte源数组
	 * begin：截取源byte数组起始位置（0位置有效）
	 * bs,：byte目的数组（截取后存放的数组）
	 * destPos：截取后存放的数组起始位置（0位置有效） 
	 * count：截取的数据长度
	 */
	public static byte[] subBytes(byte[] src, int begin ,int count) {
		byte[] bs = new byte[count];
		System.arraycopy(src, begin, bs, 0, count);
		return bs;
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

	// 将16进制的字符串转成字符数组
	public static byte[] getHexBytes(String str) {
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < str.length() / 2; i++) {
			String subStr = str.substring(i * 2, i * 2 + 2);
			bytes[i] = (byte) Integer.parseInt(subStr, 16);
		}
		return bytes;
	}
}
