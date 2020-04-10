package com.qs.udp.start;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qs.udp.pojo.ServicePojo;

public class SrartGetPortService {
	// 获取端口数据
	public static Map<String, List<Integer>> getPort(List<ServicePojo> list) {
		Map<String, List<Integer>> mapList = new HashMap<String, List<Integer>>();
		List<Integer> postList = new ArrayList();// 电表协议端口号
		List<Integer> waterPostList = new ArrayList();// 水表协议端口号

		int port;
		boolean status;
		for (int i = 0; i < list.size(); i++) {
			status = list.get(i).isStatus();
			if (status) {
				if (list.get(i).getType().equals("1001")) {// 代表是udp电表协议
					port = list.get(i).getPort();
					postList.add(port);
				}
				if (list.get(i).getType().equals("1002")) {// 代表是udp水表协议
					port = list.get(i).getPort();
					waterPostList.add(port);
				}

			}

		}
		mapList.put("electric", postList);
		mapList.put("waterPostList", waterPostList);

		return mapList;
	}
}
