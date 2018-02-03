package com.ziroom.minsu.services.account.dto;
/**
 * <p>
 * 余额 转 冻结 参数
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

public class BalanceToFreezeRequest extends AccountCommonRequest{
	/** 支付总金额单位分 */
	private Integer totalFee;
	/** 客户类型 */
	private String uidType;
	/** 唯一标识  */
	private String unique_num;
	
	public String getUnique_num() {
		return unique_num;
	}
	public void setUnique_num(String unique_num) {
		this.unique_num = unique_num;
	}
	public Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	public String getUidType() {
		return uidType;
	}
	public void setUidType(String uidType) {
		this.uidType = uidType;
	}
	

}
