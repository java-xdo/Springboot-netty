package com.qs.udp.mapper;

import com.qs.udp.pojo.Voltmeter;
import com.qs.udp.pojo.VoltmeterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VoltmeterMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(Voltmeter record);

	Voltmeter selectByPrimaryKey(Integer id);

	int updateByPrimaryKey(Voltmeter record);
}