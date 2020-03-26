package com.qs.udp.mapper;

import java.util.List;

import com.qs.udp.pojo.DeviceBasicPojo;

public interface DeviceBasicMapper {

	List<DeviceBasicPojo> findDeviceBasicId(String deviceNum);
	
	List<DeviceBasicPojo> findDeviceBasicNum(Long id);
	
}