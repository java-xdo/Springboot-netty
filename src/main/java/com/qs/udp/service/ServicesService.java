package com.qs.udp.service;

import java.util.List;

import com.qs.udp.pojo.ServicePojo;

public interface ServicesService {
	List<ServicePojo> findService(String port);
}
