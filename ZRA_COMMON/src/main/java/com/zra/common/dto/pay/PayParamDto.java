package com.zra.common.dto.pay;

import com.zra.common.dto.appbase.AppBaseDto;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: wangxm113
 * @CreateDate: 2016年5月7日
 */
public class PayParamDto extends AppBaseDto {
	@ApiModelProperty(value = "支付方式")
	private String payChannel;
	@ApiModelProperty(value = "支付订单号")
	private String paymentNum;
	@ApiModelProperty(value = "支付金额")
	private Double payAmount;

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPaymentNum() {
		return paymentNum;
	}

	public void setPaymentNum(String paymentNum) {
		this.paymentNum = paymentNum;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

}
