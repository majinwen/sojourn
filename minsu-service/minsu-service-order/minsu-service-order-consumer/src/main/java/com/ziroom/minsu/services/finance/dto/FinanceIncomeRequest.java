package com.ziroom.minsu.services.finance.dto;

import java.util.List;


/**
 * <p>收入查询条件</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class FinanceIncomeRequest extends BillOrderRequest{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -8049084302286905921L;
	
	/**
	 * 实际付款开始时间
	 */
	private String actualStartTime;
	
	/**
	 * 实际付款结束时间
	 */
	private String actualEndTime;
	
	/**
	 * 创建开始时间
	 */
	private String createTimeStart;
	
	/**
	 * 创建结束时间
	 */
	private String createTimeEnd;
	
	/**
	 * 	收入类型 1：客户房租佣金 2：客户违约金佣金 3：房东房租佣金 4：房东违约金佣金 5：房东违约金
	 */
	private Integer incomeType;
	
	/**
	 * 角色类型
	 */
	private Integer roleType;
	
	/**
	 * 房源fid集合
	 */
	private List<String> houseFids;
    
	/**
	 * @return the roleType
	 */
	public Integer getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the houseFids
	 */
	public List<String> getHouseFids() {
		return houseFids;
	}

	/**
	 * @param houseFids the houseFids to set
	 */
	public void setHouseFids(List<String> houseFids) {
		this.houseFids = houseFids;
	}


	public String getActualStartTime() {
		return actualStartTime;
	}


	public void setActualStartTime(String actualStartTime) {
		this.actualStartTime = actualStartTime;
	}


	public String getActualEndTime() {
		return actualEndTime;
	}


	public void setActualEndTime(String actualEndTime) {
		this.actualEndTime = actualEndTime;
	}


	public String getCreateTimeStart() {
		return createTimeStart;
	}


	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}


	public String getCreateTimeEnd() {
		return createTimeEnd;
	}


	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}


	public Integer getIncomeType() {
		return incomeType;
	}


	public void setIncomeType(Integer incomeType) {
		this.incomeType = incomeType;
	}

	
}
