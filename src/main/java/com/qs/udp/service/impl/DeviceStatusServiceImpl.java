package com.qs.udp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qs.udp.mapper.DeviceStatusMapper;
import com.qs.udp.service.DeviceStatusService;

//更改设备状态
@Service
public class DeviceStatusServiceImpl implements DeviceStatusService {

	@Autowired
	private DeviceStatusMapper deviceStatusMapper;

	@Override
	public int updateDeviceStatus(int status, String gatherKey) {
		// TODO Auto-generated method stub
		return deviceStatusMapper.updateDeviceStatus(status, gatherKey);
	}

}
