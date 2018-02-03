/**
 * @FileName: FinancePenaltyVo.java
 * @Package com.ziroom.minsu.services.order.entity
 * 
 * @author yd
 * @created 2017年5月15日 下午3:45:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.entity;

import java.io.Serializable;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.common.utils.StringUtils;

/**
 * <p>罚款单 vo</p>
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
public class FinancePenaltyVo implements Serializable{

	
	 /**
	 * 序列id
	 */
	private static final long serialVersionUID = -6539336369434714594L;

	/**
     * 罚款单编号
     */
    private String penaltySn;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 罚款人uid
     */
    private String penaltyUid;

    /**
     * 罚款人类型 1=房东 2=房客
     */
    private Integer penaltyUserType;

    /**
     * 罚款金额
     */
    private Integer penaltyFee;

    /**
     * 罚款剩余金额
     */
    private Integer penaltyLastFee;

    /**
     * 罚款状态 0=待处理 1=处理中 2=已完成 3=已废除
     */
    private Integer penaltyStatus;
    /**
     * 老罚款状态 不更新
     */
    private transient Integer oldPenaltyStatus;

    /**
     * 罚款类型 10=房东强制取消扣罚100元 11=房东强制取消扣罚首晚房费
     */
    private Integer penaltyType;
    
    /**
     * 罚款金额 字符串
     */
    private String penaltyFeeStr;
    

	/**
	 * @return the penaltyFeeStr
	 */
	public String getPenaltyFeeStr() {
		return penaltyFeeStr;
	}

	/**
	 * @param penaltyFeeStr the penaltyFeeStr to set
	 */
	public void setPenaltyFeeStr(String penaltyFeeStr) {
		this.penaltyFeeStr = penaltyFeeStr;
	}

	/**
	 * @return the penaltySn
	 */
	public String getPenaltySn() {
		return penaltySn;
	}

	/**
	 * @param penaltySn the penaltySn to set
	 */
	public void setPenaltySn(String penaltySn) {
		this.penaltySn = penaltySn;
	}

	/**
	 * @return the orderSn
	 */
	public String getOrderSn() {
		return orderSn;
	}

	/**
	 * @param orderSn the orderSn to set
	 */
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	/**
	 * @return the penaltyUid
	 */
	public String getPenaltyUid() {
		return penaltyUid;
	}

	/**
	 * @param penaltyUid the penaltyUid to set
	 */
	public void setPenaltyUid(String penaltyUid) {
		this.penaltyUid = penaltyUid;
	}

	/**
	 * @return the penaltyUserType
	 */
	public Integer getPenaltyUserType() {
		return penaltyUserType;
	}

	/**
	 * @param penaltyUserType the penaltyUserType to set
	 */
	public void setPenaltyUserType(Integer penaltyUserType) {
		this.penaltyUserType = penaltyUserType;
	}

	/**
	 * @return the penaltyFee
	 */
	public Integer getPenaltyFee() {
		return penaltyFee;
	}

	/**
	 * @param penaltyFee the penaltyFee to set
	 */
	public void setPenaltyFee(Integer penaltyFee) {
		if(!Check.NuNObj(penaltyFee)){
			setPenaltyFeeStr(StringUtils.getPriceFormat(penaltyFee));
		}
		this.penaltyFee = penaltyFee;
	}

	/**
	 * @return the penaltyLastFee
	 */
	public Integer getPenaltyLastFee() {
		return penaltyLastFee;
	}

	/**
	 * @param penaltyLastFee the penaltyLastFee to set
	 */
	public void setPenaltyLastFee(Integer penaltyLastFee) {
		this.penaltyLastFee = penaltyLastFee;
	}

	/**
	 * @return the penaltyStatus
	 */
	public Integer getPenaltyStatus() {
		return penaltyStatus;
	}

	/**
	 * @param penaltyStatus the penaltyStatus to set
	 */
	public void setPenaltyStatus(Integer penaltyStatus) {
		this.penaltyStatus = penaltyStatus;
	}

	/**
	 * @return the oldPenaltyStatus
	 */
	public Integer getOldPenaltyStatus() {
		return oldPenaltyStatus;
	}

	/**
	 * @param oldPenaltyStatus the oldPenaltyStatus to set
	 */
	public void setOldPenaltyStatus(Integer oldPenaltyStatus) {
		this.oldPenaltyStatus = oldPenaltyStatus;
	}

	/**
	 * @return the penaltyType
	 */
	public Integer getPenaltyType() {
		return penaltyType;
	}

	/**
	 * @param penaltyType the penaltyType to set
	 */
	public void setPenaltyType(Integer penaltyType) {
		this.penaltyType = penaltyType;
	}
    
    
    
    
}
