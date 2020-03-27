package com.qs.udp.redis.util;

public class main {
	public static void main(String[] args) {
		RedisUtil.setValue("869975030499233", "qskj_HC_UDP_1_161711");
		
		String gatherKey = RedisUtil.getValue("8699750304992331");
		if(gatherKey!=null) {
			System.out.println(gatherKey);
		}else {
			System.out.println("");
		}
		
		
	}

}
