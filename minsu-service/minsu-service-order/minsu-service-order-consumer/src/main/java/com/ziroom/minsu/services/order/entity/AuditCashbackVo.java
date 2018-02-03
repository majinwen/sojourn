package com.ziroom.minsu.services.order.entity;

import com.ziroom.minsu.entity.order.FinanceCashbackEntity;

/**
 * <p>订单返现审核VO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年9月7日
 * @since 1.0
 * @version 1.0
 */
public class AuditCashbackVo extends FinanceCashbackEntity {
	
	/**序列化ID*/
	private static final long serialVersionUID = -4869685824484441341L;

	/**活动名称*/
	private String actName;
	
	/**收款人名称*/
	private String receiveName;
	
	/**收款人电话*/
	private String receiveTel;
	
	/**收款人是否为在民宿黑名单*/
	private String isCustomerBlack;

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReceiveTel() {
		return receiveTel;
	}

	public void setReceiveTel(String receiveTel) {
		this.receiveTel = receiveTel;
	}

	public String getIsCustomerBlack() {
		return isCustomerBlack;
	}

	public void setIsCustomerBlack(String isCustomerBlack) {
		this.isCustomerBlack = isCustomerBlack;
	}
	
}
