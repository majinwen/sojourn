package com.ziroom.zrp.service.trading.dto.finance;

/**
 * <p>付款单详情对象</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月16日 15:51
 * @since 1.0
 */
public class PayVoucherDetailReqDto {
	private String costCode; // 费用项编码
	private Double refundAmount; //退款金额 单位是元

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getCostCode() {
		return costCode;
	}

	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}
}
