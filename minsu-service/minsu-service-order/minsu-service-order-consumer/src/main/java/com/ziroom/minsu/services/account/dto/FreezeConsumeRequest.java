package com.ziroom.minsu.services.account.dto;
/**
 * <p>
 * 消费冻结金
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

public class FreezeConsumeRequest extends AccountCommonRequest{
   
	/** 支付总金额单位分 */
	private Integer totalFee;
	/** 描述  */
    private String description;
    /** 客户类型  */
    private String uidType;
    /** 唯一标识 */
    private String unique_num;
    /** 消费类型    */
    private String bussiness_type;
    /** 区分虚拟充值    */
    private String biz_common_type;
   
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
	public String getBussiness_type() {
		return bussiness_type;
	}
	public void setBussiness_type(String bussiness_type) {
		this.bussiness_type = bussiness_type;
	}
	public String getBiz_common_type() {
		return biz_common_type;
	}
	public void setBiz_common_type(String biz_common_type) {
		this.biz_common_type = biz_common_type;
	}
	

}
