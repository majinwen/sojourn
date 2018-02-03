package com.ziroom.minsu.services.order.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>
 * 充值参数
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
public class AccountFillLogRequest extends PageRequest{

    /** 序列化id */
    private static final long serialVersionUID = -2156456452562673L;
    
    /** 业务关联号  */
    private String fillSn;
    
    /** 订单编号  */
    private String orderSn;
    
    /** 支付流水号  */
    private String tradeNo;

	public String getFillSn() {
		return fillSn;
	}

	public void setFillSn(String fillSn) {
		this.fillSn = fillSn;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	
}
