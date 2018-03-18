package com.ziroom.zrp.service.houses.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.ziroom.zrp.houses.entity.EmployeeEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;

public class EmployeeDaoTest extends BaseTest{
	@Resource(name="houses.employeeDao")
	private EmployeeDao employeeDao;
	
	@Test
	public void testfindEmployeeById(){
		String fid = "9000095657020325331";
		EmployeeEntity entity = employeeDao.findEmployeeById(fid);
		System.err.println(JSONObject.toJSON(entity));
	}
	
	@Test
	public void testfindEmployeeByCode(){
		String fcode = "20077574";
		EmployeeEntity entity = employeeDao.findEmployeeByCode(fcode);
		System.err.println(JSONObject.toJSON(entity));
	}

}
