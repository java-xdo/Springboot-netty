package com.qs.udp.mapper;

public interface DeviceDataMapper {

	int insertDeviceDataValue(Long deviceBasicId,String collect_key,String value,Long deviceOriginalOateId);

	
}