/**
 * @FileName: PayVouchersCallBackRequest.java
 * @Package com.ziroom.minsu.services.order.dto
 * 
 * @author liyingjie
 * @created 2016年4月2日 上午10:02:43
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.order.dto;

/**
 * <p>
 * 财务系统  打款回调 平台
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
public class PayVouchersCallBackApiRequest {
	
	/** 业务关联id */
    private String busId;
	
    /** 1：未付款，2：已付款，3：付款异常 */
    private int payFlag;
    
    /** 实际打款时间*/
    private String payTime;
    
    /** reason  错误原因  */
	private String reason;
    
	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public int getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(int payFlag) {
		this.payFlag = payFlag;
	}

	
    public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
