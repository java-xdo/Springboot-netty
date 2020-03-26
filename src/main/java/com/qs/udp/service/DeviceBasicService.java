package com.qs.udp.service;

import java.util.List;

import com.qs.udp.pojo.DeviceBasicPojo;

public interface DeviceBasicService {
	List<DeviceBasicPojo> findDeviceBasicId(String deviceNum);

	List<DeviceBasicPojo> findDeviceBasicNum(Long id);
}
