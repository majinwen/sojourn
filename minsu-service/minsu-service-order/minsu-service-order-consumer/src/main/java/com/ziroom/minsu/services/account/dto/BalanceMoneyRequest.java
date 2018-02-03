package com.ziroom.minsu.services.account.dto;

/**
 * <p>
 * 消费余额
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
public class BalanceMoneyRequest extends AccountCommonRequest{
	/** 支付总金额单位分 */
	private Integer totalFee;
   
	/** 支付总金额单位分 */
	private String description;
	
	/** 客户类型 */
	private String uidType;
	/** 客户类型 */
	private String unique_num;
	
	public Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUidType() {
		return uidType;
	}
	public void setUidType(String uidType) {
		this.uidType = uidType;
	}
	public String getUnique_num() {
		return unique_num;
	}
	public void setUnique_num(String unique_num) {
		this.unique_num = unique_num;
	}
	

}
