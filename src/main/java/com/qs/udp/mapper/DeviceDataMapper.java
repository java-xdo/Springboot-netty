package com.qs.udp.mapper;

public interface DeviceDataMapper {

	int insertDeviceDataValue(String gatherKey,String collect_key,String value,Long deviceOriginalOateId);

	
}