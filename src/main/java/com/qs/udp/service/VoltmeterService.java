package com.qs.udp.service;

import com.qs.udp.pojo.Voltmeter;

public interface VoltmeterService {
	int deleteByPrimaryKey(Integer id);

	int insert(Voltmeter record);

	Voltmeter selectByPrimaryKey(Integer id);

	int updateByPrimaryKey(Voltmeter record);
}
