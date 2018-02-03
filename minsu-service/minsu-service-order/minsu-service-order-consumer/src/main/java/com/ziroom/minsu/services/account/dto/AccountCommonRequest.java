package com.ziroom.minsu.services.account.dto;
/**
 * <p>
 * 账户接口请求 公共参数
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

public class AccountCommonRequest {
   
	/** 用户uid */
	private String uid;
	
	/** 支付类型 或者 支付批次 */
	private String bizCommon;
	
	/** 平台城市 */
	private String dzCityCode;
	
	/** 系统来源  */
	private String sysSource;

	/** 订单号*/
	private String orderSn;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getBizCommon() {
		return bizCommon;
	}
	public void setBizCommon(String bizCommon) {
		this.bizCommon = bizCommon;
	}
	public String getSysSource() {
		return sysSource;
	}
	public void setSysSource(String sysSource) {
		this.sysSource = sysSource;
	}
	public String getDzCityCode() {
		return dzCityCode;
	}
	public void setDzCityCode(String dzCityCode) {
		this.dzCityCode = dzCityCode;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
}
