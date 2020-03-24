package com.qs.udp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qs.udp.mapper.VoltmeterMapper;
import com.qs.udp.pojo.Voltmeter;
import com.qs.udp.service.VoltmeterService;

@Service
public class VoltmeterServiceImpl implements VoltmeterService {

	
	@Autowired
	private VoltmeterMapper voltmeterMapper;


	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return voltmeterMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Voltmeter record) {
		// TODO Auto-generated method stub
		return voltmeterMapper.insert(record);
	}

	@Override
	public Voltmeter selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return voltmeterMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Voltmeter record) {
		// TODO Auto-generated method stub
		return voltmeterMapper.updateByPrimaryKey(record);
	}

}
