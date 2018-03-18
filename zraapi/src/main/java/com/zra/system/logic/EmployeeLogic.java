package com.zra.system.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.projectZO.ProjectZODto;
import com.zra.system.entity.EmployeeEntity;
import com.zra.system.service.EmployeeService;

/**
 * 员工logic
 * @author wangws21 2016-8-1
 */
@Component
public class EmployeeLogic {
	
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 根据id获取employee
	 * @param 员工id
	 */
	public EmployeeEntity getEmployeeById(String id){
		return employeeService.getEmployeeById(id);
	}
	
	/**
	 * 根据userId获取employee
	 * @param 员工userId
	 */
	public EmployeeEntity getEmployeeByUserId(String userId){
		return employeeService.getEmployeeByUserId(userId);
	}
	
	/**
	 * 根据电话获取员工信息
	 * @author tianxf9
	 * @param mobileStr
	 * @return
	 */
	public List<EmployeeEntity> getEmployeeByMobile(String mobileStr) {
		return this.employeeService.getEmployeeByMobile(mobileStr);
	}
	
	/**
	 * 根据useraccount获取系统管理员
	 * @author tinaxf9
	 * @param userAccount
	 * @return
	 */
	public EmployeeEntity getEmployeeByUserAccount(String userAccount) {
		
		return this.employeeService.getEmployeeByUserAccount(userAccount);
	}
	
	/**
	 * 根据userId获取员工name
	 * @author tianxf9
	 * @param 员工userId
	 */
	public ProjectZODto getZOMsgByUserId(String userId){
		EmployeeEntity employee = employeeService.getEmployeeByUserId(userId);
		ProjectZODto zoDto = new ProjectZODto();
		zoDto.setProjectZOName(employee.getName());
		zoDto.setProjectZOId(employee.getId());
		return zoDto;
	}
	
	/**
	 * 查询employeeCode查询employEntity
	 * @author tianxf9
	 * @param code
	 * @return
	 */
	public List<EmployeeEntity> getEmployeeByCode(String code) {
		return this.employeeService.getEmployeeByCode(code);
	}
}