package com.ziroom.minsu.services.account.dto;
/**
 * <p>
 * 账户间 转账
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */

public class TransfersAccountRequest{
   
	/** 消费用户uid */
	private String origUid;
	/** 消费 用户城市 */
	private String orgCityCode;
	/** 消费客户类型 */
	private String uidType;
	/** 消费类型，2：消费余额  4：消费冻结 */
	private Integer reduceType;
	/** 充值客户uid */
	private String targetUid;
	/** 充值 用户城市 */
	private String targetCityCode;
	
	/** 充值 业务类型 */
	private String origBusinessType;
	
	/** 支付方式 */
	private String payType;
	/** 充值客户类型 */
	private String targetUidType;
	/** 消费用户uid */
	private Integer addType;
	/** 消费用户uid */
	private String tradeNo;
	/** 消费用户uid */
	private Integer totalFee;
	/** 消费用户uid */
	private String bizCommon;
	/** 消费用户uid */
	private String description;

	/** 订单号*/
	private String orderSn;
	
	public String getOrigUid() {
		return origUid;
	}
	public void setOrigUid(String origUid) {
		this.origUid = origUid;
	}
	public String getUidType() {
		return uidType;
	}
	public void setUidType(String uidType) {
		this.uidType = uidType;
	}
	public Integer getReduceType() {
		return reduceType;
	}
	public void setReduceType(Integer reduceType) {
		this.reduceType = reduceType;
	}
	public String getTargetUid() {
		return targetUid;
	}
	public void setTargetUid(String targetUid) {
		this.targetUid = targetUid;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTargetUidType() {
		return targetUidType;
	}
	public void setTargetUidType(String targetUidType) {
		this.targetUidType = targetUidType;
	}
	public Integer getAddType() {
		return addType;
	}
	public void setAddType(Integer addType) {
		this.addType = addType;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	public String getBizCommon() {
		return bizCommon;
	}
	public void setBizCommon(String bizCommon) {
		this.bizCommon = bizCommon;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrgCityCode() {
		return orgCityCode;
	}
	public void setOrgCityCode(String orgCityCode) {
		this.orgCityCode = orgCityCode;
	}
	public String getTargetCityCode() {
		return targetCityCode;
	}
	public void setTargetCityCode(String targetCityCode) {
		this.targetCityCode = targetCityCode;
	}
	public String getOrigBusinessType() {
		return origBusinessType;
	}
	public void setOrigBusinessType(String origBusinessType) {
		this.origBusinessType = origBusinessType;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
}
