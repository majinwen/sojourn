/**
 * @FileName: EmployeeRequest.java
 * @Package com.ziroom.minsu.services.basedata.dto
 * 
 * @author liujun
 * @created 2016年3月12日 下午3:13:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.dto;


import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * 
 * <p>请求参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class EmployeeRequest extends PageRequest{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4625548985466290939L;
	
	/**
	 * 员工姓名
	 */
	private String empName;
	
	/**
	 * 员工编号
	 */
	private String empCode;
	
	/**
	 * 员工手机号
	 */
	private String empMobile;

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpMobile() {
		return empMobile;
	}

	public void setEmpMobile(String empMobile) {
		this.empMobile = empMobile;
	}
}
