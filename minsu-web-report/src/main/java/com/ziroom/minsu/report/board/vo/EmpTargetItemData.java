/**
 * @FileName: EmpTargetItemData.java
 * @Package com.ziroom.minsu.report.board.vo
 * 
 * @author bushujie
 * @created 2017年1月22日 上午10:35:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.board.vo;

/**
 * <p>专员目标数据</p>
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
public class EmpTargetItemData {
	
	/**
	 * 员工编号
	 */
	private String empCode;

	/**
	 *  员工编号以及姓名
	 */
	private String empCodeName;
	
	/**
	 * 员工目标月份
	 */
	private String targetMonth;
	
	/**
	 * 员工发布房源目标数量
	 */
	private Integer targetHouseNum;
	
	/**
	 * 员工名称
	 */
	private String empName;
	
	/**
	 * 是否可设置
	 */
	private Integer isSet;

	/**
	 * @return the isSet
	 */
	public Integer getIsSet() {
		return isSet;
	}

	/**
	 * @param isSet the isSet to set
	 */
	public void setIsSet(Integer isSet) {
		this.isSet = isSet;
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
	 * @return the empCodeName
	 */
	public String getEmpCodeName() {
		return empCodeName;
	}

	/**
	 * @param empCodeName the empCodeName to set
	 */
	public void setEmpCodeName(String empCodeName) {
		this.empCodeName = empCodeName;
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

	/**
	 * @return the targetHouseNum
	 */
	public Integer getTargetHouseNum() {
		return targetHouseNum;
	}

	/**
	 * @param targetHouseNum the targetHouseNum to set
	 */
	public void setTargetHouseNum(Integer targetHouseNum) {
		this.targetHouseNum = targetHouseNum;
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
}
