package com.ziroom.minsu.api.order.dto;

import java.util.List;

import com.ziroom.minsu.api.common.dto.BaseParamDto;
import com.ziroom.minsu.services.order.entity.PayVo;

public class ToPayApiDto extends BaseParamDto {

    /** 支付类型 1 正常支付 2：罚款支付 */
    private Integer toPayType;
    
    /** 总金额*/
    private Integer totalFee;
    
	/** 城市编码  TODO:没用字段，但是得等客户端去掉以后，这边才能干掉*/ 
	private String cityCode;

    /** 单号 (罚款单支付时传) */
	private String bizeCode;
	
	/** 订单编号 */
	private String orderSn;

	/** 支付方式 ：银联信用卡:yl_card, 银联借记:yljj, 银联贷记:yldj, 微信:wx, 支付宝:zfb */
	private String payType;

	/** 设备表示：1、安卓 2、ios */
	private String sourceType;

	/** 支付类型 */
	private List<PayVo> payList;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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

	public String getBizeCode() {
		return bizeCode;
	}

	public void setBizeCode(String bizeCode) {
		this.bizeCode = bizeCode;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

}
