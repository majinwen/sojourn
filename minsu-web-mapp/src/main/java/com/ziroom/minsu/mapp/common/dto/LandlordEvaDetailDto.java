package com.ziroom.minsu.mapp.common.dto;

import javax.validation.constraints.NotNull;

import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;

/**
 * <p>房东请求订单评价详情参数</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class LandlordEvaDetailDto extends BaseParamDto{
 
	/**
     * 被评人uid
     */
	@NotNull(message="{evaluate.ratedUserUid.null}")
    private String ratedUserUid;
	
	/**
     * 订单编号
     */
	@NotNull(message="{evaluate.orderSn.null}")
    private String orderSn;
	
	/**
     * 评价状态
     * @see EvaluateStatuEnum#ONLINE
     */
    private Integer evaStatu = EvaluateStatuEnum.ONLINE.getEvaStatuCode();
    
    /**
     * 评价人类型
     * @see UserTypeEnum#All
     */
    private Integer evaUserType = UserTypeEnum.All.getUserType();

	public String getRatedUserUid() {
		return ratedUserUid;
	}

	public void setRatedUserUid(String ratedUserUid) {
		this.ratedUserUid = ratedUserUid;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getEvaStatu() {
		return evaStatu;
	}

	public void setEvaStatu(Integer evaStatu) {
		this.evaStatu = evaStatu;
	}

	public Integer getEvaUserType() {
		return evaUserType;
	}

	public void setEvaUserType(Integer evaUserType) {
		this.evaUserType = evaUserType;
	}
	
}
