package com.ziroom.minsu.api.evaluate.dto;

import java.io.Serializable;


/**
 * <p>房客评价请求参数</p>
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
public class LanEvaApiRequest implements Serializable{

	/**
	 *  序列id
	 */
	private static final long serialVersionUID = -1764970786327596652L;
	/**
	 * 订单编号
	 */
	private String orderSn;
	/**
	 * 评价人类型（房东=1 房客=2）
	 */
	private Integer evaUserType;

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

}
