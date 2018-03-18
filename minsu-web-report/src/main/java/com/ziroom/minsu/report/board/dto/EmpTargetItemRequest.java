/**
 * @FileName: EmpTargetItemRequest.java
 * @Package com.ziroom.minsu.report.board.dto
 * 
 * @author bushujie
 * @created 2017年1月13日 上午11:20:21
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.board.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>专员目标列表参数</p>
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
public class EmpTargetItemRequest extends PageRequest{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -2310020779839419579L;
	
	/**
	 * 员工编号
	 */
	private String empCode;
	/**
	 * 员工名称
	 */
	private String empName;
	/**
	 * 员工目标月份
	 */
	private String targetMonth;

	/** excel名字 */
	private  String excelName ;

	/** methodName名字 */
	private  String methodName ;

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}
	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @return the targetMonth
	 */
	public String getTargetMonth() {
		return targetMonth;
	}
	/**
	 * @param targetMonth the targetMonth to set
	 */
	public void setTargetMonth(String targetMonth) {
		this.targetMonth = targetMonth;
	}

}
