package com.zra.common.dto.pay;

import com.zra.common.dto.appbase.AppBaseDto;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by cuigh6 on 2016/12/21.
 */
public class BillDetailDto extends AppBaseDto {
    @ApiModelProperty(value = "合同号")
    private String contractCode;

    @ApiModelProperty(value = "账单号")
    private String billNum;

    @ApiModelProperty(value = "应缴款日期")
    private String gatherDateStr;
    private Date gatherDate;

    @ApiModelProperty(value = "已缴金额总计")
    private String actualAmountStr;
    private BigDecimal actualAmount;

    @ApiModelProperty(value = "最小支付金额")
    private String minPayAmount;

    @ApiModelProperty(value = "应缴金额:应交金额=已缴金额总计+待缴纳费用")
    private String oughtTotalAmountStr;
    private BigDecimal oughtTotalAmount;

    @ApiModelProperty(value = "第几次收款")
    private Integer payNum;

    @ApiModelProperty(value = "账单状态")
    private Integer state;

    @ApiModelProperty(value = "账单标识")
    private String billFid;//账单标识

    @ApiModelProperty(value = "待缴纳费用")
    private String pendingAmountStr;
    private BigDecimal pendingAmount;

    @ApiModelProperty(value = "实际交款日期")
    private String paymentTime;
    
    @ApiModelProperty(value = "费用详情")
    private List<CostDetailDto> details;

    //added by wangxm113
    @ApiModelProperty(value="账单类型：0：合同计划收款（房租-租金卡）；1：其他费用（生活费-优惠券）")
    private Integer billType;
    
    //add by tianxf9 自如寓对接卡券
    @ApiModelProperty(value="客户uid")
    private String uid;
    /**
     * 是租金卡还是优惠券取决于billType
     */
    @ApiModelProperty(value="优惠券或者租金卡")
    private List<CardCouponDto>  cardCoupons;
    //end by tianxf9
    
    //added by wangxm113
    @ApiModelProperty(value="是否显示支付按钮(0:显示；1:不显示)")
    private Integer showPay = 1;

    public Integer getShowPay() {
        return showPay;
    }

    public void setShowPay(Integer showPay) {
        this.showPay = showPay;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPendingAmountStr() {
        return pendingAmountStr;
    }

    public void setPendingAmountStr(String pendingAmountStr) {
        this.pendingAmountStr = pendingAmountStr;
    }

    public String getOughtTotalAmountStr() {
        return oughtTotalAmountStr;
    }

    public void setOughtTotalAmountStr(String oughtTotalAmountStr) {
        this.oughtTotalAmountStr = oughtTotalAmountStr;
    }

    public BigDecimal getOughtTotalAmount() {
        return oughtTotalAmount;
    }

    public void setOughtTotalAmount(BigDecimal oughtTotalAmount) {
        this.oughtTotalAmount = oughtTotalAmount;
    }

    public Integer getPayNum() {
        return payNum;
    }

    public void setPayNum(Integer payNum) {
        this.payNum = payNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMinPayAmount() {
        return minPayAmount;
    }

    public void setMinPayAmount(String minPayAmount) {
        this.minPayAmount = minPayAmount;
    }

    public String getBillFid() {
        return billFid;
    }

    public void setBillFid(String billFid) {
        this.billFid = billFid;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public Date getGatherDate() {
        return gatherDate;
    }

    public void setGatherDate(Date gatherDate) {
        this.gatherDate = gatherDate;
    }

    public String getGatherDateStr() {
        return gatherDateStr;
    }

    public void setGatherDateStr(String gatherDateStr) {
        this.gatherDateStr = gatherDateStr;
    }

    public String getActualAmountStr() {
        return actualAmountStr;
    }

    public void setActualAmountStr(String actualAmountStr) {
        this.actualAmountStr = actualAmountStr;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public List<CostDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<CostDetailDto> details) {
        this.details = details;
    }

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public List<CardCouponDto> getCardCoupons() {
		return cardCoupons;
	}

	public void setCardCoupons(List<CardCouponDto> cardCoupons) {
		this.cardCoupons = cardCoupons;
	}

	public BigDecimal getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(BigDecimal pendingAmount) {
		this.pendingAmount = pendingAmount;
	}
}
