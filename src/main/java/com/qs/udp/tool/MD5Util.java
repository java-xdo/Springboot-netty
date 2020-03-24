package com.qs.udp.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密;根据力控的加密方式
 */
public class MD5Util {
 
	public static void main(String[] args) {
		MD5Util md5= new MD5Util();//测试方法
		System.out.println(md5.crypt("123456{zhenghao}"));
	}
	public static String crypt(String str) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("String to encript cannot be null or zero length");
		}
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}
 
}
