package com.zra.common.dto.pay;

import java.util.List;

/**
 * 校验、使用卡券相关参数
 * @author tianxf9
 *
 */
public class CardCouponParamsDto {
	
	//卡券编号
	private List<String> code;
	//用户uid
	private String uid;
	//收款单id
	private String voucherId;
	//来源 1：app;2:个人新签
	private Integer comeSource;
	
	private Integer billType;
	
	public List<String> getCode() {
		return code;
	}
	public void setCode(List<String> code) {
		this.code = code;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public Integer getComeSource() {
		return comeSource;
	}
	public void setComeSource(Integer comeSource) {
		this.comeSource = comeSource;
	}
	public Integer getBillType() {
		return billType;
	}
	public void setBillType(Integer billType) {
		this.billType = billType;
	}
}
