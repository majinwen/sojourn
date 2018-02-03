package com.ziroom.minsu.services.account.dto;

/**
 * <p>
 * 余额解冻
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
public class BalanceThawRequest extends AccountCommonRequest{
   
	/** 支付流水号保证唯一 */
	private String trade_no;
	/** 支付总金额单位分 */
	private Integer totalFee;
	
	/** 保证唯一 */
	private String unique_num;
	
	/** 客户类型*/
	private String uid_type;
	
	public String getUnique_num() {
		return unique_num;
	}
	public void setUnique_num(String unique_num) {
		this.unique_num = unique_num;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	public String getUid_type() {
		return uid_type;
	}
	public void setUid_type(String uid_type) {
		this.uid_type = uid_type;
	}
	

}
