package com.ziroom.minsu.mapp.common.dto;


/**
 * <p>房客评价的请求参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class LanlordEvaRequest extends BaseParamDto{


	/**
	 * 订单编号
	 */
	private String orderSn;
	/**
	 * 评价人类型（房东=1 房客=2）
	 */
	private Integer evaUserType;

	/**
	 * 评价内容
	 */
	private String content;
	/**
     * 房东对房客的满意星级(1颗星 2颗星 3颗星 4颗星 5颗星 5是最满意 1是最不满意)
     */
    private Integer landlordSatisfied;
    /**
     * 是否删除 0：否，1：是
     */
    private Integer isDel;
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Integer getEvaUserType() {
		return evaUserType;
	}
	public void setEvaUserType(Integer evaUserType) {
		this.evaUserType = evaUserType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getLandlordSatisfied() {
		return landlordSatisfied;
	}
	public void setLandlordSatisfied(Integer landlordSatisfied) {
		this.landlordSatisfied = landlordSatisfied;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
    
    
    
    
}
