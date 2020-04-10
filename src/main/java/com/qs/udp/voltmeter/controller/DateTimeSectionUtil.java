package com.qs.udp.voltmeter.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeSectionUtil {
	// 根据当前时间时间获取表名
	public static int getTableName() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String time = df.format(new Date());
		String[] beginDate = time.split(" ");
		int tableName = 0;
		time = beginDate[0].replace("-", "");
		tableName = Integer.valueOf(time).intValue();
		return tableName;
	}
	public static void main(String[] args) {
		System.out.println(getTableName());
	}
}
