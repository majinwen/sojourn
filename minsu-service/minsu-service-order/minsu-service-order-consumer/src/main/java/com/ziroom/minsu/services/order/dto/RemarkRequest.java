package com.ziroom.minsu.services.order.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>
 * 订单备注请求参数
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年6月27日
 * @since 1.0
 * @version 1.0
 */
public class RemarkRequest extends BaseEntity {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 5026487998660922540L;
	private String orderSn;
	private String uid;
	private String fid;

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

}
