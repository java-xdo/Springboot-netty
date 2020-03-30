package com.qs.udp.mapper;

import java.util.List;

import com.qs.udp.pojo.ServicePojo;

public interface ServiceMapper {

	List<ServicePojo> findService(String port);

	
}