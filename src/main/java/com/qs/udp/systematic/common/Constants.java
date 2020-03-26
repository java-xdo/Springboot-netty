package com.qs.udp.systematic.common;

/*
 * 存放设备相关的协议配置，新建设备时，再此处新加变量，方便管理
 * @author: xdo
 * 
 * */
public class Constants {
	/*
	 * DDSU5168型单相电子式电能表，udp协议，报文内容为：
	 * {"C":0,"T":946665018,"S":1,"R":23,"P":25.9,"L":1440,"n":0.966,"I":0.136,"HC":9.33,
	 * "dkey":"869975030499233","pkey":"DDS","U":235}
	 * 或者{"dkey":"869975030499233","pkey":"DDS"}Electric energy meter
	 * */
	public static final int ELECTRIC_ENERGY_METER = 0001;
}
