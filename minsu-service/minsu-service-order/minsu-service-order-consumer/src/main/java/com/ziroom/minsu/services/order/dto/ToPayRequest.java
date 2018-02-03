package com.ziroom.minsu.services.order.dto;

import java.io.Serializable;
import java.util.List;

import com.ziroom.minsu.services.order.entity.PayVo;

public class ToPayRequest implements Serializable{
	
	private static final long serialVersionUID = 2419142409229361170L;

	/** 用户uid */
	private String userUid;
	
	/** 用户类型 */
	private String uidType;
	
	/**
     * 支付类型 1 正常支付 2：罚款支付
     */
    private Integer toPayType;

	/** 城市编码 */
	private String cityCode;

	/** 订单编号 */
	private String orderSn;

	/** 支付方式 如：微信:wx 支付宝 zfb */
	private String payType;

	/** 设备表示 */
	private String sourceType;

	/** 支付类型 */
	private List<PayVo> payList;
	
	/** 业务单号 */
	private String bizeCode;

	/** 支付总金额 */
	private Integer totalFee;

	/** 异步回调 */
	private String notifyUrl;

	/** 0-->不传 1-->充值消费 2-->充值冻结 */
	private Integer passAccount = 0;

	/** 公司名称 */
	private String company = "自如科技";


	/** 订单有效时间 单位 s */
	private Long expiredDate;

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public Integer getPassAccount() {
		return passAccount;
	}

	public void setPassAccount(Integer passAccount) {
		this.passAccount = passAccount;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

    public Long getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Long expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getBizeCode() {
		return bizeCode;
	}

	public void setBizeCode(String bizeCode) {
		this.bizeCode = bizeCode;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public List<PayVo> getPayList() {
		return payList;
	}

	public void setPayList(List<PayVo> payList) {
		this.payList = payList;
	}

	public Integer getToPayType() {
		return toPayType;
	}

	public void setToPayType(Integer toPayType) {
		this.toPayType = toPayType;
	}

	public String getUidType() {
		return uidType;
	}

	public void setUidType(String uidType) {
		this.uidType = uidType;
	}

	
}
