package com.qs.udp.tool;

import java.util.HashSet;
import java.util.Random;

public class GetGatherKeyTool {
	// 生成设备唯一标识规则，根据设备厂家/设备参数/设备协议/所属产品的id/随机数
	public static String getGatherKey(String Manufactor, String parameter, String Agreement, String projectId) {
		String gaterkey = "";
		// 根据厂商汉字，获取首字母前四位
		String cs = ChineseCharacterUtilTool.convertHanzi2Pinyin(Manufactor, false);
		if (cs.length() > 4) {
			cs = cs.substring(0, 4);
		}
		String num = randomArr();//随机数
		
		// String
		// gaterkey+=
		gaterkey = cs+"_"+parameter+"_"+Agreement+"_"+projectId+"_"+num;
		return gaterkey;
	}

	//生成不重复随机数
	public static String randomArr() {
		Random random = new Random();
		HashSet<Integer> hs = new HashSet<Integer>();
		String num = "";
		while (hs.size() < 3) {
			hs.add(random.nextInt(10) + 10);
		}
		for (Integer integer : hs) {
			num+=integer;
			//System.out.println(integer);
		}
		return num;
	}

	public static void main(String[] args) {
		System.out.println(getGatherKey("求实科技有限公司","HC","UDP","1"));
	}

}
