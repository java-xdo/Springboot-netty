package com.qs.udp.mapper;

import java.util.List;

import com.qs.udp.pojo.DeviceBasicPojo;

public interface DeviceBasicMapper {

	List<DeviceBasicPojo> findDeviceBasicId(String deviceNum);
	
	List<DeviceBasicPojo> findDeviceBasicNum(Long id);
	
	
	List<DeviceBasicPojo> findGatherKey(String deviceNum);//根据设备设备标识码，获取设备唯一标识即：gatherkey
	
}