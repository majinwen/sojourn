/**
 * @FileName: NeedPayFeeACItemResponse.java
 * @Package com.ziroom.minsu.services.order.dto
 * 
 * @author yd
 * @created 2017年6月8日 上午10:14:28
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.order.dto;

import java.io.Serializable;

/**
 * <p>申请预定  普通活动展示项</p>
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
public class NeedPayFeeAcItemResponse implements Serializable{

	
	/**
	 * 序列id
	 */
	private static final long serialVersionUID = -7335968470623566859L;

	/**
	 * 活动名称，例如:首单立减
	 */
	String name;
	
	/**
	 * 颜色类型，0：无颜色（默认），1：红色
	 */
	Integer colorType = 0;
	
	/**
	 * 金额
	 */
	String fee;
	
	/**
	 * 排列序号
	 */
	Integer index;

	/**
	 * 加减符号
	 */
	String symbol = "";
	
	/**
	 * 是否包好提示  0：无
	 */
	Integer isHasTips  = 0;
	
	/**
	 * 提示内容
	 */
	String tipContent;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the colorType
	 */
	public Integer getColorType() {
		return colorType;
	}

	/**
	 * @param colorType the colorType to set
	 */
	public void setColorType(Integer colorType) {
		this.colorType = colorType;
	}

	/**
	 * @return the fee
	 */
	public String getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(String fee) {
		this.fee = fee;
	}

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the isHasTips
	 */
	public Integer getIsHasTips() {
		return isHasTips;
	}

	/**
	 * @param isHasTips the isHasTips to set
	 */
	public void setIsHasTips(Integer isHasTips) {
		this.isHasTips = isHasTips;
	}

	/**
	 * @return the tipContent
	 */
	public String getTipContent() {
		return tipContent;
	}

	/**
	 * @param tipContent the tipContent to set
	 */
	public void setTipContent(String tipContent) {
		this.tipContent = tipContent;
	}
	
	
	
}
