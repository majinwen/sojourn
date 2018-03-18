package com.zra.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.system.entity.EmployeeEntity;

/**
 * 员工dao
 * @author wangws21 2016-8-1
 */
@Repository
public interface EmployeeMapper {

	/**
	 * 根据id获取employee
	 * @param 员工id
	 */
	EmployeeEntity getEmployeeById(@Param("id")String id);
	
	/**
	 * 根据userId获取employee
	 * @param 员工userId
	 */
	List<EmployeeEntity> getEmployeeByUserId(@Param("userId")String userId);
	
	/**
	 * 根据电话号码查询员工信息
	 * @author tianxf9
	 * @param mobileStr
	 * @return
	 */
	List<EmployeeEntity> getEmployeeByMobile(@Param("mobileStr")String mobileStr);
	
	/**
	 * 根据useraccount获取系统管理员
	 * @author tianxf9
	 * @param userAccount
	 * @return
	 */
	EmployeeEntity getSystemManager(String userAccount);
	
	/**
	 * 根据employeeCode查询employeeEntity
	 * @author tianxf9
	 * @param code
	 * @return
	 */
	List<EmployeeEntity> getEmployeeByCode(String code);
} 
