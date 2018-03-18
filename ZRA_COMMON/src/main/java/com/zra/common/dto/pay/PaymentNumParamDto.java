package com.zra.common.dto.pay;

import com.zra.common.dto.appbase.AppBaseDto;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuigh6 on 2016/12/26.
 */
public class PaymentNumParamDto extends AppBaseDto{
    @ApiModelProperty(value = "账单标识")
    private String billFid;
    @ApiModelProperty(value = "账单号")
    private String billNum;
    @ApiModelProperty(value ="支付金额" )
    private BigDecimal payAmount;
    @ApiModelProperty(value = "用户标识")
    private String uid;
    //add by tianxf9 对接卡券系统
    @ApiModelProperty(value="账单类型")
    private Integer billType;
    @ApiModelProperty(value ="优惠券或者租金卡code")
    private List<String> cardCouponCodes;
    //end by tianxf9

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getBillFid() {
        return billFid;
    }

    public void setBillFid(String billFid) {
        this.billFid = billFid;
    }

	public List<String> getCardCouponCodes() {
		return cardCouponCodes;
	}

	public void setCardCouponCodes(List<String> cardCouponCodes) {
		this.cardCouponCodes = cardCouponCodes;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}
	
	

}
