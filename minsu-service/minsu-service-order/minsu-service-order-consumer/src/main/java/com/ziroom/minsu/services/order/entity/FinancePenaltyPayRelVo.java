/**
 * @FileName: FinancePenaltyPayRelVo.java
 * @Package com.ziroom.minsu.services.order.entity
 * 
 * @author yd
 * @created 2017年5月15日 下午5:50:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.entity;

import java.io.Serializable;

/**
 * <p>罚款与付款单的详细信息</p>
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
public class FinancePenaltyPayRelVo implements Serializable{

	  /**
	 * 序列id
	 */
	private static final long serialVersionUID = 4329949089648439064L;
    /**
     * 罚款单订单号
     */
    private String penaltyOrderSn;

    /**
     * 付款单订单号
     */
    private String pvOrderSn;

    /**
     * 付款金额 分
     */
    private Integer totalFee;

    /**
     * 付款金额字符串 元
     */
    private String totalFeeStr;
    

	/**
	 * @return the totalFeeStr
	 */
	public String getTotalFeeStr() {
		return totalFeeStr;
	}

	/**
	 * @param totalFeeStr the totalFeeStr to set
	 */
	public void setTotalFeeStr(String totalFeeStr) {
		this.totalFeeStr = totalFeeStr;
	}

	/**
	 * @return the penaltyOrderSn
	 */
	public String getPenaltyOrderSn() {
		return penaltyOrderSn;
	}

	/**
	 * @param penaltyOrderSn the penaltyOrderSn to set
	 */
	public void setPenaltyOrderSn(String penaltyOrderSn) {
		this.penaltyOrderSn = penaltyOrderSn;
	}


	/**
	 * @return the pvOrderSn
	 */
	public String getPvOrderSn() {
		return pvOrderSn;
	}

	/**
	 * @param pvOrderSn the pvOrderSn to set
	 */
	public void setPvOrderSn(String pvOrderSn) {
		this.pvOrderSn = pvOrderSn;
	}

	/**
	 * @return the totalFee
	 */
	public Integer getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee the totalFee to set
	 */
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
    
    
}
