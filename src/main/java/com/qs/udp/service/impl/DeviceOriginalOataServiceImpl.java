package com.qs.udp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qs.udp.mapper.DeviceOriginalOataMapper;
import com.qs.udp.service.DeviceOriginalOataService;

@Service
public class DeviceOriginalOataServiceImpl implements DeviceOriginalOataService {

	
	@Autowired
	private DeviceOriginalOataMapper deviceOriginalOataMapper;

	@Override
	public int insertDeviceOriginalOataValue(long id,String gatherKey, String upData) {
		// TODO Auto-generated method stub
		return deviceOriginalOataMapper.insertDeviceOriginalOataValue(id,gatherKey, upData);
	}




	

}
