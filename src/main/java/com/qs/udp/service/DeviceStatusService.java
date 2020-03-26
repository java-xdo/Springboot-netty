package com.qs.udp.service;

//更改设备状态
public interface DeviceStatusService {
	int updateDeviceStatus (int status,Long deviceBasicId);
}
