package com.zra.common.dto.pay;

import com.zra.common.dto.appbase.AppBaseDto;

import io.swagger.annotations.ApiModelProperty;
/**
 * 兑换优惠券Dto
 * @author tianxf9
 *
 */
public class ExchangeCouponDto extends AppBaseDto {
	
	@ApiModelProperty(value="兑换码")
	private String redeemCode;
	
	@ApiModelProperty(value="兑换人")
	private String uid;

	public String getRedeemCode() {
		return redeemCode;
	}

	public void setRedeemCode(String redeemCode) {
		this.redeemCode = redeemCode;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	

}
