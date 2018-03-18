package com.zra.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.system.dao.EmployeeMapper;
import com.zra.system.entity.EmployeeEntity;

/**
 * @author wangws21 2016-8-1
 * 员工服务
 */
@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	/**
	 * 根据id获取employee
	 * @param 员工id
	 */
	public EmployeeEntity getEmployeeById(String id){
		return employeeMapper.getEmployeeById(id);
	}
	
	/**
	 * 根据userId获取employee
	 * @param 员工userId
	 */
	public EmployeeEntity getEmployeeByUserId(String userId){
		List<EmployeeEntity> employeeEntityList = employeeMapper.getEmployeeByUserId(userId);
		if(employeeEntityList!=null && !employeeEntityList.isEmpty()){
			return employeeEntityList.get(0);
		}
		return null;
	}
	
	/**
	 * 根据电话号码查询员工信息
	 * @author tianxf9
	 * @param mobileStr
	 * @return
	 */
	public List<EmployeeEntity> getEmployeeByMobile(String mobileStr) {
		return this.employeeMapper.getEmployeeByMobile(mobileStr);
	}
	
	/**
	 * 根据userAccount获取系统管理员
	 * @author tianxf9
	 * @param userAccount
	 * @return
	 */
	public EmployeeEntity getEmployeeByUserAccount(String userAccount) {
		return this.employeeMapper.getSystemManager(userAccount);
	}
	
	/**
	 * 根据employeeCode查询employeeEntity
	 * @author tianxf9
	 * @param code
	 * @return
	 */
	public List<EmployeeEntity> getEmployeeByCode(String code) {
		return this.employeeMapper.getEmployeeByCode(code);
	}
}
