package com.ziroom.minsu.services.order.dto;

/**
 * <p>订单金额明细出参</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年8月19日
 * @since 1.0
 * @version 1.0
 */
public class NeedPayFeeItemResponse {

	/**
	 * 金额名称，例如:押金
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
	 * 标题1
	 */
	String title1 = "";

	/**
	 * 标题2
	 */
	String title2 = "";
	
	/**
	 * 是否包好提示
	 */
	Integer isHasTips  = 0;
	
	/**
	 * 提示内容
	 */
	String tipContent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getColorType() {
		return colorType;
	}

	public void setColorType(Integer colorType) {
		this.colorType = colorType;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
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
