package com.qs.udp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qs.udp.mapper.DeviceBasicMapper;
import com.qs.udp.pojo.DeviceBasicPojo;
import com.qs.udp.service.DeviceBasicService;

@Service
public class DeviceBasicServiceImpl implements DeviceBasicService {

	@Autowired
	private DeviceBasicMapper deviceBasicMapper;

	@Override
	public List<DeviceBasicPojo> findDeviceBasicId(String deviceNum) {
		// TODO Auto-generated method stub
		return deviceBasicMapper.findDeviceBasicId(deviceNum);
	}

	@Override
	public List<DeviceBasicPojo> findDeviceBasicNum(Long id) {
		// TODO Auto-generated method stub
		return deviceBasicMapper.findDeviceBasicNum(id);
	}

}
