package com.ziroom.minsu.services.order.dto;

import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>订单返现审核请求参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年9月8日
 * @since 1.0
 * @version 1.0
 */
public class AuditCashbackQueryRequest extends PageRequest {

	/**序列化ID*/
	private static final long serialVersionUID = -3569274988308124532L;

	/**返现单号*/
    private String cashbackSn;
	
	/**订单号*/
	private String orderSn;
	
	/**
     * 返现单状态 10：初始 20：已审核 30：已驳回 
     * @see com.ziroom.minsu.valenum.order.CashbackStatusEnum
     */
    private Integer cashbackStatus;
    
    /**
     * 活动码
     */
    private String actSn;
    
    /**
     * 收款人类型 收款人类型：1：房东、2：租客
     * @see com.ziroom.minsu.valenum.order.ReceiveTypeEnum
     */
    private Integer receiveType;
    
    /**
     * 收款人uid
     */
    private String receiveUid;
    
    /**
     * 收款人uidList
     */
    private List<String> receiveUidList;
    
    /**
     * 收款人name
     */
    private String receiveName;
    
    /**
     * 收款人电话
     */
    private String receiveTel;
    
    
    /**审核/驳回日期 开始*/
    private String operTimeStart;
    
    /**审核/驳回日期 结束*/
    private String operTimeEnd;
    
    /**返现单创建时间 开始*/
    private String createTimeStart;
    
    /**返现单创建时间 结束*/
    private String createTimeEnd;
    
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


	public String getCashbackSn() {
		return cashbackSn;
	}


	public void setCashbackSn(String cashbackSn) {
		this.cashbackSn = cashbackSn;
	}


	public String getOrderSn() {
		return orderSn;
	}


	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}


	public Integer getCashbackStatus() {
		return cashbackStatus;
	}


	public void setCashbackStatus(Integer cashbackStatus) {
		this.cashbackStatus = cashbackStatus;
	}


	public String getActSn() {
		return actSn;
	}


	public void setActSn(String actSn) {
		this.actSn = actSn;
	}


	public Integer getReceiveType() {
		return receiveType;
	}


	public void setReceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}


	public String getReceiveUid() {
		return receiveUid;
	}


	public void setReceiveUid(String receiveUid) {
		this.receiveUid = receiveUid;
	}
	
	
	public List<String> getReceiveUidList() {
		return receiveUidList;
	}


	public void setReceiveUidList(List<String> receiveUidList) {
		this.receiveUidList = receiveUidList;
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


	public String getOperTimeStart() {
		return operTimeStart;
	}


	public void setOperTimeStart(String operTimeStart) {
		this.operTimeStart = operTimeStart;
	}


	public String getOperTimeEnd() {
		return operTimeEnd;
	}


	public void setOperTimeEnd(String operTimeEnd) {
		this.operTimeEnd = operTimeEnd;
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
	

    
    
    
	
}
