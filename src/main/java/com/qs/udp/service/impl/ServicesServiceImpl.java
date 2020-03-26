package com.qs.udp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qs.udp.mapper.ServiceMapper;
import com.qs.udp.pojo.ServicePojo;
import com.qs.udp.service.ServicesService;

@Service
public class ServicesServiceImpl implements ServicesService {

	
	@Autowired
	private ServiceMapper serviceMapper;

	@Override
	public List<ServicePojo> findService() {
		// TODO Auto-generated method stub
		return serviceMapper.findService();
	}


	

}
