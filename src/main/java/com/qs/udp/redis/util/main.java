package com.qs.udp.redis.util;

public class main {
	public static void main(String[] args) {
		 RedisUtil.setValue("869975032169826", "bjhy_MC_UDP_2_161213");
		for (int i = 0; i < 1; i++) {
			String gatherKey = RedisUtil.getValue("869975032169826");
			if (gatherKey != null) {
				System.out.println(gatherKey);
			} else {
				System.out.println("");
			}
		}

	}

}
