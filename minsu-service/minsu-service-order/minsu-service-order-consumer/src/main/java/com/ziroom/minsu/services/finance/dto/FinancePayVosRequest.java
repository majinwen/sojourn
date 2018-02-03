package com.ziroom.minsu.services.finance.dto;

import java.util.List;

/**
 * <p>付款单查询条件</p>
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
public class FinancePayVosRequest extends BillOrderRequest{

	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -488687250375776478L;

	/**
	 * 收款人名称
	 */
	private String receiveName;
	
	/**
	 * 收款人电话
	 */
	private String receiveTel;
	
	/**
	 * 收款人Uid List
	 */
	private List<String> receiveUidList;
	
	/**
	 * 付款状态 1：未付款 2：已消费冻结 3：已申请打款 4：已打款 5：已打余额 6：被动余额 7：提前退房取消 8：未绑定银行卡 9：失败
	 */
	private Integer paymentStatus;
	/**
	 * 实际付款开始时间
	 */
	private String actualStartTime;
	
	/**
	 * 实际付款结束时间
	 */
	private String actualEndTime;
	
	/**
	 * 应付款时间（即订单任务执行时间） 开始
	 */
	private String runTimeStart;
	
	/**
	 * 应付款时间（即订单任务执行时间） 结束
	 */
	private String runTimeEnd;
	
	/**付款单号（业务关联id） 付款单号 订单_标识_序号*/
	private String pvSn;
	
	/**
	 *  订单编号
	 */
	private String orderSn;
	
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
	
	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReceiveTel() {
		return receiveTel;
	}

	public void setReceiveTel(String receiveTel) {
		this.receiveTel = receiveTel;
	}

	public List<String> getReceiveUidList() {
		return receiveUidList;
	}

	public void setReceiveUidList(List<String> receiveUidList) {
		this.receiveUidList = receiveUidList;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
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

	public String getRunTimeStart() {
		return runTimeStart;
	}

	public void setRunTimeStart(String runTimeStart) {
		this.runTimeStart = runTimeStart;
	}

	public String getRunTimeEnd() {
		return runTimeEnd;
	}

	public void setRunTimeEnd(String runTimeEnd) {
		this.runTimeEnd = runTimeEnd;
	}

	public String getPvSn() {
		return pvSn;
	}

	public void setPvSn(String pvSn) {
		this.pvSn = pvSn;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	
}
