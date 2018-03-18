package com.ziroom.zrp.trading.entity;

/**
 * <p>判断是否可以支付 专用类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月30日 15:15
 * @since 1.0
 */
public class RentContractBillEntity extends RentContractEntity {
	// 账单类型
	private String fBillType;
	public String getFBillType() {
		return fBillType;
	}

	public void setFBillType(String fBillType) {
		this.fBillType = fBillType;
	}
}
