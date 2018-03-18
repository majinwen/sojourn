/**
 * @FileName: EmpTargetItem.java
 * @Package com.ziroom.minsu.report.board.vo
 * 
 * @author bushujie
 * @created 2017年1月12日 下午9:09:25
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.board.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>员工目标</p>
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
public class EmpTargetItem extends BaseEntity{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -8278917680472521552L;
	/**
	 * 员工目标fid
	 */
	private String empTargetFid;
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
	/**
	 * 员工发布房源目标数量
	 */
	private Integer targetHouseNum;
	/**
	 * @return the empTargetFid
	 */
	public String getEmpTargetFid() {
		return empTargetFid;
	}
	/**
	 * @param empTargetFid the empTargetFid to set
	 */
	public void setEmpTargetFid(String empTargetFid) {
		this.empTargetFid = empTargetFid;
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
}
